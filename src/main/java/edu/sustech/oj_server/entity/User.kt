package edu.sustech.oj_server.entity

import edu.sustech.oj_server.util.Authentication
import edu.sustech.oj_server.util.MTRandom
import java.sql.Timestamp
import kotlin.math.abs

open class User {
    var id: String? = null
    var email: String? = null
    var submit: Int? = null
    var solved: Int? = null
    var is_disabled: String? = null
    var ip: String? = null
    var accesstime: java.sql.Timestamp? = null
    var volume: Int? = null
    var language: Int? = null
//    var password: String? = null
    var create_time: java.sql.Timestamp? = null
    var last_login:Timestamp? = null
    var username: String? = null
    var school: String? = null
    var admin_type:String? = null
    val two_factor_auth = false
    val open_api = false

    constructor(id: String?, email: String?, submit: Int?, solved: Int?, is_disabled: String?, ip: String?, accesstime: Timestamp?, volume: Int?, language: Int?, password: String?, create_time: Timestamp?, username: String?, school: String?) {
        val random = MTRandom(System.currentTimeMillis())
        this.id = id
//        this.id = abs(random.nextInt())
        this.email = email
        this.submit = submit
        this.solved = solved
        this.is_disabled = is_disabled
        this.ip = ip
        this.accesstime = accesstime
        this.volume = volume
        this.language = language
//        this.password = password
        this.create_time = create_time
        this.username = username
        this.school = school
        this.admin_type = if (Authentication.isAdministrator(id)) "Super Admin" else "Regular User"
        this.last_login = create_time

    }


    override fun toString(): String =
            "Entity of type: ${javaClass.name} ( " +
                    "id = $username "

    // constant value returned to avoid entity inequality to itself before and after it's update/merge
//    override fun hashCode(): Int = 42

//    override fun equals(other: Any?): Boolean {
//        if (this === other) return true
//        if (javaClass != other?.javaClass) return false
//        other as User
//        return id == other.id
//    }

}

