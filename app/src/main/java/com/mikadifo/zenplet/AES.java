package com.mikadifo.zenplet;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AES {

    private static byte[] keyValue = new byte[]{0x07, 0x0a, 0x06, 0x05, 0x06, 0x0e, 0x07, 0x00, 0x06, 0x0c, 0x06, 0x05, 0x07, 0x04, 0x05, 0x0a};

    public static String encrypt(String strToEncrypt) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(keyValue, "AES"));
            return Base64.encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")), Base64.NO_WRAP);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}