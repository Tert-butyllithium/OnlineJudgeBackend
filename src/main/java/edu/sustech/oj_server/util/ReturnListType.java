package edu.sustech.oj_server.util;

import java.util.List;

public class ReturnListType<T> {
    List<T> results;
    int total;

    public ReturnListType(List<T> results, int total) {
        this.results = results;
        this.total = total;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getResults() {
        return results;
    }

    public int getTotal() {
        return total;
    }
}
