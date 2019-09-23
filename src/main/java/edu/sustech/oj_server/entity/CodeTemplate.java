package edu.sustech.oj_server.entity;

import java.util.List;

public class CodeTemplate {
//    List<String> list;
//    public CodeTemplate() {
//    }
//
//    public CodeTemplate(List<String> list) {
//        this.list = list;
//    }
//
//    public void setList(List<String> list) {
//        this.list = list;
//    }
//
//    public List<String> getList() {
//        return list;
//    }
    String CPP;
    String Java;
    String C;

    public String getCPP() {
        return CPP;
    }

    public CodeTemplate(String CPP, String java, String c) {
        this.CPP = CPP;
        Java = java;
        C = c;
    }

    public CodeTemplate() {
    }

    public String getJava() {
        return Java;
    }

    public String getC() {
        return C;
    }
}
