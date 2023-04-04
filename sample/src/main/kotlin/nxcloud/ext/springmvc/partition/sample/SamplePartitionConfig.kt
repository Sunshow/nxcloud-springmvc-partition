package nxcloud.ext.springmvc.partition.sample

import nxcloud.ext.springmvc.partition.spi.MvcPartitionRegistration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SamplePartitionConfig {

    @Bean
    protected fun adminPartition(): MvcPartitionRegistration {
        return MvcPartitionRegistration("admin")
    }
    
}