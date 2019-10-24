package edu.sustech.oj_server.entity

import edu.sustech.oj_server.utilclass.Person
import java.sql.Timestamp

open class News {
    var newsId: Int? = null
    var userId: String? = null
    var title: String? = null
    var content: String? = null
    var time: java.sql.Timestamp? = null
    var create_time: java.sql.Timestamp? = null
    var last_update_time: java.sql.Timestamp? = null
    var importance: Int? = null
    var visible: Boolean? = null
    var created_by: Person?=null

    constructor(newsId: Int?, userId: String?, title: String?, content: String?, time: Timestamp?, importance: Int?, defunct: String?) {
        this.newsId = newsId
        this.userId = userId
        this.title = title
        this.content = content
        this.time = time
        this.create_time=time;
        this.last_update_time=time;
        this.importance = importance
        this.visible = defunct=="N"
        this.created_by=Person(userId,userId)
    }

    // constant value returned to avoid entity inequality to itself before and after it's update/merge
    override fun hashCode(): Int = 42

}

