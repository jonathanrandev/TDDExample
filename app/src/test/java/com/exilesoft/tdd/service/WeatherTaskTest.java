package com.exilesoft.tdd.service;

import com.exilesoft.tdd.BuildConfig;
import com.exilesoft.tdd.model.Weather;
import com.exilesoft.tdd.ui.MainActivity;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Created by Jonathan Senaratne on 22/02/2016.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(manifest = Config.NONE, sdk = 21, constants = BuildConfig.class)
public class WeatherTaskTest {

    private WeatherTask weatherTask;

    @Before
    public void setUp() {
        weatherTask = new WeatherTask(Mockito.mock(MainActivity.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testReadNullInputStreamThrowsException() throws IOException {
        weatherTask.readStream(null);
    }

    @Test
    public void testReadInputStreamNotNull() throws Exception {
        InputStream stream = new ByteArrayInputStream("New Input Stream".getBytes(StandardCharsets.UTF_8));
        String response = weatherTask.readStream(stream);
        assertNotNull(response);
    }

    @Test
    public void testInputStreamStringConversion() throws Exception {
        String inputString = "Input Stream String";
        InputStream stream = new ByteArrayInputStream(inputString.getBytes(StandardCharsets.UTF_8));
        String response = weatherTask.readStream(stream);
        assertThat(inputString, is(equalTo(response)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetWeatherFromNullResponse() throws JSONException {
        weatherTask.getWeatherFromJsonResponse(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetWeatherFromEmptyResponse() throws JSONException {
        weatherTask.getWeatherFromJsonResponse("");
    }

    @Test(expected = JSONException.class)
    public void testGetWeatherFromJsonBadJsonThrowsException() throws Exception {
        weatherTask.getWeatherFromJsonResponse("This is not a json string !");
    }

    @Test
    public void testGetWeatherFromJsonString() throws Exception {
        Weather weather = weatherTask.getWeatherFromJsonResponse(getJsonWeatherResponse());
        assertNotNull(weather);
        assertThat(weather, is(instanceOf(Weather.class)));
    }

    @Test
    public void testGetWeatherFromValidJson() throws JSONException {
        Weather expected = getWeather();
        Weather actual = weatherTask.getWeatherFromJsonResponse(getJsonWeatherResponse());
        assertThat(expected.getLatitude(), is(equalTo(actual.getLatitude())));
        assertThat(expected.getLongitude(), is(equalTo(actual.getLongitude())));
        assertThat(expected.getWeatherDescription(), is(equalTo(actual.getWeatherDescription())));
        assertThat(expected.getHumidity(), is(equalTo(actual.getHumidity())));
        assertThat(expected.getTemperature(), is(equalTo(actual.getTemperature())));
    }

    private static Weather getWeather() {
        Weather weather = new Weather();
        weather.setLatitude(51.51);
        weather.setLongitude(-0.13);
        weather.setHumidity(65);
        weather.setTemperature(281.09);
        weather.setWeatherDescription("few clouds");
        return weather;
    }

    private static String getJsonWeatherResponse() {
        return "{\"coord\":{\"lon\":-0.13,\"lat\":51.51},\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"few clouds\"," +
                "\"icon\":\"02d\"}],\"base\":\"stations\",\"main\":{\"temp\":281.09,\"pressure\":1009,\"humidity\":65,\"temp_min\":279" +
                ".85,\"temp_max\":282.15},\"visibility\":10000,\"wind\":{\"speed\":3.6,\"deg\":290},\"clouds\":{\"all\":20}," +
                "\"dt\":1456158478,\"sys\":{\"type\":1,\"id\":5089,\"message\":0.0328,\"country\":\"GB\",\"sunrise\":1456124420," +
                "\"sunset\":1456162108},\"id\":2643743,\"name\":\"London\",\"cod\":200}";
    }

}
