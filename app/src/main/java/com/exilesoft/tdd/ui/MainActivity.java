package com.exilesoft.tdd.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.exilesoft.tdd.R;
import com.exilesoft.tdd.model.Weather;
import com.exilesoft.tdd.service.WeatherTask;

/**
 * Created by Jonathan Senaratne on 22/02/2016.
 */
public class MainActivity extends Activity {

    private Button btnGetWeather;
    private EditText editCity;
    private TextView textLatLng;
    private TextView textTemperature;
    private TextView textHumidity;
    private TextView textWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnGetWeather = (Button) findViewById(R.id.btn_get_weather);
        editCity = (EditText) findViewById(R.id.edit_city);
        textLatLng = (TextView) findViewById(R.id.text_lat_lng);
        textTemperature = (TextView) findViewById(R.id.text_temperature);
        textHumidity = (TextView) findViewById(R.id.text_humidity);
        textWeather = (TextView) findViewById(R.id.text_weather);

        btnGetWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String location = editCity.getText().toString();
                if (validateCity(location)) {
                    new WeatherTask(MainActivity.this).execute(location);
                }

            }
        });
    }

    public boolean validateCity(String location) {
        return location != null && !location.isEmpty();
    }


    public void updateView(Weather weather) {
        textLatLng.setText(mergeLatAndLong(weather.getLatitude(), weather.getLongitude()));
        textTemperature.setText(String.valueOf(weather.getTemperature()));
        textHumidity.setText(String.valueOf(weather.getHumidity()));
        textWeather.setText(weather.getWeatherDescription());
    }


    public String mergeLatAndLong(double latitude, double longitude) {
        return latitude + "," + longitude;
    }
}
