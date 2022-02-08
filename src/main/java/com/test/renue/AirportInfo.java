package com.test.renue;

public class AirportInfo {
    private final String name;
    private final String town;
    private final String country;
    private final String smth1;
    private final String smth2;
    private final String latitude;
    private final String longitude;
    private final String number1;
    private final String number2;
    private final String letter;
    private final String place;
    private final String type;
    private final String airport;

    public AirportInfo(String name, String town, String country, String smth1, String smth2, String latitude,
                       String longitude, String number1, String number2, String letter, String place,
                       String type, String airport) {
        this.name = name;
        this.town = town;
        this.country = country;
        this.smth1 = smth1;
        this.smth2 = smth2;
        this.latitude = latitude;
        this.longitude = longitude;
        this.number1 = number1;
        this.number2 = number2;
        this.letter = letter;
        this.place = place;
        this.type = type;
        this.airport = airport;
    }

    public String toString() {
        StringBuilder result  = new StringBuilder();
        result
                .append(name).append(" ")
                .append(town).append(" ")
                .append(country).append(" ")
                .append(smth1).append(" ")
                .append(smth2).append(" ")
                .append(latitude).append(" ")
                .append(longitude).append(" ")
                .append(number1).append(" ")
                .append(number2).append(" ")
                .append(letter).append(" ")
                .append(place).append(" ")
                .append(type).append(" ")
                .append(airport).append(" ");

        return result.toString();
    }

    public String getName() {
        return name;
    }

    public String getTown() {
        return town;
    }

    public String getCountry() {
        return country;
    }

    public String getSmth1() {
        return smth1;
    }

    public String getSmth2() {
        return smth2;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getNumber1() {
        return number1;
    }

    public String getNumber2() {
        return number2;
    }

    public String getLetter() {
        return letter;
    }

    public String getPlace() {
        return place;
    }

    public String getType() {
        return type;
    }

    public String getAirport() {
        return airport;
    }
}
