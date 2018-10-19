package com.example.jovanhn.speedtestapp;

import android.annotation.SuppressLint;
import android.util.Log;

public class Client {

    private static int totalClients = 0;
    private int clientId;

    private InternetSpeedTest internetSpeedTest;
    private double maxSpeed = 0;
    private boolean testFinished=false;
    private int cnt;

    @SuppressLint("StaticFieldLeak")
    public Client(){
        maxSpeed = 0.0;
        clientId = ++totalClients;
        internetSpeedTest = new InternetSpeedTest() {
            @Override
            public void downloadIsCanceled() {

            }

            /**
             *
             * @param progress
             * @param speedMbs
             */
            @Override
            public void downloadProgress(int progress, double speedMbs) {
                Log.i("\nClientSpeed [" + Integer.toString(clientId) +"] |", "\nProgress: " + Integer.toString(progress) + " %, Speed: "
                        + Double.toString(speedMbs) + " Mbs");

                /**
                 * In first few steps speed is too high
                 * Skip 10 steps at beginning for correct results
                 */
                cnt++;
                if(cnt>10) {

                    cnt--;
                    if (speedMbs > maxSpeed) {
                        maxSpeed = speedMbs;
                        Log.i("New_max_speed", Double.toString(speedMbs));
                    }
                }
                /**
                 * if progress is 100 % put up flag for finish
                 */
                if(progress == 100){
                    testFinished=true;
                }
            }
        };
    }
    public int getClientId(){
        return clientId;
    }
    public void executeClient(){
        internetSpeedTest.execute();
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }
    public boolean isTestFinished() {
        return testFinished;
    }
}
