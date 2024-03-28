package nxcloud.ext.springmvc.partition.spi

import nxcloud.ext.springmvc.partition.exception.PartitionServletWrapperException

open class MvcPartitionRegistration(
    // 分区名称, 对应 URL 前缀
    val partition: String,
    val contextClass: Class<out PartitionRequestContext>,
    val servletExceptionClass: Class<out PartitionServletWrapperException>,
    // 除了分区匹配之外额外的触发分区异常的判断条件
    val servletExceptionPredicate: ((MvcPartitionServletErrorContext) -> Boolean)? = null,
)

/**
 * Servlet 出错时的上下文
 */
class MvcPartitionServletErrorContext(
    val partition: MvcPartitionRegistration,

    val statusCode: Int,
    val exception: Exception?,
    val errorMessage: String?,
    val forwardUri: String,
    val mappingUri: String,
)