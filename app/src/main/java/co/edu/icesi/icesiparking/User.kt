package co.edu.icesi.icesiparking

import java.util.*

class User {

    var id:String
    var userId:String
    var name:String
    var surname:String
    var email: String
    var password:String

    constructor(
        userId: String,
        name: String,
        surname: String,
        email: String,
        password: String
    ) {
        this.id = UUID.randomUUID().toString()
        this.userId = userId
        this.name = name
        this.surname = surname
        this.email = email
        this.password = password
    }
}