package org.example;

import java.sql.Time;

public class Id {


    private String name;
    private Time lestRequestsTime;
    private String lestRequests;
    private int sumOfRequests;


    public Id(String name, Time lestRequestsTime, String lestRequests) {

        this.name = name;
        this.lestRequestsTime = lestRequestsTime;
        this.lestRequests = lestRequests;
        this.sumOfRequests = 1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Time getLestRequestsTime() {
        return lestRequestsTime;
    }

    public void setLestRequestsTime(Time lestRequestsTime) {
        this.lestRequestsTime = lestRequestsTime;
    }

    public String getLestRequests() {
        return lestRequests;
    }

    public void setLestRequests(String lestRequests) {
        this.lestRequests = lestRequests;
    }

    public int getSumOfRequests() {
        return sumOfRequests;
    }

    public void setSumOfRequests() {
        this.sumOfRequests++;
    }
}
