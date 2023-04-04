package nxcloud.ext.springmvc.partition.sample

import nxcloud.ext.springmvc.partition.exception.PartitionServletWrapperException

class SampleServletWrapperException(
        statusCode: Int,
        message: String? = null,
        cause: Throwable? = null
) : PartitionServletWrapperException(statusCode, "sample", message, cause)