package nxcloud.ext.springmvc.partition.exception

open class PartitionServletWrapperException(
    val partition: String,
    message: String? = null,
    cause: Throwable? = null
) : RuntimeException(message, cause)