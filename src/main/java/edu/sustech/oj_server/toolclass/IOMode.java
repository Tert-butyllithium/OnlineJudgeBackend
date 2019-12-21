package edu.sustech.oj_server.toolclass;

public class IOMode {
    String input;
    String output;
    String io_mode;

    public IOMode() {
        this.input = "input.txt";
        this.output = "output.txt";
        this.io_mode = "Standard IO";
    }

    public void setInput(String input) {
        this.input = input;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public void setIo_mode(String io_mode) {
        this.io_mode = io_mode;
    }

    public String getInput() {
        return input;
    }

    public String getOutput() {
        return output;
    }

    public String getIo_mode() {
        return io_mode;
    }
}
