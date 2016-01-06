package com.luxoft.bankapp.ecxeptions;

/**
 * Created by Konrad on 2016-01-03.
 */
public class FeedException extends RuntimeException {

    private static final long serialVersionUID = 5857984859930773172L;

    public FeedException(String message) {
        super(message);
    }
}