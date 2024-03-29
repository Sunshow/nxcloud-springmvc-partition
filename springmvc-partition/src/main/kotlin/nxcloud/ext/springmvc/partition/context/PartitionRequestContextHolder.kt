package nxcloud.ext.springmvc.partition.context

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import nxcloud.ext.springmvc.partition.spi.MvcPartitionRegistration
import nxcloud.ext.springmvc.partition.spi.PartitionRequestContext
import org.apache.commons.lang3.StringUtils
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

@Component
class PartitionRequestContextHolder : ApplicationContextAware {

    companion object {
        private val empty = object : PartitionRequestContext {}

        private val contextThreadLocal: ThreadLocal<PartitionRequestContext> = ThreadLocal()

        private lateinit var applicationContext: ApplicationContext

        private val partitionRegistrationMap: Map<String, MvcPartitionRegistration> by lazy {
            applicationContext.getBeansOfType(MvcPartitionRegistration::class.java)
                .map { it.value.partition to it.value }
                .toMap()
        }

        @JvmStatic
        fun request(): HttpServletRequest {
            return (RequestContextHolder.getRequestAttributes()!! as ServletRequestAttributes).request
        }

        @JvmStatic
        fun response(): HttpServletResponse {
            return (RequestContextHolder.getRequestAttributes()!! as ServletRequestAttributes).response!!
        }

        private fun requestURI(): String {
            return StringUtils.substringAfter(request().requestURI, request().contextPath)
        }

        fun partition(): String? {
            return partitionRegistrationMap.values
                .firstOrNull {
                    requestURI().startsWith("/${it.partition}/")
                }
                ?.partition
        }


        private fun createContext(partition: String): PartitionRequestContext {
            val registration = partitionRegistrationMap[partition]
                ?: throw IllegalArgumentException("Partition '$partition' is not registered.")
            return registration.contextClass.getDeclaredConstructor().newInstance()
        }

        @JvmStatic
        fun current(): PartitionRequestContext {
            var context = contextThreadLocal.get()
            if (context == null) {
                context = if (partition() != null) {
                    createContext(partition()!!)
                } else {
                    empty
                }
                contextThreadLocal.set(context)
            }
            return context
        }

        @JvmStatic
        fun exists(): Boolean {
            return contextThreadLocal.get() != null
        }

        @JvmStatic
        fun reset() {
            contextThreadLocal.remove()
        }
    }

    override fun setApplicationContext(applicationContext: ApplicationContext) {
        PartitionRequestContextHolder.applicationContext = applicationContext
    }

}