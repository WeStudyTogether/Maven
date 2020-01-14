package com.fhlxc.mvnbook.account.captcha;

import java.util.List;

/**
* @author Xingchao Long
* @date 2020/26/14 16:26:03
* @ClassName AccountCaptchaService
* @Description 类描述
*/

public interface AccountCaptchaService {
    String generateCaptchaKey();
    byte[] generateCaptchaImage(String captchaKey);
    boolean validateCaptcha(String captchaKey, String captchaValue);
    List<String> getPreDefinedTexta();
    void setPreDefinedTexts(List<String> preDefinedTexts);
}
