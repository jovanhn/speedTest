/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.example.jovanhn.speedtestapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/*
 * MainActivity class that loads {@link MainFragment}.
 */
public class MainActivity extends Activity {

    private String SPEED_TEST_FILE_SASKTEL ="http://www.proda.maxstream.pres.sasktel.com/Speedtest/filename.txt";
    private String SPEED_TEST_FILE_VANCOUVER ="http://vancouver.speedtest.telus.com/speedtest/random2500x2500.jpg";
    private String SPEED_TEST_FILE_EDMONTON ="http://edmonton.speedtest.telus.com/speedtest/random2500x2500.jpg";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Executing a speedTest
         */
        TestSpeedTest testSpeedTest = new TestSpeedTest(4);
        testSpeedTest.startTest();
    }



}
