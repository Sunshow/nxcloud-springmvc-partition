package nxcloud.ext.springmvc.partition.sample

import nxcloud.ext.springmvc.partition.exception.PartitionServletWrapperException

class SampleServletWrapperException(
    statusCode: Int,
    requestUri: String? = null,
    message: String? = null,
    cause: Throwable? = null
) : PartitionServletWrapperException(statusCode, "sample", requestUri, message, cause)