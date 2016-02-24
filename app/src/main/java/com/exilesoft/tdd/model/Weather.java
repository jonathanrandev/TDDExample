package com.exilesoft.tdd.model;

/**
 * Created by Jonathan Senaratne on 22/02/2016.
 */
public class Weather {
    private double latitude;
    private double longitude;
    private double temperature;
    private double humidity;
    private String weatherDescription;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Weather)) return false;

        Weather weather = (Weather) o;

        if (Double.compare(weather.latitude, latitude) != 0) return false;
        if (Double.compare(weather.longitude, longitude) != 0) return false;
        if (Double.compare(weather.temperature, temperature) != 0) return false;
        if (Double.compare(weather.humidity, humidity) != 0) return false;
        return weatherDescription != null ? weatherDescription.equals(weather.weatherDescription) : weather.weatherDescription == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(latitude);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(temperature);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(humidity);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (weatherDescription != null ? weatherDescription.hashCode() : 0);
        return result;
    }
}
