package com.exilesoft.tdd.ui;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.exilesoft.tdd.BuildConfig;
import com.exilesoft.tdd.R;
import com.exilesoft.tdd.model.Weather;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by Jonathan Senaratne on 22/02/2016.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(manifest = "app/src/main/AndroidManifest.xml", constants = BuildConfig.class, sdk = 21)
public class MainActivityTest {

    private MainActivity mainActivity;

    @Before
    public void setUp() {
        mainActivity = Robolectric.buildActivity(MainActivity.class).create().visible().get();
    }

    @Test
    public void testActivity() {
        assertThat(mainActivity, notNullValue());
    }

    @Test
    public void testCityEditTextNotNull() {
        EditText editCity = (EditText) mainActivity.findViewById(R.id.edit_city);
        assertThat(editCity, notNullValue());
    }

    @Test
    public void testGetWeatherButtonNotNull() {
        Button getWeatherButton = (Button) mainActivity.findViewById(R.id.btn_get_weather);
        assertThat(getWeatherButton, notNullValue());
    }

    @Test
    public void testLatLongTextViewNotNull() {
        TextView latLngTextView = (TextView) mainActivity.findViewById(R.id.text_lat_lng);
        assertNotNull(latLngTextView);
    }

    @Test
    public void testTemperatureTextViewNotNull() {
        TextView temperatureTextView = (TextView) mainActivity.findViewById(R.id.text_temperature);
        assertNotNull(temperatureTextView);
    }

    @Test
    public void testHumidityTextViewNotNull() {
        TextView humidityTextView = (TextView) mainActivity.findViewById(R.id.text_humidity);
        assertNotNull(humidityTextView);
    }

    @Test
    public void testWeatherTextViewNotNull() {
        TextView weatherTextView = (TextView) mainActivity.findViewById(R.id.text_weather);
        assertNotNull(weatherTextView);
    }

    @Test
    public void shouldReturnFalseIfLocationIsNull() throws Exception {
        assertFalse(mainActivity.validateCity(null));
    }

    @Test
    public void shouldReturnFalseIfLocationIsEmpty() {
        assertFalse(mainActivity.validateCity(""));
    }

    @Test
    public void shouldReturnTrueIfLocationIsValid() {
        assertTrue(mainActivity.validateCity("A test city"));
    }

    @Test
    public void shouldMergeLatitudeAndLongitude() {
        Weather weather = getWeather();
        String expected = weather.getLatitude() + "," + weather.getLongitude();
        String actual = mainActivity.mergeLatAndLong(weather.getLatitude(), weather.getLongitude());
        assertEquals(expected, actual);
    }

    @Test
    public void shouldSetWeatherDetails() {
        Weather weather = getWeather();
        mainActivity.updateView(weather);

        String actualLatLng = ((TextView) mainActivity.findViewById(R.id.text_lat_lng)).getText().toString();
        String actualTemperature = ((TextView) mainActivity.findViewById(R.id.text_temperature)).getText().toString();
        String actualHumidity = ((TextView) mainActivity.findViewById(R.id.text_humidity)).getText().toString();
        String actualWeather = ((TextView) mainActivity.findViewById(R.id.text_weather)).getText().toString();
        assertEquals(weather.getLatitude() + "," + weather.getLongitude(), actualLatLng);
        assertThat(weather.getTemperature(), is(equalTo(Double.parseDouble(actualTemperature))));
        assertThat(weather.getHumidity(), is(equalTo(Double.parseDouble(actualHumidity))));
        assertEquals(weather.getWeatherDescription(), actualWeather);
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
}
