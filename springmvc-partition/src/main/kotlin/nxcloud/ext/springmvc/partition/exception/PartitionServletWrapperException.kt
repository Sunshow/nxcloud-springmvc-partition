package nxcloud.ext.springmvc.partition.exception

open class PartitionServletWrapperException(
    val statusCode: Int,
    val partition: String?,
    val requestUri: String? = null,
    message: String? = null,
    cause: Throwable? = null
) : RuntimeException(message, cause)