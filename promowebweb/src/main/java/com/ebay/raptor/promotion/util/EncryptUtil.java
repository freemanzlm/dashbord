package com.ebay.raptor.promotion.util;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.ebay.cbt.raptor.wltapi.util.AES;
import com.ebay.kernel.logger.Logger;

/**
 * @author pinchen
 *
 */
public class EncryptUtil {
	private static final String salt = "sixteenlengthstr";
	private static final Logger logger = Logger.getInstance(EncryptUtil.class);
	private static AES aes = new AES(salt);
	
	/**
	 * ENCRYPT with AES
	 * return string may contain "+" "/" "=" and so on 
	 * @param content
	 * @return
	 */
	public static String encrypt(String content) {
		String result = null;
		if (StringUtil.isEmpty(content)) {
			return result;
		}
		try {
			result = aes.encryptWithECB_PKCS5Padding(content);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} 
		return result;
	}
	
	/**
	 * DECRYPT with AES
	 * @param content
	 * @return
	 */
	public static String decrypt(String content) {
		String result = null;
		if (StringUtil.isEmpty(content)) {
			return result;
		}
		try {
			result = aes.decryptWithECB_PKCS5Padding(content);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void main(String[] args) {
		String res = "11111123123";
		System.out.println(encrypt(res));
		String ret = URLEncoder.encode(encrypt(res));
		System.out.println(ret);
		String bakString = URLDecoder.decode(ret);
		System.out.println(bakString);
		String x = decrypt(bakString);
		System.out.println(x);
	}

}
