package com.luxoft.bankapp.ecxeptions;

/**
 * Created by Konrad on 2015-12-19.
 */
public class ClientNotExistsException extends Throwable {
    private String name;
    private static final long serialVersionUID = 7471041238018929740L;

    public ClientNotExistsException(){
    }

    public ClientNotExistsException(String name) {
        this.name = name;
    }

    public String printMessage() {
        if (name != null)
            return "Client with name \"" + name + "\" does not exist: ";
        else
            return "No client";
    }
}
