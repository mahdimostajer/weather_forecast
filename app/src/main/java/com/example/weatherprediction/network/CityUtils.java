package com.example.weatherprediction.network;

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CityUtils {

    private static final String CITY_BASE_URL = "https://api.mapbox.com/geocoding/v5/mapbox.places/";
    private static final String TYPES = "types";
    private static final String MATCH = "fuzzyMatch";
    private static final String ACCESS_TOKEN = "access_token";


    private static final String TOKEN = "pk.eyJ1IjoibWFoZGltb3N0YWplciIsImEiOiJjbDM3YmtyMmEwOGRuM2tudHpkdXV2aHFmIn0.49lKrermiG2IdnuiMMBV7w";

    private OkHttpClient client;

    public CityUtils() {
        client = Client.getInstance().client;
    }

    public String getCoordinates(String city) {

        HttpUrl.Builder urlBuilder = HttpUrl.parse(CITY_BASE_URL).newBuilder();
        urlBuilder.addPathSegment(city + ".json");
        urlBuilder.addQueryParameter(TYPES, "place");
        urlBuilder.addQueryParameter(MATCH, "false");
        urlBuilder.addQueryParameter(ACCESS_TOKEN, TOKEN);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();


        try {
            Response response = client.newCall(request).execute();
            String res = response.body().string();
            return res;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}
