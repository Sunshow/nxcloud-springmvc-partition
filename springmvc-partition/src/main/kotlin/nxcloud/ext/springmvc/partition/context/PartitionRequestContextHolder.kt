package nxcloud.ext.springmvc.partition.context

import nxcloud.ext.springmvc.partition.spi.MvcPartitionRegistration
import nxcloud.ext.springmvc.partition.spi.PartitionRequestContext
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestAttributes
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class PartitionRequestContextHolder : ApplicationContextAware {

    companion object {
        private lateinit var applicationContext: ApplicationContext

        private val attributes: ServletRequestAttributes by lazy {
            RequestContextHolder.getRequestAttributes()!! as ServletRequestAttributes
        }

        private val partitionRegistrationMap: Map<String, MvcPartitionRegistration> by lazy {
            applicationContext.getBeansOfType(MvcPartitionRegistration::class.java)
                .map { it.value.partition to it.value }
                .toMap()
        }

        private fun attributeKey(partition: String): String {
            return "nxcloud.ext.partition.context.${partition}"
        }

        @JvmStatic
        fun getRequest(): HttpServletRequest {
            return (RequestContextHolder.getRequestAttributes()!! as ServletRequestAttributes).request
        }

        @JvmStatic
        fun getResponse(): HttpServletResponse {
            return (RequestContextHolder.getRequestAttributes()!! as ServletRequestAttributes).response!!
        }

        private fun createContext(partition: String): PartitionRequestContext {
            val registration = partitionRegistrationMap[partition]
                ?: throw IllegalArgumentException("Partition '$partition' is not registered.")
            return registration.contextClass.newInstance()
        }

        @JvmStatic
        fun current(partition: String): PartitionRequestContext {
            val key = attributeKey(partition)
            var context = attributes.getAttribute(key, RequestAttributes.SCOPE_REQUEST) as PartitionRequestContext?
            if (context == null) {
                context = createContext(partition)
                attributes.setAttribute(key, context, RequestAttributes.SCOPE_REQUEST)
            }
            return context
        }

        @JvmStatic
        fun exists(partition: String): Boolean {
            val key = attributeKey(partition)
            return attributes.getAttribute(key, RequestAttributes.SCOPE_REQUEST) != null
        }

        @JvmStatic
        fun reset(partition: String) {
            val key = attributeKey(partition)
            attributes.removeAttribute(key, RequestAttributes.SCOPE_REQUEST)
        }
    }

    override fun setApplicationContext(applicationContext: ApplicationContext) {
        PartitionRequestContextHolder.applicationContext = applicationContext
    }

}