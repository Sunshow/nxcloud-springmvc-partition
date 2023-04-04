package nxcloud.ext.springmvc.partition.spi

import nxcloud.ext.springmvc.partition.exception.PartitionServletWrapperException

open class MvcPartitionRegistration(
        // 分区名称, 对应 URL 前缀
        val partition: String,
        val contextClass: Class<out PartitionRequestContext>,
        val servletExceptionClass: Class<out PartitionServletWrapperException>,
)