package com.oozol.utils;

import com.oozol.exception.TechnicalException;

import java.security.MessageDigest;

/**
 * @Classname:
 * @Description:
 * @Date: 2019-04-29 10:14
 */
class MD5Utils {
    /**
     * Instance Variables
     */
    private static final char[] hexadecimal =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                    'a', 'b', 'c', 'd', 'e', 'f'};
    // Public Methods
    /**
     * Encodes the 128 bit (16 bytes) MD5 into a 32 character String.
     *
     * @param binaryData Array containing the digest
     * @return Encoded MD5, or null if encoding failed
     */
    public String encode( byte[] binaryData ) {
        if (binaryData.length != 16)
            return null;
        char[] buffer = new char[32];
        for (int i=0; i<16; i++) {
            int low = (int) (binaryData[i] & 0x0f);
            int high = (int) ((binaryData[i] & 0xf0) >> 4);
            buffer[i*2] = hexadecimal[high];
            buffer[i*2 + 1] = hexadecimal[low];
        }
        return new String(buffer);
    }

    public static String md5(String inStr) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("md5");
        } catch (Exception var8) {
            throw new TechnicalException("T1004", var8.getMessage());
        }

        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for(int i = 0; i < charArray.length; ++i) {
            byteArray[i] = (byte)charArray[i];
        }

        byte[] md5Bytes = md5.digest(byteArray);
        StringBuilder hexValue = new StringBuilder();

        for(int i = 0; i < md5Bytes.length; ++i) {
            int val = md5Bytes[i] & 255;
            if (val < 16) {
                hexValue.append("0");
            }

            hexValue.append(Integer.toHexString(val));
        }

        return hexValue.toString();
    }
}
