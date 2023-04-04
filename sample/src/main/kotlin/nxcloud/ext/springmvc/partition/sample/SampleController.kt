package nxcloud.ext.springmvc.partition.sample

import nxcloud.ext.springmvc.partition.context.PartitionRequestContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SampleController {

    @GetMapping("/sample/test")
    fun test(): String {
        println(PartitionRequestContextHolder.current())
        return "test"
    }

}