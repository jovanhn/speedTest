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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Client> clients = makeClients(1);
        for(Client client :clients){
            client.executeClient();
        }

        ClientChecker t1 = new ClientChecker(clients);
        t1.start();

    }

    public List<Client> makeClients(int n){

        List<Client> clients = new ArrayList<>();
        for(int i = 0; i < n; i++){
            Client client =  new Client();
            clients.add(client);
        }
        return clients;
    }

}