package com.github.yablonski.majordom;

/**
 * Created by Acer on 25.11.2014.
 */
public class Api {

    public static final String BASE_PATH = "http://melvillestrada.com/";
    public static final String VERSION_VALUE = "5.8";
    public static final String VERSION_PARAM = "v";

    //public static final String FRIENDS_GET = BASE_PATH + "friends.get?fields=photo_200_orig,online,nickname";
    public static final String FRIENDS_GET = "http://melville.webatu.com/lib/loginAndroid.php?username=consierge";
    public static final String NEWS_GET = BASE_PATH + "inc/newstojson.php";
    public static final String PACKAGES_GET = BASE_PATH + "inc/packagestojson.php";
}
