package com.xtuniversity.mall.model.base.util;

import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

/**
 * @author jtoms
 */
public class RandCodeUtil {

    public static String getSalt() {
        return get(8);
    }

    /**
     * 生成UUID
     *
     * @return
     */
    public static String getUUID() {
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replace("-", "");
        return uuid;
    }

    public static String getNumber(int len) {
        return get(len, 0);
    }

    public static String getLetter(int len) {
        return get(len, 1);
    }

    public static String get(int len) {
        return get(len, 2);
    }

    /**
     * 生成随机码
     *
     * @param len  长度
     * @param type 0-全数字，1-全字母，2数字与字母混合
     * @return
     */
    private static String get(int len, int type) {
        String[] codes = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
                "S", "T", "U", "V", "W", "X", "Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y",
                "z"};
        Random random = new SecureRandom();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < len; i++) {
            if (type == 0) {
                code.append(codes[random.nextInt(10)]);
            } else if (type == 1) {
                code.append(codes[10 + random.nextInt(codes.length - 10)]);
            } else {
                code.append(codes[random.nextInt(codes.length)]);
            }
        }
        return code.toString();
    }

    public static int getInt(int min, int max) {
        Random random = new SecureRandom();
        return min + random.nextInt(max - min);
    }
}
