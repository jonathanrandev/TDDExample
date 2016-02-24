package com.exilesoft.tdd.service;

import android.os.AsyncTask;
import android.util.Log;

import com.exilesoft.tdd.model.Weather;
import com.exilesoft.tdd.ui.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

/**
 * Created by Jonathan Senaratne on 22/02/2016.
 */
public class WeatherTask extends AsyncTask<String, Void, String> {

    private static final String COORDINATE_TAG = "coord";
    private static final String LAT_TAG = "lat";
    private static final String LONG_TAG = "lon";

    private static final String WEATHER_TAG = "weather";
    private static final String DESCRIPTION_TAG = "description";

    private static final String MAIN_TAG = "main";
    private static final String TEMPERATURE_TAG = "temp";
    private static final String HUMIDITY_TAG = "humidity";

    private static final String URL = "api.openweathermap.org";
    private static final String PATH = "/data/2.5/weather";
    private static final String QUERY = "?q=";
    private static final String API_KEY ="&appid=7043d8dcd33c1d9bec9edeef8432600c";
    private static final String TAG = WeatherTask.class.getSimpleName();

    private MainActivity mainActivity;

    public WeatherTask(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    protected String doInBackground(String... params) {
        String readStream = "";

        try {
            URI uri = new URI(
                    "http",
                    URL,
                    PATH,
                    QUERY + params[0] + API_KEY,
                    null);
            URL url = uri.toURL();
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            readStream = readStream(con.getInputStream());
            Log.i(TAG, readStream);
        } catch (Exception e) {
            Log.e(TAG, "There was an error getting the weather", e);
        }
        return readStream;
    }

    @Override
    protected void onPostExecute(String jsonResponse) {
        super.onPostExecute(jsonResponse);
        try {
            mainActivity.updateView(getWeatherFromJsonResponse(jsonResponse));
        } catch (JSONException e) {
            Log.e(TAG, "There was an error getting the weather from the json response", e);
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "Invalid JSON response", e);
        }
    }

    public String readStream(InputStream inputStream) throws IOException {
        if (inputStream == null) throw new IllegalArgumentException("The input stream is null");

        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            String nextLine;
            while ((nextLine = reader.readLine()) != null) {
                sb.append(nextLine);
            }
        }
        return sb.toString();
    }


    public Weather getWeatherFromJsonResponse(String jsonResponse) throws JSONException {
        if (jsonResponse == null) throw new IllegalArgumentException("The json response cannot be null");
        if (jsonResponse.isEmpty()) throw new IllegalArgumentException("The json response cannot be empty");

        Weather weather = new Weather();
        JSONObject jsonWeatherObject = new JSONObject(jsonResponse);
        JSONObject jsonTagCoordinate = jsonWeatherObject.getJSONObject(COORDINATE_TAG);
        weather.setLatitude(jsonTagCoordinate.getDouble(LAT_TAG));
        weather.setLongitude(jsonTagCoordinate.getDouble(LONG_TAG));

        JSONArray jsonTagWeather = jsonWeatherObject.getJSONArray(WEATHER_TAG);
        if (jsonWeatherObject.length() > 0)
            weather.setWeatherDescription(jsonTagWeather.getJSONObject(0).getString(DESCRIPTION_TAG));

        JSONObject jsonTagMain = jsonWeatherObject.getJSONObject(MAIN_TAG);
        weather.setTemperature(jsonTagMain.getDouble(TEMPERATURE_TAG));
        weather.setHumidity(jsonTagMain.getDouble(HUMIDITY_TAG));
        return weather;
    }
}
