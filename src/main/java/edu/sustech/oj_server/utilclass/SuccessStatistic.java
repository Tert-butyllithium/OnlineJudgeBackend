package edu.sustech.oj_server.utilclass;

public class SuccessStatistic {
    int time_cost,memory_cost;

    public void setTime_cost(int time_cost) {
        this.time_cost = time_cost;
    }

    public void setMemory_cost(int memory_cost) {
        this.memory_cost = memory_cost;
    }

    public int getTime_cost() {
        return time_cost;
    }

    public int getMemory_cost() {
        return memory_cost;
    }

    public SuccessStatistic(int time_cost, int memory_cost) {
        this.time_cost = time_cost;
        this.memory_cost = memory_cost;
    }
}
