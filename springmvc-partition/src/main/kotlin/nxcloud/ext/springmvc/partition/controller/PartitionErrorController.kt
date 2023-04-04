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
        val exception = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION) as Exception?
        if (exception != null) {
            if (exception is ServletException) {
                val forwardUri = request.getAttribute(RequestDispatcher.FORWARD_REQUEST_URI) as String
                val mappingUri = StringUtils.substringAfter(forwardUri, request.contextPath)

                partitions
                    ?.first { partition ->
                        mappingUri.startsWith("/${partition.partition}/")
                    }
                    ?.let { partition ->
                        throw PartitionServletWrapperException(partition.partition, exception.message, exception.cause)
                    }
            }
            throw exception
        }
    }
}