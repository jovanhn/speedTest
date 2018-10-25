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
    private double averageSpeed = 0.0;
    private int average_counter = 0;
    private double currSpeed = 0.0;

    public ClientChecker(List<Client> clients){
        this.clients = clients;
    }

    @Override
    public void run() {
        while(!allDone) {
            allDone = checkAll(clients);
            try {
                Thread.sleep(1000);
                double maximumSpeed = Math.round((maxSpeed(clients)* (1514.0/1460.0))*100.0)/100.0;
                averageSpeed+=sum_all_curr_speed(clients);
                average_counter++;
                currSpeed = Math.round(sum_all_curr_speed(clients)*100.0)/100.0;

                Log.i("Current_speed", Math.round(sum_all_curr_speed(clients)*100.0)/100.0 + " ");
                Log.i("Max_speed_local: ", Math.round(maximumSpeed*100.0)/100.0 + ", Number of clients: " + clients.size());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Log.i("Current_speed", "======= Done ======== ");
        Log.i("Max_speed_local: ", "======= Done ======== ");
        /**
         * if flag allDone is true
         * all clients finished
         * print speed to log
         */
       // String maximumSpeed = Double.toString(maxSpeed(clients) / (double)clients.size());
        double maximumSpeed = Math.round((maxSpeed(clients)* (1514.0/1460.0))*100.0)/100.0;
        Log.i("Max_speed_final: ", maximumSpeed + ", Number of clients: " + clients.size() );
        printSpeedAtProgress(clients);

        printMaxSpeeds(clients);
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

    public void printMaxSpeeds(List<Client> clients){
        for(Client client : clients){
            Log.i("Max_client_speed: ", client.getMaxSpeed() + " Client: " + client.getClientId() );
        }
    }

    public double sum_all_curr_speed(List<Client> clients) {
        double speed = 0;
        for(Client client : clients){
            speed+=client.getCurrSpeed();
        }
        return speed;
    }

    public double speedAtProgress(List<Client> clients, int index) {
        double speed = 0;
        for(Client client : clients) {
            speed += client.getPercent_speed()[index];
        }
        return speed;
    }

    public void printSpeedAtProgress(List<Client> clients) {
        for(int i = 0; i < 101; i++) {
            Log.i("Speed_at_progress",Math.round(speedAtProgress(clients, i)*100.0)/100.0+ " Mbps, " + i + "%");
        }
        Log.i("Speed_at_progress","========= Done ==========");
    }
    public double getCurrSpeed(){
        return currSpeed;
    }


}
