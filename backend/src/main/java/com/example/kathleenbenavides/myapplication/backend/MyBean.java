package com.example.kathleenbenavides.myapplication.backend;

/** The object model for the data we are sending through endpoints
 * Created following instructions from the following:
 * https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
 * */

public class MyBean {

    private String myData;

    public String getData() {
        return myData;
    }

    public void setData(String data) {
        myData = data;
    }
}