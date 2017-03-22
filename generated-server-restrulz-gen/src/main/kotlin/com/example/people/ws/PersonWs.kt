package com.example.people.ws

import com.example.people.exception.ApplicationException
import com.example.people.exception.ValidationException
import com.example.people.model.Error
import com.example.people.model.Person
import com.example.people.service.PersonService
import com.example.people.ws.api.PersonWsApi
import com.example.people.ws.api.personws.GetPersonListResponse
import com.example.people.ws.api.personws.GetPersonResponse
import com.example.people.ws.api.personws.UpdatePersonResponse
import io.reactivex.Single
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class PersonWs(private val personService: PersonService) : PersonWsApi {

    private val log = LoggerFactory.getLogger(PersonWs::class.java)

    private fun Throwable.toError(): Error {
        return if (this is ApplicationException) {
            Error(
                    errorCode = this.code,
                    errorMessage = this.message!!
            )
        } else {
            unexpectedError
        }
    }

    fun <T> wrapException(single: () -> Single<T>): Single<T> {
        try {
            return single()
        } catch (e: Throwable) {
            return Single.error(e)
        }
    }

    private fun logException(t: Throwable) {
        if (t is ValidationException) {
            log.debug("Validation exception", t)
        } else if (t is ApplicationException) {
            log.error("Application exception", t)
        } else {
            log.error("Unexpected exception", t)
        }
    }

    override fun getPersonList(): Single<GetPersonListResponse> {
        return wrapException { personService.getPersonList() }
                .map(GetPersonListResponse.Companion::ok)
                .doOnError(this::logException)
                .onErrorReturn { e ->
                    GetPersonListResponse.internalServerError(e.toError())
                }
    }

    override fun getPerson(id: String): Single<GetPersonResponse> {
        return wrapException { personService.getPerson(id) }
                .map(GetPersonResponse.Companion::ok)
                .doOnError(this::logException)
                .onErrorReturn { e ->
                    if (e is ValidationException) {
                        GetPersonResponse.badRequest(e.toError())
                    } else {
                        GetPersonResponse.internalServerError(e.toError())
                    }
                }
    }

    override fun updatePerson(id: String, person: Person): Single<UpdatePersonResponse> {
        return wrapException { personService.updatePerson(id, person) }
                .map(UpdatePersonResponse.Companion::ok)
                .doOnError(this::logException)
                .onErrorReturn { e ->
                    if (e is ValidationException) {
                        UpdatePersonResponse.badRequest(e.toError())
                    } else {
                        UpdatePersonResponse.internalServerError(e.toError())
                    }
                }
    }

    companion object {
        private var unexpectedError = Error(
                errorCode = "ERR_001",
                errorMessage = "An unexpected error occurred"
        )
    }
}
