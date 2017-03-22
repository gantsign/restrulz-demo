package com.example.people.ws

import com.example.people.model.Error
import com.gantsign.restrulz.json.reader.ParseException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalControllerExceptionHandler {

    private val log = LoggerFactory.getLogger(GlobalControllerExceptionHandler::class.java)

    @ExceptionHandler(ParseException::class)
    fun handleParseException(e: ParseException): ResponseEntity<Error> {
        log.error("Parsing error", e)
        val body = Error(
                errorCode = "VAL_001",
                errorMessage = e.message!!
        )
        return ResponseEntity<Error>(body, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<Error> {
        log.error("Unexpected error", e)
        return ResponseEntity<Error>(unexpectedError, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    companion object {
        private val unexpectedError = Error(
                errorCode = "ERR_001",
                errorMessage = "An unexpected error occurred")
    }
}
