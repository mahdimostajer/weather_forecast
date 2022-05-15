package com.example.weatherprediction.network;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherUtils {
    private static final String CITY_BASE_URL = "https://api.openweathermap.org/data/2.5/onecall?";
    private static final String LAT = "lat";
    private static final String LON = "lon";
    private static final String APP_ID = "appid";
    private static final String UNITS = "units";
    private static final String EXCLUDE = "exclude";

    private static final String TOKEN = "aef48d9ec8e0b984582a6a1573a58a83";

    private OkHttpClient client;


    public WeatherUtils() {
        client = Client.getInstance().client;
    }

    public String getWeather(String lat, String lon) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(CITY_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter(LAT, lat);
        urlBuilder.addQueryParameter(LON, lon);
        urlBuilder.addQueryParameter(APP_ID, TOKEN);
        urlBuilder.addQueryParameter(UNITS, "metric");
        urlBuilder.addQueryParameter(EXCLUDE, "minutely,hourly,alerts");
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
