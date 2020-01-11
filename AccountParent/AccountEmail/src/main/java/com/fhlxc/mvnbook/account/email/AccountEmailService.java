package com.fhlxc.mvnbook.account.email;

/**
* @author Xingchao Long
* @date 2019/56/28 23:56:04
* @ClassName AccountEmailService
* @Description 一个接口
*/

public interface AccountEmailService {
    void sendMail(String to, String subject, String htmlText);
}
