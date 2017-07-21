package com.example.vladimir.seabattle.logic.models;


import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

@SuppressWarnings("WeakerAccess")
@IgnoreExtraProperties
public class User {
    public String firstName;

    public String lastName;

    public User () {}

    public User(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Exclude
    public String getFullName() {
        return firstName + " " + lastName;
    }
}
