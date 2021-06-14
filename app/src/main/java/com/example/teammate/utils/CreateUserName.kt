package com.example.teammate.utils

class CreateUserName {

    private fun createUserName() : String{
        return "player" + (1..10_000).random()
    }

    fun checkUser(): String {
        return createUserName()
    }
}