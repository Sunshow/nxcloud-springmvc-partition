package nxcloud.ext.springmvc.partition.interceptor

import org.springframework.core.annotation.AnnotationUtils
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.AsyncHandlerInterceptor

abstract class BaseHandlerInterceptorAdapter : AsyncHandlerInterceptor {

    protected open fun <T : Annotation> getAnnotation(
        handlerMethod: HandlerMethod,
        annotationType: Class<T>
    ): T? {
        return this.getAnnotation(handlerMethod, annotationType, false)
    }

    protected open fun <T : Annotation> getAnnotation(
        handlerMethod: HandlerMethod,
        annotationType: Class<T>,
        searchClassType: Boolean
    ): T? {
        return AnnotationUtils.findAnnotation(handlerMethod.method, annotationType)
            ?: if (searchClassType) {
                AnnotationUtils.findAnnotation(handlerMethod.beanType, annotationType)
            } else {
                null
            }
    }
    
}