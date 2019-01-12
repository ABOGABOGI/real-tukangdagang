package tukangdagang.id.co.tukangdagang_koperasi.app;

public class Config {
    public static final String JSON_URL = "https://35utech.com/td/public/product";
    public static final String JSON_URL_STORES = "https://35utech.com/td/public/stores";
    public static final String path = "https://35utech.com/td/resources/images/";
    public static final String URLDaftar = "http://35utech.com/td/public/register";

    //URL to our login.php file
    public static final String LOGIN_URL = "http://192.168.48.1/insert/login.php";

    //Keys for email and password as defined in our $_POST['key'] in login.php
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";

    //If server response is equal to this that means login is successful
    public static final String LOGIN_SUCCESS = "success";

    //Keys for Sharedpreferences
    //This would be the name of our shared preferences
    public static final String SHARED_PREF_NAME = "myloginapp";

    //This would be used to store the email of current logged in user
    public static final String EMAIL_SHARED_PREF = "email";

    //We will use this to store the boolean in sharedpreference to track user is loggedin or not
    public static final String LOGGEDIN_SHARED_PREF = "loggedin";


}
