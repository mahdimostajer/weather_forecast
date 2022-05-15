package com.example.weatherprediction.network;

import okhttp3.OkHttpClient;

public class Client {
    private static Client single_instance = null;
    public OkHttpClient client;

    private Client() {
        client = new OkHttpClient();
    }

    public static Client getInstance() {
        if (single_instance == null)
            single_instance = new Client();

        return single_instance;
    }

}
