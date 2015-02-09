package com.github.yablonski.majordom.auth;

import android.content.Context;
import android.provider.Settings;
import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * Created by Acer on 08.01.2015.
 */
public class EncryptManager {
    public static final String ENCODING = "UTF8";
    public static final String DES = "DES";
    private static String sSourceKey;
    private static byte[] sBytesKey;

    public static String encrypt(final Context context, final String value) throws Exception {
        sSourceKey = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        sBytesKey = sSourceKey.getBytes(ENCODING);
        final DESKeySpec keySpec = new DESKeySpec(sBytesKey);
        final SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        final SecretKey key = keyFactory.generateSecret(keySpec);
        final byte[] text = value.getBytes(ENCODING);
        final Cipher cipher = Cipher.getInstance(DES);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return Base64.encodeToString(cipher.doFinal(text), Base64.DEFAULT);

    }

    public static String decrypt(final Context context, final String value) throws Exception {
        sSourceKey = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        sBytesKey = sSourceKey.getBytes(ENCODING);
        final DESKeySpec keySpec = new DESKeySpec(sBytesKey);
        final SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        final SecretKey key = keyFactory.generateSecret(keySpec);
        final byte[] encrypedBytes = Base64.decode(value, Base64.DEFAULT);
        final Cipher cipher = Cipher.getInstance(DES);
        cipher.init(Cipher.DECRYPT_MODE, key);
        final byte[] plainTextBytes = cipher.doFinal(encrypedBytes);
        return new String(plainTextBytes);
    }
}
