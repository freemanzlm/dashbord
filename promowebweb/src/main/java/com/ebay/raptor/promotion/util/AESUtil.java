package com.ebay.raptor.promotion.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletResponse;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.slf4j.LoggerFactory;

import com.ebay.cbt.raptor.wltapi.util.AES;
import com.ebay.kernel.logger.LogLevel;
import com.ebay.kernel.logger.Logger;
import com.ebay.raptor.promotion.subsidy.controllers.SubsidyController;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.itextpdf.tool.xml.ElementList;

/**
 * @author pinchen
 *
 */
public class AESUtil {
	private static final String salt = "sixteenlengthstr";
	private static final Logger logger = Logger.getInstance(AESUtil.class);
	private static AES aes = new AES(salt);
	
	public static String encrypt(String content){
		String result = null;
		if(StringUtil.isEmpty(content)){
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
	
	public static String decrypt(String content){
		String result = null;
		if(StringUtil.isEmpty(content)){
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
	
}
