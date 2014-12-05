package com.github.yablonski.majordom.utils;

import com.github.yablonski.majordom.VkOAuthHelper;
import com.github.yablonski.majordom.auth.OAuthHelper;

/**
 * Created by Acer on 22.11.2014.
 */
public class AuthUtils {
    public static boolean isLogged() {
        return VkOAuthHelper.isLogged();
    }
}
