package nxcloud.ext.springmvc.partition.spring.boot.autoconfigure.support

import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.context.annotation.ComponentScan
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping

@AutoConfiguration
@ComponentScan("nxcloud.ext.springmvc.partition")
@ConditionalOnClass(RequestMappingHandlerMapping::class)
class NXSpringMvcPartitionAutoConfiguration {


}