package com.fhlxc.mvnbook.account.captcha;

import java.util.Random;

/**
* @author Xingchao Long
* @date 2020/12/14 19:12:39
* @ClassName RandomGenerator
* @Description 类描述
*/

public class RandomGenerator {
    private static String range = "0123456789abcdefghijklmnopqrstuvwxyz";
    
    public static synchronized String getRandomString() {
        Random random = new Random();
        
        StringBuffer result = new StringBuffer();
        
        for (int i = 0; i < 8; i++) {
            result.append(range.charAt(random.nextInt(range.length())));
        }
        
        return result.toString();
    }
}
