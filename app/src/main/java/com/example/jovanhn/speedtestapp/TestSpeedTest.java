package com.example.jovanhn.speedtestapp;

import java.util.ArrayList;
import java.util.List;

public class TestSpeedTest {

    private int clientNumber;

    private ClientChecker clientChecker;
    private List<Client> clients;

    TestSpeedTest(int clientNumber){
        this.clientNumber = clientNumber;
        this.clients = makeClients(clientNumber);
        this.clientChecker = new ClientChecker(clients);
    }

    public List<Client> makeClients(int n) {

        List<Client> clients = new ArrayList<>();
        for(int i = 0; i < n; i++){
            Client client =  new Client();
            clients.add(client);
        }
        return clients;
    }

    public void startClients() {
        for(Client client : clients) {
            client.executeClient();
        }
    }

    public void startTest() {
        startClients();
        clientChecker.start();
    }
    public double getClientsSpeed() {
        return clientChecker.getCurrSpeed();
    }
}
