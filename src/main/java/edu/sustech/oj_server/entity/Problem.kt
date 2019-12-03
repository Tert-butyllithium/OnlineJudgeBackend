package edu.sustech.oj_server.entity

import edu.sustech.oj_server.toolclass.IOMode
import edu.sustech.oj_server.toolclass.Person
import edu.sustech.oj_server.toolclass.Sample
import java.sql.Timestamp

open class Problem {
    var id: Int? = null
    var tags:List<String> ? = null
    var title: String? = null
    var description: String? = null
    var input_description: String? = null
    var output_description: String? = null
//    var sampleInput: String? = null
//    var sampleOutput: String? = null
    var _id:String? = null
    var samples:ArrayList<Sample>?=null
    var spj: Boolean? = null
    var hint: String? = null
    var source: String? = null
    var create_time: Timestamp? = null
    var time_limit:Int
    var memory_limit: Int? = null
    var defunct: String? = null
//    var accepted: Int? = null
    var submission_number: Int? = null
    var accepted_number: Int? = null
    var created_by: Person?=null
    var template:CodeTemplate?=null
    var spj_language:String?=null
    var last_update_time:Timestamp?=null
    val io_mode = IOMode()
    val rule_type="ACM"
    val difficulty= "Mid"
    val total_score = 0
    val share_submission=false
    var contest:Int?=null
    var my_status:Int?=null
    var languages:List<String>?=null

    constructor(id: Int?, title: String?, description: String?, input: String?, output: String?, sampleInput: String?, sampleOutput: String?, spj: String?, hint: String?, source: String?, inDate: Timestamp?, time_lim: Int, memory_limit: Int?, defunct: String?, accepted: Int, submission_number: Int, solved: Int) {
        this.id = id
        this._id = id.toString()
        this.title = title
        this.description = description
        this.input_description = input
        this.output_description = output
//        this.sampleInput = sampleInput
//        this.sampleOutput = sampleOutput
        this.samples= ArrayList()
        this.samples!!.add(Sample(sampleInput, sampleOutput))
//        this.spj = spj
        if(spj == "Y"){
            this.spj = true
            this.spj_language = "C++";
        }
        else{
            this.spj = false
        }
        this.hint = hint
        this.source = source
        this.create_time = inDate

        this.time_limit = time_lim
//        println(this.time_limit)

        this.memory_limit = memory_limit
        this.defunct = defunct
        this.submission_number = submission_number
        this.accepted_number = accepted
        this.tags = listOf("unknown")
        this.created_by = Person("root", null)
        this.last_update_time = inDate
        this.languages= listOf("C","C++","Java","Python3","Kotlin")
        this.template=CodeTemplate()
//        println(this.time_limit)
    }

//    private fun help(c:Int):Int{
//        return 1000;
//    }
}

