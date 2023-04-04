package nxcloud.ext.springmvc.partition.interceptor

import mu.KotlinLogging
import nxcloud.ext.springmvc.partition.context.PartitionRequestContextHolder
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class PartitionRequestContextInterceptor : BaseHandlerInterceptorAdapter() {

    private val logger = KotlinLogging.logger {}

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        logger.debug("收到请求: {}", request.requestURI)
        PartitionRequestContextHolder.reset()
        return true
    }

    override fun afterCompletion(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        ex: Exception?
    ) {
        super.afterCompletion(request, response, handler, ex)
        PartitionRequestContextHolder.reset()
        logger.debug("结束请求: {}", request.requestURI)
    }

}