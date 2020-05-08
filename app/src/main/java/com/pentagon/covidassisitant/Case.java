package com.pentagon.covidassisitant;

public class Case {
    String state;
    String recovered;
    String death;
    String total;

    public Case(String state, String recovered, String death, String total) {
        this.state = state;
        this.recovered = recovered;
        this.death = death;
        this.total = total;
    }

    public String getState() {
        return state;
    }

    public String getRecovered() {
        return recovered;
    }

    public String getDeath() {
        return death;
    }

    public String getTotal() {
        return total;
    }
}
