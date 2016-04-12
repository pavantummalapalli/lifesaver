package com.team.lifesaver.representations;

/**
 * Created by pavan.t on 10/03/16.
 */
public class User {

    public User(String username, String name, String password, String gender, String dateOfBirth, String bloodgroup, String mobile, String email, String state, int pincode, String city, String address, double lang, double lat) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.bloodgroup = bloodgroup;
        this.mobile = mobile;
        this.email = email;
        this.state = state;
        this.pincode = pincode;
        this.city = city;
        this.address = address;
        this.lang = lang;
        this.lat = lat;
    }

    public  User(){

    }

    private String name;
    private String username;
    private String password;
    private String gender;
    private String dateOfBirth;
    private String bloodgroup;
    private String mobile;
    private String email;
    private String state;
    private String city;
    private int pincode;
    private String address;
    private double lang;
    private double lat;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getBloodgroup() {
        return bloodgroup;
    }

    public void setBloodgroup(String bloodgroup) {
        this.bloodgroup = bloodgroup;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPincode() {
        return pincode;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
    }

    public double getLang() {
        return lang;
    }

    public void setLang(double lang) {
        this.lang = lang;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

}
