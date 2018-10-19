package com.example.jovanhn.speedtestapp;


import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import fr.bmartel.speedtest.inter.ISpeedTestListener;
import fr.bmartel.speedtest.model.SpeedTestError;
import fr.bmartel.speedtest.SpeedTestReport;
import fr.bmartel.speedtest.SpeedTestSocket;

/**
 * @author Milos Milanovic
 */
public abstract class InternetSpeedTest extends AsyncTask<Void, Void, String> {

    /** Socket timeout used in ms. */
    private final static int SOCKET_TIMEOUT = 10000;
    /** Wait to cancel Speed Test Task. 90sec*/
    private static final int WAIT_TO_CACNCEL = 90000;

    private String SPEED_TEST_HOST ="http://www.proda.maxstream.pres.sasktel.com/Speedtest/filename.txt";
    /** Fields. */
    private Handler mHandler = null;
    private SpeedTestSocket mSpeedTestSocket = null;
    private Runnable mRunnableStopTask = new Runnable() {

        @Override
        public void run() {
            stopTask();
            downloadIsCanceled();
        }
    };

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mHandler = new Handler();
        mHandler.postDelayed(mRunnableStopTask, WAIT_TO_CACNCEL);
    }

    public int pingHost(String host) {
        boolean status = false;
        long startTime = System.currentTimeMillis();
        try {
            status = InetAddress.getByName(host).isReachable(SOCKET_TIMEOUT);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        return status ? (int) (endTime - startTime) : -1;
    }

    @Override
    protected String doInBackground(Void... voids) {
            //    for (int i = 0; i < 10; i++) {
             //       Log.d(TAG, i + "# ping=" + pingHost(SPEED_TEST_HOST) + " ms");
            //    }
        mSpeedTestSocket = new SpeedTestSocket();
        mSpeedTestSocket.setSocketTimeout(SOCKET_TIMEOUT);
        mSpeedTestSocket.addSpeedTestListener(new ISpeedTestListener() {

            @Override
            public void onCompletion(final SpeedTestReport report) {
                //Log.i("SpeedTest", "getTransferRateBit: " + report.getTransferRateBit());
                mHandler.removeCallbacks(mRunnableStopTask);
                stopTask();
            }

            @Override
            public void onProgress(final float percent, final SpeedTestReport report) {
                //Log.i("SpeedTest", "percent: " + percent + ", bitrate: " + report.getTransferRateBit().doubleValue());
                mHandler.post(new Runnable() {

                    @Override
                    public void run() {
                        downloadProgress((int) percent,
                                report.getTransferRateBit().doubleValue() / 1000000.0);
                    }
                });
            }

            @Override
            public void onError(SpeedTestError speedTestError, String errorMessage) {
                //Log.i("SpeedTest", "errorMessage: " + errorMessage);
            }
        });
        mSpeedTestSocket.startDownload(SPEED_TEST_HOST);
        return null;
    }


    public void stopTask() {
        if (mSpeedTestSocket != null) {
            mSpeedTestSocket.forceStopTask();
        }
    }

    public abstract void downloadIsCanceled();

    public abstract void downloadProgress(int progress, double speedMbs);
}