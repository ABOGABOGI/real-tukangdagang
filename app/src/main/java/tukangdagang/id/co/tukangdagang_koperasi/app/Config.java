package tukangdagang.id.co.tukangdagang_koperasi.app;

public class Config {
    public static final String JSON_URL = "https://35utech.com/td/public/product";
    public static final String URL_KOPERASI = "https://35utech.com/td/public/koperasi";
    public static final String URL_SLIDER = "https://35utech.com/td/public/slide";
    public static final String JSON_URL_STORES = "https://35utech.com/td/public/stores";
    public static final String path = "https://35utech.com/td/public/upload/logokoperasi/";
    public static final String path_slider = "https://35utech.com/td/public/upload/slide/";
    public static final String pathKoperasi = "https://35utech.com/td/public/upload/gambarkoperasi/";
    public static final String URLDaftar = "http://35utech.com/td/public/register";
    public static final String URLLoginWith = "http://35utech.com/td/public/loginwith";
    public static final String URLUpload = "http://35utech.com/td/public/uploadktp";
    public static final String URLGantipwd = "http://35utech.com/td/public/Gantipwd";
    public static final String URL_ID_KOPERASI = "http://35utech.com/td/public/IdKoperasi";
    public static final String URL_IMG_KOPERASI = "http://35utech.com/td/public/ImgKoperasi";
    public static final String URL_PROFILE = "http://35utech.com/td/public/getProfile";

    //URL to our login.php file
    public static final String LOGIN_URL = "http://35utech.com/td/public/login";

    //Keys for email and password as defined in our $_POST['key'] in login.php
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";

    //If server response is equal to this that means login is successful
    public static final String LOGIN_SUCCESS = "success";
    public static final String LOGIN_FAILURE = "failure";

    //Keys for Sharedpreferences
    //This would be the name of our shared preferences
    public static final String SHARED_PREF_NAME = "myloginapp";

    //This would be used to store the email of current logged in user
    public static final String PROFILE_ID = "id_profile";
    public static final String EMAIL_SHARED_PREF = "email";
    public static final String NAME_SHARED_PREF = "nama";
    public static final String LOGINWITH_SHARED_PREF = "loginwith";
    public static final String IMAGE_SHARED_PREF = "gambar";
    public static final String n_info_nama_depan = "info_nama_depan";
    public static final String n_info_nama_belakang = "info_nama_belakang";
    public static final String n_info_jk = "info_jk";
    public static final String n_info_alamat = "info_alamat";
    public static final String n_info_rtrw = "info_rtrw";
    public static final String n_info_kodepos = "info_kodepos";
    public static final String n_info_provinsi = "info_provinsi";
    public static final String n_info_kota = "info_kota";
    public static final String n_info_kecamatan = "info_kecamatan";
    public static final String n_info_noktp = "info_noktp";
    public static final String n_info_nokk = "info_nokk";
    public static final String n_info_nohp = "info_nohp";
    public static final String n_imagePreferance = "imagePreferance";
    public static final String n_imagePreferance2 = "imagePreferance2";
    public static final String n_imagePreferance3 = "imagePreferance3";
    public static final String n_info_status = "info_status";
    public static final String n_status_nomor = "status_nomor";
    public static final String n_status_upload = "status_upload";
    public static final String n_info_refferal = "n_info_refferal";

    //We will use this to store the boolean in sharedpreference to track user is loggedin or not
    public static final String LOGGEDIN_SHARED_PREF = "loggedin";


}
