package com.example.chatapp

class User {
    var name: String? = null
    var email: String? = null
    var password: String? = null
    var mobile: String? = null
    var uid: String? = null

    constructor(){}

    constructor(name: String?, email: String?, password: String?, mobile: String?, uid: String?){
        this.name = name
        this.email = email
        this.password = password
        this.mobile = mobile
        this.uid = uid
    }
}