package com.example.people.exception

abstract class ApplicationException(val code: String,
                                    message: String,
                                    cause: Throwable? = null) : RuntimeException(message, cause)
