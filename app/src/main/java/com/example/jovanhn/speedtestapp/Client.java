package com.example.jovanhn.speedtestapp;

import android.annotation.SuppressLint;
import android.util.Log;

public class Client {

    private static int totalClients = 0;
    private int clientId;

    private InternetSpeedTest internetSpeedTest;
    private double maxSpeed = 0.0;
    private double currSpeed = 0.0;
    private double[] percent_speed = new double[101];



    private boolean testFinished = false;
    private int cnt;
    private boolean validResults = false;



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


                percent_speed[progress] = (speedMbs > currSpeed)? speedMbs : currSpeed;
                currSpeed = speedMbs;

                /**
                 * Results are valid after few reports
                 */
                if(validResults){
                    if (currSpeed > maxSpeed) {
                        maxSpeed = currSpeed;
                        Log.i("New_max_speed [" + Integer.toString(clientId) + "]", Double.toString(speedMbs));
                    }
                } else {
                    cnt++;
                    if(cnt>10)
                        validResults = true;
                }


                /**
                 * if progress is 100 % put up flag for finish
                 */
                if(progress == 100){
                    testFinished = true;
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

    public double getCurrSpeed() {
        return currSpeed;
    }
    public double[] getPercent_speed() {
        return percent_speed;
    }
}
