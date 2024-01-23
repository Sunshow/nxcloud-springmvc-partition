package nxcloud.ext.springmvc.partition.exception

import org.springframework.context.ApplicationEvent

class PartitionServletWrapperExceptionApplicationEvent(
    payload: PartitionServletWrapperException,
) : ApplicationEvent(payload)