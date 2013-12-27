package com.shahbaz.digital.digitalSignature;

import org.jasypt.contrib.org.apache.commons.codec_1_3.binary.Base64;

/**
 * 
 * @author shabby
 *
 */
public class DecodingBase64Message {

	public static void main(String[] args) {
		
		/**
		 * This Base64 Encoded
		 */
		String message="SGVsbG8gd29ybGQgISBJIHdhbnQgdG8gYmUgc2lnbmVk";
		
		System.out.println(" Messag was  -- >>  "+new DecodingBase64Message().decodeBase64(message));
	}
	
	/**
	 * Decode the Base64 Message 
	 * 
	 * @param Base64 message
	 * @return Decoded Message
	 */
	public String decodeBase64(String message){
		
		return new String(Base64.decodeBase64(message.getBytes()));
	}
	
}
