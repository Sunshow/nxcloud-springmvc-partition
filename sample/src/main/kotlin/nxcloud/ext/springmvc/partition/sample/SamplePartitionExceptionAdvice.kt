package nxcloud.ext.springmvc.partition.sample

import nxcloud.ext.springmvc.partition.exception.PartitionServletWrapperException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class SamplePartitionExceptionAdvice {

    @ExceptionHandler(PartitionServletWrapperException::class)
    fun exceptionHandler(e: PartitionServletWrapperException): ResponseEntity<Any> {
        return ResponseEntity<Any>(e.partition, HttpStatus.valueOf(e.statusCode))
    }
    
}