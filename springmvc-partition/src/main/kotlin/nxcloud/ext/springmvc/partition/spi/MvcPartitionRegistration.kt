package nxcloud.ext.springmvc.partition.spi

open class MvcPartitionRegistration(
    // 分区名称, 对应 URL 前缀
    val partition: String,
)