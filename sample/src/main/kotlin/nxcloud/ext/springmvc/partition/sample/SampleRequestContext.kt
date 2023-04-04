package nxcloud.ext.springmvc.partition.sample

import nxcloud.ext.springmvc.partition.spi.PartitionRequestContext

class SampleRequestContext : PartitionRequestContext {
    var foo: String? = null
}
