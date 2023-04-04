package nxcloud.ext.springmvc.partition.controller

import nxcloud.ext.springmvc.partition.exception.PartitionServletWrapperException
import nxcloud.ext.springmvc.partition.spi.MvcPartitionRegistration
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.RequestDispatcher
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest

@RestController
class PartitionErrorController : ErrorController {

    @Autowired(required = false)
    private var partitions: List<MvcPartitionRegistration>? = null

    @RequestMapping("/error")
    @Throws(Exception::class)
    fun handleError(request: HttpServletRequest, modelMap: ModelMap) {
        // 捕获从filter等处过来的异常或者处理404等 然后将异常重新抛出给全局处理
        val statusCode = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE) as Int
        val exception = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION) as Exception?
        val errorMessage = request.getAttribute(RequestDispatcher.ERROR_MESSAGE) as String?

        val forwardUri = request.getAttribute(RequestDispatcher.FORWARD_REQUEST_URI) as String
        val mappingUri = StringUtils.substringAfter(forwardUri, request.contextPath)

        val partition = partitions
                ?.firstOrNull { partition ->
                    mappingUri.startsWith("/${partition.partition}/")
                }
                ?: throw PartitionServletWrapperException(
                        statusCode,
                        null,
                        errorMessage
                                ?.takeIf {
                                    it.isNotEmpty()
                                }
                                ?: exception?.message,
                        if (exception != null && exception is ServletException) {
                            exception.cause
                        } else {
                            exception
                        }
                )

        // 反射创建异常
        throw partition
                .servletExceptionClass.getDeclaredConstructor(
                        Int::class.java,
                        String::class.java,
                        Throwable::class.java
                )
                .newInstance(
                        statusCode,
                        errorMessage
                                ?.takeIf {
                                    it.isNotEmpty()
                                }
                                ?: exception?.message,
                        if (exception != null && exception is ServletException) {
                            exception.cause
                        } else {
                            exception
                        }
                )
    }
}