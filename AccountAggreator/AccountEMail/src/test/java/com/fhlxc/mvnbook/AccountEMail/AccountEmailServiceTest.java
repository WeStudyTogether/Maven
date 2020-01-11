package com.fhlxc.mvnbook.AccountEMail;

import com.fhlxc.mvnbook.AccountEMail.AccountEmailService;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetup;

import static junit.framework.Assert.assertEquals;

import javax.mail.Message;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
* @author Xingchao Long
* @date 2019/29/29 00:29:01
* @ClassName AccountEmailServiceTest
* @Description 测试邮件发送的类
*/

public class AccountEmailServiceTest {
    private GreenMail greenMail;
    
    @Before
    public void startMailServer() throws Exception {
        greenMail = new GreenMail(ServerSetup.SMTP);
        greenMail.setUser("z15881614642@163.com", "fhlxc447730");
        greenMail.start();
    }
    
    @Test
    public void testSendMail() throws Exception {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("account-email.xml");
        AccountEmailService accountEmailService =  (AccountEmailService) ctx.getBean("accountEmailService");
        
        String subject = "Test Subject";
        String htmlText = "<h3>Test</h3>";
        accountEmailService.sendMail("1771583929@qq.com", subject, htmlText);
        
        greenMail.waitForIncomingEmail(2000, 1);
        
        Message[] msgs = greenMail.getReceivedMessages();
        assertEquals(1, msgs.length);
        assertEquals(subject, msgs[0].getSubject());
        assertEquals(htmlText, GreenMailUtil.getBody(msgs[0]).trim());
    }
    
    @After
    public void stopMailServer() {
        greenMail.stop();
    }
}
