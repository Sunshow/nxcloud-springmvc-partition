package nxcloud.ext.springmvc.partition.sample

import nxcloud.ext.springmvc.partition.spi.PartitionRequestContext
import nxcloud.ext.springmvc.partition.spi.SinglePartitionRequestContextHolder

class SampleRequestContext : PartitionRequestContext {
    var foo: String? = null
}

class SampleRequestContextHolder : SinglePartitionRequestContextHolder<SampleRequestContext> {
    override val partition: String
        get() = "sample"

}