package com.example.jovanhn.speedtestapp;

import android.util.Log;

import java.util.List;

/**
 * Client Checker checks if all clients finished speed test
 * It runs until all clients have finished their test
 * Sleeps 1 second after every check
 */
public class ClientChecker extends Thread {

    private List<Client> clients;
    private boolean allDone = false;

    public ClientChecker(List<Client> clients){
        this.clients = clients;
    }

    @Override
    public void run() {
        while(!allDone) {
            allDone = checkAll(clients);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        /**
         * if flag allDone is true
         * all clients finished
         * print speed to log
         */
        String maximumSpeed = Double.toString(maxSpeed(clients) / (double)clients.size());
        Log.i("Max_speed_final: ", maximumSpeed + " Number of clients: " + clients.size() );
    }

    private boolean checkAll(List<Client> clients) {
        for(Client client : clients){
            if(!client.isTestFinished())
                return false;
        }
        return true;
    }
    public double maxSpeed(List<Client> clients){
        double max = 0;
        for(Client client : clients){
            max+=client.getMaxSpeed();
        }
        return max;
    }
}
