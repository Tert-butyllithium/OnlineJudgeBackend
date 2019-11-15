package edu.sustech.oj_server.entity

import edu.sustech.oj_server.toolclass.Person
import java.sql.Timestamp

open class Contest() {

    var id: Int = 0
    var contest_id:Int
        get() = id
        set(value){
            this.id=value
        }
    var defunct:String?=null

    var title: String? = null

    var start_time: java.sql.Timestamp? = null

    var end_time: java.sql.Timestamp? = null

    var contest_type: String?
        get() = if(defunct.equals("N")) "Public" else  "Password Protected"
        set(value) = if(value.equals("Public")) this.defunct="N" else this.defunct="Y"

    val rule_type  = "ACM"
    val real_time_rank = true
    val created_by = Person("admin", "lanran")
    var last_update_time:Timestamp?=null
    var status:String?=null
        get(){
            val now = Timestamp(System.currentTimeMillis())
            return if (now.after(start_time) and now.before(end_time)) "0" else if(now.before(start_time)) "1" else "-1"
        }
    var private:Byte?=null

    var description: String? = null
    var visible: Boolean
        get() = private?.toInt() == 0
        set(value) {
            this.private = if(value) 0 else 1
        }
    var langmask: Int? = null
    var password: String? = null
    var frozen_time:Int?=null


    // constant value returned to avoid entity inequality to itself before and after it's update/merge
    override fun hashCode(): Int = 42

    override fun toString(): String {
        return "Contest(id=$id, title=$title, start_time=$start_time, end_time=$end_time, status=${this.status})"
    }


}

