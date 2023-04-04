package nxcloud.ext.springmvc.partition.context

import nxcloud.ext.springmvc.partition.spi.MvcPartitionRegistration
import nxcloud.ext.springmvc.partition.spi.PartitionRequestContext
import org.apache.commons.lang3.StringUtils
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

        @JvmStatic
        val request: HttpServletRequest by lazy {
            (RequestContextHolder.getRequestAttributes()!! as ServletRequestAttributes).request
        }

        @JvmStatic
        val response: HttpServletResponse by lazy {
            (RequestContextHolder.getRequestAttributes()!! as ServletRequestAttributes).response!!
        }

        private val requestURI by lazy {
            StringUtils.substringAfter(request.requestURI, request.contextPath)
        }

        val partition: String? by lazy {
            partitionRegistrationMap.values
                .firstOrNull {
                    requestURI.startsWith("/${it.partition}/")
                }
                ?.partition
        }

        private fun attributeKey(partition: String): String {
            return "nxcloud.ext.partition.context.${partition}"
        }


        private fun createContext(partition: String): PartitionRequestContext {
            val registration = partitionRegistrationMap[partition]
                ?: throw IllegalArgumentException("Partition '$partition' is not registered.")
            return registration.contextClass.newInstance()
        }

        @JvmStatic
        fun current(): PartitionRequestContext? {
            val key = partition
                ?.let {
                    attributeKey(it)
                }
                ?: return null

            var context = attributes.getAttribute(key, RequestAttributes.SCOPE_REQUEST) as PartitionRequestContext?
            if (context == null) {
                context = createContext(partition!!)
                attributes.setAttribute(key, context, RequestAttributes.SCOPE_REQUEST)
            }
            return context
        }

        @JvmStatic
        fun exists(): Boolean {
            return partition
                ?.let {
                    val key = attributeKey(it)
                    attributes.getAttribute(key, RequestAttributes.SCOPE_REQUEST) != null
                }
                ?: false
        }

        @JvmStatic
        fun reset() {
            partition
                ?.apply {
                    val key = attributeKey(this)
                    attributes.removeAttribute(key, RequestAttributes.SCOPE_REQUEST)
                }
        }
    }

    override fun setApplicationContext(applicationContext: ApplicationContext) {
        PartitionRequestContextHolder.applicationContext = applicationContext
    }

}