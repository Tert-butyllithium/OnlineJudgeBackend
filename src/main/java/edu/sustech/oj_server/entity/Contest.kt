package edu.sustech.oj_server.entity

import edu.sustech.oj_server.toolclass.Person
import java.sql.Timestamp

open class Contest {

    var id: Int? = null

    var title: String? = null

    var start_time: java.sql.Timestamp? = null

    var end_time: java.sql.Timestamp? = null

    var contest_type: String? = null

    val rule_type  = "ACM"
    val real_time_rank = true
    val created_by = Person("admin", "lanran")
    var last_update_time:Timestamp?=null
    var status:String?= null


    var description: String? = null
    var private1: Byte? = null
    var langmask: Int? = null
    var password: String? = null

    constructor(id: Int?, title: String?, start_time: Timestamp?, end_time: Timestamp?, contest_type: String?, description: String?, private1: Byte?, langmask: Int?, password: String?) {
        this.id = id
        this.title = title
        this.start_time = start_time
        this.end_time = end_time
        this.contest_type = if(contest_type==("N")) "Public" else  "Password Protected"
        this.description = description
        this.private1 = private1
        this.langmask = langmask
        this.password = password
//        this.status = private1.toString()
        val now = Timestamp(System.currentTimeMillis())
        this.status = if (now.after(start_time) and now.before(end_time)) "0" else if(now.before(start_time)) "1" else "-1"
        this.last_update_time = start_time
//        println(now)
    }

//    constructor(id): Int?, title: String?, start_time: Timestamp?, end_time: Timestamp?, contest_type: String?, description: String?, private1: Byte?, langmask: Int?, password: String?) {
//        this.id = id
//        this.title = title
//        this.start_time = start_time
//        this.end_time = end_time
//        this.contest_type = contest_type
//        this.description = description
//        this.private1 = private1
//        this.langmask = langmask
//        this.password = password
//    }


    // constant value returned to avoid entity inequality to itself before and after it's update/merge
    override fun hashCode(): Int = 42

//    override fun equals(other: Any?): Boolean {
//        if (this === other) return true
//        if (javaClass != other?.javaClass) return false
//        other as Contest
//
//        if (id) != other.id) return false
//        if (title != other.title) return false
//        if (start_time != other.start_time) return false
//        if (end_time != other.end_time) return false
//        if (defunct != other.defunct) return false
//        if (description != other.description) return false
//        if (private1 != other.private1) return false
//        if (langmask != other.langmask) return false
//        if (password != other.password) return false
//
//        return true
//    }

}

