package com.example.people.ws

import com.example.people.exception.ApplicationException
import com.example.people.exception.ValidationException
import com.example.people.model.Error
import com.example.people.model.Person
import com.example.people.service.PersonService
import com.example.people.ws.api.PersonWsApi
import com.example.people.ws.api.personws.*
import io.reactivex.Single
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class PersonWs(private val personService: PersonService) : PersonWsApi {

    private val log = LoggerFactory.getLogger(PersonWs::class.java)

    private fun logException(t: Throwable) {
        if (t is ValidationException) {
            log.debug("Validation exception", t)
        } else if (t is ApplicationException) {
            log.error("Application exception", t)
        } else {
            log.error("Unexpected exception", t)
        }
    }

    fun toError(e: Throwable): Error {
        return if (this is ApplicationException) {
            Error(
                    errorCode = this.code,
                    errorMessage = this.message!!
            )
        } else {
            unexpectedError
        }
    }

    override fun getPersonList(
            singleRequest: Single<GetPersonListRequest>): Single<GetPersonListResponse> {

        return singleRequest
                .flatMap({ personService.getPersonList() })
                .map(GetPersonListResponse.Companion::ok)
                .doOnError(this::logException)
                .onErrorReturn({ e ->
                    GetPersonListResponse.internalServerError(toError(e))
                })
    }

    override fun getPerson(singleRequest: Single<GetPersonRequest>): Single<GetPersonResponse> {
        return singleRequest
                .flatMap({ request ->
                    personService.getPerson(request.id)
                })
                .map(GetPersonResponse.Companion::ok)
                .doOnError(this::logException)
                .onErrorResumeNext({ e ->
                    if (e is ValidationException) {
                        log.debug("Validation exception", e)
                        Single.just(GetPersonResponse.badRequest(toError(e)))
                    } else {
                        Single.error(e)
                    }
                })
    }

    override fun updatePerson(
            singleRequest: Single<UpdatePersonRequest>): Single<UpdatePersonResponse> {

        return singleRequest
                .flatMap<Person>({ request ->
                    personService.updatePerson(
                            id = request.id,
                            person = request.person)
                })
                .map(UpdatePersonResponse.Companion::ok)
                .onErrorResumeNext({ e ->
                    if (e is ValidationException) {
                        log.debug("Validation exception", e)
                        Single.just(UpdatePersonResponse.badRequest(toError(e)))
                    } else {
                        Single.error(e)
                    }
                })
    }

    companion object {
        private var unexpectedError = Error(
                errorCode = "ERR_001",
                errorMessage = "An unexpected error occurred"
        )
    }
}
