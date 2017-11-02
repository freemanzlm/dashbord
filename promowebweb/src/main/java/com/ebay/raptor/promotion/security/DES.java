package com.ebay.raptor.promotion.security;

import java.io.IOException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.ebay.kernel.util.FastURLEncoder;
import com.ebay.kernel.util.URLDecoder;

public class DES {
	private static final String DEFAULT_DESKEY = "wKz~9P_>";
	private static final String[] REPLACEMENT = { "<", ">", "//", "\\\\\\\\" };
	private static final String[] ENREPLACEMENT = { "/</", "/>/",
			"\\\\/\\\\/\\\\", "/\\\\/\\\\/" };
	private static final String[] DEREPLACEMENT = { "/ /", "/ /",
			"\\\\/\\\\/\\\\", "/\\\\/\\\\/" };
	
	private static DES des = null;
	
	public synchronized static DES getInstance () {
		if (des == null) {
			des = new DES();
		}
		
		return des;
	}

	private DES() {
		this.desKey = DEFAULT_DESKEY.getBytes();
	}

//	public DES(String desKey) {
//		this.desKey = desKey.getBytes();
//	}

	public byte[] desEncrypt(byte[] plainText) throws Exception {
		SecureRandom sr = new SecureRandom();
		byte rawKeyData[] = desKey;
		DESKeySpec dks = new DESKeySpec(rawKeyData);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey key = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.ENCRYPT_MODE, key, sr);
		byte data[] = plainText;
		byte encryptedData[] = cipher.doFinal(data);
		return encryptedData;
	}

	public byte[] desDecrypt(byte[] encryptText) throws Exception {
		SecureRandom sr = new SecureRandom();
		byte rawKeyData[] = desKey;
		DESKeySpec dks = new DESKeySpec(rawKeyData);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey key = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.DECRYPT_MODE, key, sr);
		byte encryptedData[] = encryptText;
		byte decryptedData[] = cipher.doFinal(encryptedData);
		return decryptedData;
	}

	public String encrypt(String input, boolean urlEncode) throws Exception {
		return encrypt(input, true, urlEncode);
	}
	
	public String encrypt(String input, boolean replace, boolean urlEncode) throws Exception {
		// Should encode the token in order to transfer through URL.
		String bas64Str = base64Encode(desEncrypt(input.getBytes()));
		// replace the string in case XssCheckUtil would take affect
		bas64Str = replace ? enReplace(bas64Str) : bas64Str;

		return urlEncode ? FastURLEncoder.encode(bas64Str, "UTF-8") : bas64Str;
	}
	
	public String encrypt2(String input) throws Exception {
		// Should encode the token in order to transfer through URL.
		byte[] enc = desEncrypt(input.getBytes("UTF8"));
		// replace the string in case XssCheckUtil would take affect
		
		return bytes2hex(enc);
	} 
	
	private static String bytes2hex(byte[] bytes) {
		StringBuffer hex = new StringBuffer();
		for(int i=0; i<bytes.length; i++) {
			String temp = Integer.toHexString(bytes[i] & 0xFF);
			if(temp.length() == 1) {
				hex.append("0");
			}
			hex.append(temp.toLowerCase());
		}
		return hex.toString();
	}

	public String decrypt(String input, boolean urlDecode) throws Exception {
		return decrypt(input, true, urlDecode);
	}
	
	public String decrypt(String input, boolean replace, boolean urlDecode) throws Exception {
		// decode url
		String str = urlDecode ? URLDecoder.decode(input) : input;
		// replace XssCheckUtil's change
		str = replace ? deReplace(str) : str;
		// decode base64
		byte[] result = base64Decode(str);
		return new String(desDecrypt(result));
	}

	public static String base64Encode(byte[] s) {
		if (s == null)
			return null;
		
		return Base64.encodeBase64String(s);
	}

	public static byte[] base64Decode(String s) throws IOException {
		if (s == null)
			return null;
		
		return Base64.decodeBase64(s);
	}

	/**
	 * This step is to avoid com.ebay.raptor.kernel.util.XssCheckUtil to parse
	 * the token in the httpRequest.
	 * 
	 * We simply replace the string which would be considered as illegal tags by
	 * XssCheckUtil.
	 */
	private String enReplace(String str) {
		if (str == null) {
			return null;
		}

		for (int i = 0; i < REPLACEMENT.length; i++) {
			str = str.replaceAll(REPLACEMENT[i], ENREPLACEMENT[i]);
		}
		return str;
	}

	/**
	 * This step is to avoid com.ebay.raptor.kernel.util.XssCheckUtil to parse
	 * the token in the httpRequest.
	 * 
	 * We simply revert the replacement to original string.
	 */
	private String deReplace(String str) {
		if (str == null) {
			return null;
		}

		for (int i = 0; i < DEREPLACEMENT.length; i++) {
			str = str.replaceAll(DEREPLACEMENT[i], REPLACEMENT[i]);
		}
		return str;
	}

	private byte[] desKey;
	public static void main(String[] args) throws Exception{
		System.out.println(base64Encode("best-shopping-mall".getBytes())); //aGVsbG8=
		String ecncrypted = DES.getInstance().encrypt2("best-shopping-mall");
		System.out.println(ecncrypted); //Dl3YGwmmKXU%3D
		
		ecncrypted = DES.getInstance().encrypt("best-shopping-mall", true);
		System.out.println(ecncrypted); //Dl3YGwmmKXU%3D
		
//		String descripted = DES.getInstance().de;
	}
}
