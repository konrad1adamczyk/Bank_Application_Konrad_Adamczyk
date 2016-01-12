package com.luxoft.bankapp.network;

/**
 * Created by Konrad on 2016-01-10.
 */
public class BankServerMonitor implements Runnable {
    private boolean running = true;

    @Override
    public void run() {
        while (running) {
            System.out.println("Number of connected clients: " + BankServerThreaded.getCounter());
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
