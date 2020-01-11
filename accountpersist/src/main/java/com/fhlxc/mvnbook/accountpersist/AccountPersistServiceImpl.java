package com.fhlxc.mvnbook.accountpersist;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
* @author Xingchao Long
* @date 2020/44/08 00:44:05
* @ClassName AccountPersistServiceImpl.java
* @Description 类描述
*/

public class AccountPersistServiceImpl implements AccountPersistService {
    private static final String ELEMENT_ROOT = "account-persist";
    private static final String ELEMENT_ACCOUNTS = "accounts";
    private static final String ELEMENT_ACCOUNT = "account";
    private static final String ELEMENT_ACCOUNT_ID = "id";
    private static final String ELEMENT_ACCOUNT_NAME = "name";
    private static final String ELEMENT_ACCOUNT_EMAIL = "email";
    private static final String ELEMENT_ACCOUNT_PASSWORD = "password";
    private static final String ELEMENT_ACCOUNT_ACTIVATED = "activated";
    
    private String file;
    private SAXReader reader = new SAXReader();
    
    private Document readDocument() {
        File dataFile = new File(file);
        if (!dataFile.exists()) {
            dataFile.getParentFile().mkdirs();
            Document doc = DocumentFactory.getInstance().createDocument();
            Element rootEle = doc.addElement(ELEMENT_ROOT);
            rootEle.addElement(ELEMENT_ACCOUNTS);
            writeDocument(doc);
        }
        
        try {
            return reader.read(new File(file));
        } catch (DocumentException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private void writeDocument(Document doc) {
        Writer out = null;
        
        try {
            out = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
            XMLWriter writer = new XMLWriter(out, OutputFormat.createPrettyPrint());
            writer.write(doc);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    private Account buildAccount( Element element )
    {
        Account account = new Account();

        account.setId( element.elementText( ELEMENT_ACCOUNT_ID ) );
        account.setName( element.elementText( ELEMENT_ACCOUNT_NAME ) );
        account.setEmail( element.elementText( ELEMENT_ACCOUNT_EMAIL ) );
        account.setPassword( element.elementText( ELEMENT_ACCOUNT_PASSWORD ) );
        account.setActivated( ( "true".equals( element.elementText( ELEMENT_ACCOUNT_ACTIVATED ) ) ? true : false ) );

        return account;
    }
    
    private Element buildAccountElement( Account account )
    {
        Element element = DocumentFactory.getInstance().createElement( ELEMENT_ACCOUNT );
        
        element.addElement( ELEMENT_ACCOUNT_ID ).setText( account.getId() );
        element.addElement( ELEMENT_ACCOUNT_NAME ).setText( account.getName() );
        element.addElement( ELEMENT_ACCOUNT_EMAIL ).setText( account.getEmail() );
        element.addElement( ELEMENT_ACCOUNT_PASSWORD ).setText( account.getPassword() );
        element.addElement( ELEMENT_ACCOUNT_ACTIVATED ).setText( account.isActivated() ? "true" : "false" );
        
        return element;
    }
    
    @Override
    public Account createAccount(Account account) {
        Document doc = readDocument();
        
        Element accountsEle = doc.getRootElement().element(ELEMENT_ACCOUNTS);
        
        accountsEle.add( buildAccountElement( account ) );
        
        writeDocument( doc );
        
        return account;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Account readAccount(String id) {
        Document doc = readDocument();
        Element accountsEle = doc.getRootElement().element(ELEMENT_ACCOUNTS);
        for (Element accountEle: (List<Element>) accountsEle.elements()) {
            if (accountEle.elementText(ELEMENT_ACCOUNT_ID).equals(id)) {
                return buildAccount(accountEle);
            }
        }
        return null;
    }

    @Override
    public Account updateAccount(Account account) {

        return null;
    }

    @Override
    public void deleteAccount(String id) {
    }

}
