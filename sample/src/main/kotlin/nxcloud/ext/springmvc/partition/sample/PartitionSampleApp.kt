package nxcloud.ext.springmvc.partition.sample

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PartitionSampleApp

fun main(args: Array<String>) {
    runApplication<PartitionSampleApp>(*args)
}