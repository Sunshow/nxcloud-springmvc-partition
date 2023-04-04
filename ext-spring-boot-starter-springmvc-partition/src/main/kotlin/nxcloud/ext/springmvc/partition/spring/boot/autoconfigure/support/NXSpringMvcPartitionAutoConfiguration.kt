package nxcloud.ext.springmvc.partition.spring.boot.autoconfigure.support

import nxcloud.ext.springmvc.partition.interceptor.PartitionRequestContextInterceptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.context.annotation.ComponentScan
import org.springframework.core.Ordered
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping

@AutoConfiguration
@ComponentScan("nxcloud.ext.springmvc.partition")
@ConditionalOnClass(RequestMappingHandlerMapping::class)
class NXSpringMvcPartitionAutoConfiguration : WebMvcConfigurer {

    @Autowired
    private lateinit var partitionRequestContextInterceptor: PartitionRequestContextInterceptor

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(partitionRequestContextInterceptor)
            .addPathPatterns("/**")
            .order(Ordered.HIGHEST_PRECEDENCE)
    }
}