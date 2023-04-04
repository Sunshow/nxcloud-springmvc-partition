package nxcloud.ext.springmvc.partition.spi

import nxcloud.ext.springmvc.partition.context.PartitionRequestContextHolder
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

interface SinglePartitionRequestContextHolder<T : PartitionRequestContext> {

    val partition: String

    fun getRequest(): HttpServletRequest {
        return PartitionRequestContextHolder.getRequest()
    }

    fun getResponse(): HttpServletResponse {
        return PartitionRequestContextHolder.getResponse()
    }

    fun current(): T {
        @Suppress("UNCHECKED_CAST")
        return PartitionRequestContextHolder.current(partition) as T
    }

    fun exists(): Boolean {
        return PartitionRequestContextHolder.exists(partition)
    }

    fun reset() {
        PartitionRequestContextHolder.reset(partition)
    }
    
}