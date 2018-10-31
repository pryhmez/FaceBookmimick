package com.example.prime.fbmmk.Model;

/**
 * Created by prime on 10/29/18.
 */

public class Fb {
    public static final String TABLE_NAME = "fb";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_FIRSTNAME = "firstname";
    public static final String COLUMN_LASTNAME = "lastname";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_PASSWORD = "password";


    private int id;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private String password;

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " ("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_FIRSTNAME+ "TEXT,"
                    + COLUMN_LASTNAME + "TEXT,"
                    + COLUMN_EMAIL + "TEXT,"
                    + COLUMN_PHONE + "TEXT,"
                    + COLUMN_PASSWORD + "TEXT"
                    + ")";

    public Fb(){

    }

    public Fb(int id, String firstname, String lastname,
              String email, String phone, String password ){
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
