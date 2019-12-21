package edu.sustech.oj_server.toolclass;

import java.util.ArrayList;

public class TestCases {
    private String id;
    private ArrayList<Case> info;

    public static class Case{
        private String stripped_output_md5;
        private long input_size;
        private long output_size;
        private String input_name;
        private String output_name;

        public String getStripped_output_md5() {
            return stripped_output_md5;
        }

        public void setStripped_output_md5(String stripped_output_md5) {
            this.stripped_output_md5 = stripped_output_md5;
        }

        public long getInput_size() {
            return input_size;
        }

        public void setInput_size(long input_size) {
            this.input_size = input_size;
        }

        public long getOutput_size() {
            return output_size;
        }

        public void setOutput_size(long output_size) {
            this.output_size = output_size;
        }

        public String getInput_name() {
            return input_name;
        }

        public void setInput_name(String input_name) {
            this.input_name = input_name;
        }

        public String getOutput_name() {
            return output_name;
        }

        public void setOutput_name(String output_name) {
            this.output_name = output_name;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Case> getInfo() {
        return info;
    }

    public void setInfo(ArrayList<Case> info) {
        this.info = info;
    }
}
