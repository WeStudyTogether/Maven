package com.fhlxc.mvnbook.account.captcha;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.imageio.ImageIO;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

/**
* @author Xingchao Long
* @date 2020/17/14 19:17:45
* @ClassName AccountCaptchaServiceImpl
* @Description 类描述
*/

public class AccountCaptchaServiceImpl implements AccountCaptchaService {
    private DefaultKaptcha producer;

    private Map<String, String> captchaMap = new HashMap<String, String>();

    private List<String> preDefinedTexts;

    private int textCount = 0;

    public void afterPropertiesSet()
        throws Exception
    {
        producer = new DefaultKaptcha();

        producer.setConfig( new Config( new Properties() ) );
    }
    
    private String getCaptchaText()
    {
        if ( preDefinedTexts != null && !preDefinedTexts.isEmpty() )
        {
            String text = preDefinedTexts.get( textCount );

            textCount = ( textCount + 1 ) % preDefinedTexts.size();

            return text;
        }
        else
        {
            return producer.createText();
        }
    }
    
    @Override
    public String generateCaptchaKey() {
        String key = RandomGenerator.getRandomString();

        String value = getCaptchaText();

        captchaMap.put( key, value );

        return key;
    }

    @Override
    public byte[] generateCaptchaImage(String captchaKey) {
        String text = captchaMap.get( captchaKey );

        if ( text == null )
        {
            return null;
        }

        BufferedImage image = producer.createImage( text );

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try
        {
            ImageIO.write( image, "jpg", out );
        }
        catch ( IOException e )
        {
            
        }

        return out.toByteArray();
    }

    @Override
    public boolean validateCaptcha(String captchaKey, String captchaValue) {
        String text = captchaMap.get( captchaKey );

        if ( text == null )
        {
            return false;
        }

        if ( text.equals( captchaValue ) )
        {
            captchaMap.remove( captchaKey );

            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public List<String> getPreDefinedTexta() {
        return preDefinedTexts;
    }

    @Override
    public void setPreDefinedTexts(List<String> preDefinedTexts) {
        this.preDefinedTexts = preDefinedTexts;
    }

}
