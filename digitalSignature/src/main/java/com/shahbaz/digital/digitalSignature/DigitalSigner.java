package com.shahbaz.digital.digitalSignature;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;

import org.jasypt.contrib.org.apache.commons.codec_1_3.binary.Base64;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;


/**
 * 
 * @author Shahbaz Khan
 * 04-Nov-2013
 *
 */
public class DigitalSigner {



	/**
	 * Password of pfx file
	 */
	String password;
	
	/**
	 * Message to be signed
	 */
			
	String message;
	
	/**
	 * Alias name of Pfx file
	 */
	String alias;
	
	/**
	 * The KeYStore of PFX file
	 */
	Resource keyStoreFile;

	KeyStore keystore;

	
	/**
	 * 
	 * Shahbaz Khan
	 * 04-Nov-2013 3:03:58 pm 
	 * void
	 */

	public static void main(String args[]) {
		
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/application-context.xml");
		SignatureRequirements sr= (SignatureRequirements)applicationContext.getBean("signatureRequirements");
		
		try {
			String signedMessage=new DigitalSigner().signMesage(sr);
			System.out.println(" ------------  Signed message  ------------");
			System.out.println(signedMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 
	 * Shahbaz Khan
	 * 04-Nov-2013 3:04:13 pm 
	 * String
	 */
	public String signMesage(SignatureRequirements sr) throws Exception{
		System.out.println(" Message is "+sr.getMessage());
		System.out.println(" Password is "+sr.getPassword());
		System.out.println(" Alias is "+sr.getAlias());
		System.out.println(" Keysorefile  is "+sr.getKeyStoreFile());
		
		
		message=sr.getMessage();

		password=sr.getPassword();
		alias=sr.getAlias();
		keyStoreFile=sr.getKeyStoreFile();
		
	

		 
        Certificate cert = null;
        KeyPair pair = null;
         keystore = getKeyStore();
                 
 
        keystore.load(keyStoreFile.getInputStream(), password.toCharArray());
 

 
        System.out.println(" KEY STORE IS -- " + keystore);
        Key key = keystore.getKey(alias, password.toCharArray());
        System.out.println(" KEY IS " + key.getEncoded());
        if (key instanceof PrivateKey) {
            cert = keystore.getCertificate(alias);
            PublicKey publicKey = cert.getPublicKey();
            pair = new KeyPair(publicKey, (PrivateKey) key);
        }
        Signature signature = Signature.getInstance("SHA256WITHRSA");
        signature.initSign((PrivateKey) key);
        signature.update(message.getBytes());
        byte[] signedMessage = signature.sign();
 
        System.out.println(" WRITING THE MESSAGE AGAIN");
        ByteArrayOutputStream byteMessage = new ByteArrayOutputStream();
      
        byteMessage.write(String.format("<CONTAINER>\n").getBytes());
        byteMessage.write("<ORIGINAL-MESSAGE>\n".getBytes());
        byteMessage.write(Base64.encodeBase64(message.getBytes(), true));
        byteMessage.write("</ORIGINAL-MESSAGE>\n".getBytes());
        byteMessage.write("<SIGNATURE>\n".getBytes());
        byteMessage.write(Base64.encodeBase64(signedMessage, true));
        byteMessage.write("</SIGNATURE>\n".getBytes());
        byteMessage.write("<CERTIFICATE>\n".getBytes());
        byteMessage.write(Base64.encodeBase64(cert.getEncoded(), true));
        byteMessage.write("<CERTIFICATE>\n".getBytes());
        byteMessage.write("</CONTAINER>\n".getBytes());
 
        System.out.println(" MESSGE SUCCESSFULLY WRITTEN");
        

		
		return byteMessage.toString();
	}
	
	/**
	 * 
	 * Shahbaz Khan
	 * 04-Nov-2013 3:04:23 pm 
	 * KeyStore
	 */
    public KeyStore getKeyStore() {
        System.out.println("Inside getKeyStore() method ");
        if (keystore == null) {
 
            try {
                keystore = KeyStore.getInstance("pkcs12");
            } catch (Exception e) {
                throw new RuntimeException(
                        "Unable to create a PKCS12 keystore", e);
            }
        }
 
        return keystore;
    }
 
    /**
     * 
     * Shahbaz Khan
     * 04-Nov-2013 3:04:29 pm 
     * KeyPair
     */
    private KeyPair getPrivateKey() {
        KeyPair result = null;
        KeyStore store = getKeyStore();
 
        try {
            store.load(keyStoreFile.getInputStream(), password.toCharArray());
            Key key = keystore.getKey(alias, password.toCharArray());
            if (key instanceof PrivateKey) {
                java.security.cert.Certificate cert = keystore
                        .getCertificate(alias);
                PublicKey publicKey = cert.getPublicKey();
                result = new KeyPair(publicKey, (PrivateKey) key);
            }
        } catch (UnrecoverableKeyException e) {
        } catch (NoSuchAlgorithmException e) {
        } catch (KeyStoreException e) {
        } catch (Exception e) {
        }
        return result;
    }

}
