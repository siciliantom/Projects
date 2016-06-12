package by.bsu.hostel.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by Kate on 28.02.2016.
 */
public class MD5Util {
    public static String md5Apache(String st) {
        String md5Hex = DigestUtils.md5Hex(st);
        return md5Hex;
    }
}
