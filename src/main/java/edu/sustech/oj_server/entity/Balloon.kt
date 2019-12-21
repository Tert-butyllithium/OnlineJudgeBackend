package edu.sustech.oj_server.entity

import edu.sustech.oj_server.toolclass.Status
import java.sql.Timestamp


open class Balloon {
    var id:Int?=null
    var solution_id: Int? = null
    var username:String?=null
    var real_name:String?=null
    var ac_time:Double?=null
    var ac_info:Status?=null
    var checked:Boolean?=null
    var problem_id:String?=null
}

