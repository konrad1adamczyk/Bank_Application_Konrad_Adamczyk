package com.luxoft.bankapp.requests;

/**
 * Created by Konrad on 2016-01-04.
 */
public class LogInRequest implements Request {
    private String name;

    public LogInRequest(){
        System.out.println("Welcome ");
    }

    public LogInRequest(String name){
        this.name = name;
        System.out.println("Welcome " + name);
    }

    public String getLogin(){
        return name;
    }

    @Override
    public void printRequestInfo() {
        System.out.println("Login to the system ");
    }
}
