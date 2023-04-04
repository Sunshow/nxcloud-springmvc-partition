package nxcloud.ext.springmvc.partition.spi

import nxcloud.ext.springmvc.partition.context.PartitionRequestContextHolder
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

interface SinglePartitionRequestContextHolder<T : PartitionRequestContext> {

    val partition: String

    private fun checkPartition() {
        if (partition != PartitionRequestContextHolder.partition) {
            throw IllegalStateException("Current partition is not '$partition'.")
        }
    }

    fun getRequest(): HttpServletRequest {
        return PartitionRequestContextHolder.request
    }

    fun getResponse(): HttpServletResponse {
        return PartitionRequestContextHolder.response
    }

    fun current(): T {
        checkPartition()
        @Suppress("UNCHECKED_CAST")
        return PartitionRequestContextHolder.current() as T
    }

    fun exists(): Boolean {
        checkPartition()
        return PartitionRequestContextHolder.exists()
    }

    fun reset() {
        checkPartition()
        PartitionRequestContextHolder.reset()
    }

}