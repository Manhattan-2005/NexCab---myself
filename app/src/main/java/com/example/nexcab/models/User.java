package com.example.nexcab.models;

public class User {
    String firstname,lastname, password, email, profilepic, userId;
    public User(){}

    // Sign up constructor
    public User(String firstname,String lastname, String email, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.email = email;
        this.profilepic = profilepic;
        this.userId = userId;
    }
    public User(String firstname,String lastname, String email, String password, String profilepic, String userId) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.email = email;
        this.profilepic = profilepic;
        this.userId = userId;
    }

    public String getfirstname() {
        return firstname;
    }
    
    public String getLastname() {
        return lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setfirstname(String firstname) {
        this.firstname = firstname;
    }
    
    public void setLastname(String lastname) { this.lastname = lastname; }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setemail(String email) {
        this.email = email;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getemail() {
        return email;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public String getUserId() {
        return userId;
    }


}
