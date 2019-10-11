package edu.sustech.oj_server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnnouncementController {

    @GetMapping("/api/announcement")
    public String getAnnouncement(){
        return "{\n" +
                "    \"error\": null,\n" +
                "    \"data\": {\n" +
                "        \"results\": [\n" +
                "            {\n" +
                "                \"id\": 1,\n" +
                "                \"created_by\": {\n" +
                "                    \"id\": 1,\n" +
                "                    \"username\": \"root\",\n" +
                "                    \"real_name\": null\n" +
                "                },\n" +
                "                \"title\": \"Lanranforces复制了SUSTech OJ的数据库，但是二者并不互通\",\n" +
                "                \"content\": \"<p>测试版本，不做任何保证</p>\",\n" +
                "                \"create_time\": \"2019-10-11T14:29:14.950009Z\",\n" +
                "                \"last_update_time\": \"2019-10-11T14:29:14.950009Z\",\n" +
                "                \"visible\": true\n" +
                "            }\n" +
                "        ],\n" +
                "        \"total\": 1\n" +
                "    }\n" +
                "}";
    }
}
