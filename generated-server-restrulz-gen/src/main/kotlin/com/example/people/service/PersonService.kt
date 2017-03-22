package com.example.people.service

import com.example.people.exception.ValidationException
import com.example.people.model.Address
import com.example.people.model.Person
import io.reactivex.Single
import org.springframework.stereotype.Component
import java.util.UUID

@Component
open class PersonService {

    fun getPersonList(): Single<List<Person>> {
        return Single.just(listOf(examplePerson))
    }

    fun getPerson(id: String): Single<Person> {
        return Single.just(examplePerson)
    }

    fun updatePerson(id: String, person: Person): Single<Person> {
        try {
            throw ValidationException(
                    code = "VAL_002",
                    message = "Example validation error");
        } catch (e: Exception) {
            return Single.error(e)
        }
    }

    companion object {
        private val examplePerson = Person(
                firstName = "Joe",
                lastName = "Bloggs",
                age = 42,
                employed = true,
                monthsEmployed = 12,
                workAddress = Address(id = UUID.randomUUID().toString()),
                homeAddress = Address(id = UUID.randomUUID().toString()),
                addressHistory = listOf(Address(id = UUID.randomUUID().toString()))
        )
    }

}
