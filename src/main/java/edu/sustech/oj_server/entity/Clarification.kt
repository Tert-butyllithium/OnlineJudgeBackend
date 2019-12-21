package edu.sustech.oj_server.entity

import java.sql.Timestamp


open class Clarification {
    var id: Int? = null
    var create_time:Timestamp? = null
    var userId: String? = null
    var contestId: Int? = null
    var message: String? = null
    var reply: String? = null


    override fun toString(): String =
            "Entity of type: ${javaClass.name} ( " +
                    "id = $id " +
                    "userId = $userId " +
                    "contestId = $contestId " +
                    "message = $message " +
                    "reply = $reply " +
                    ")"

    // constant value returned to avoid entity inequality to itself before and after it's update/merge
    override fun hashCode(): Int = 42

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Clarification

        if (id != other.id) return false
        if (userId != other.userId) return false
        if (contestId != other.contestId) return false
        if (message != other.message) return false
        if (reply != other.reply) return false

        return true
    }

}

