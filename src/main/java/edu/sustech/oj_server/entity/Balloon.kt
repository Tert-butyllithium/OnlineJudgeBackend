package edu.sustech.oj_server.entity


open class Balloon {
    var id: Int? = null
    var username: String? = null
    var solution_id: Int? = null
    var contest_id: Int? = null
    var Problem_id: Int? = null
    var status: Short? = null

    constructor(balloonId: Int?, userId: String?, sid: Int?, cid: Int?, pid: Int?, status: Short?) {
        this.id = balloonId
        this.username = userId
        this.solution_id = sid
        this.contest_id = cid
        this.Problem_id = pid
        this.status = status
    }




}

