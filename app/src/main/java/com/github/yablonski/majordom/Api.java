package com.github.yablonski.majordom;

/**
 * Created by Acer on 25.11.2014.
 */
public class Api {

    public static final String BASE_PATH = "http://melvillestrada.com";
    public static final String TYPE = "?type=";
    public static final String REQUEST = "&message=";
    public static final String NEWS_GET = BASE_PATH + "/inc/newstojson.php";
    public static final String REPORTS_GET = BASE_PATH + "/inc/orderstojson.php";
    public static final String PACKAGES_GET = BASE_PATH + "/inc/packagestojson.php";
    public static final String REDIRECT_URL = BASE_PATH + "/blank.html";
    public static final String AUTHORIZATION_URL = BASE_PATH + "/lib/userAndroid.php";
    public static final String PROFILE_GET = BASE_PATH + "/inc/usertojson.php";

}
