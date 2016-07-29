package com.ebay.raptor.promotion.validation;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.multipart.MultipartFile;

import com.ebay.raptor.promotion.excep.AttachmentUploadException;
import com.ebay.raptor.promotion.util.AttachmentAllowedFileType;

/**
 * 
 * @author xiatu
 */
public class AttachmentFileValidator {
	private Logger logger = Logger.getLogger(AttachmentFileValidator.class.getName());
	private String bundleBaseName = "Message";
	private ResourceBundle bundle;
	private Locale locale;
	private static AttachmentFileValidator attachmentFileValidator = null;
	
	private AttachmentFileValidator() {}
	
	public static AttachmentFileValidator getInstance() {
		if(attachmentFileValidator == null) {
			attachmentFileValidator = new AttachmentFileValidator();
		}
		return attachmentFileValidator;
	}
	
	public  boolean isValidate(MultipartFile[] files) throws AttachmentUploadException {
		for(MultipartFile file : files) {
			return isValidate(file);
		}
		return true;
	}
	
	public  boolean isValidate(MultipartFile file) throws AttachmentUploadException{
		if(file.isEmpty()) {
			throw new AttachmentUploadException(getBundle().getString("attachment.validation.message.notnull"));
		}
		try {
			AttachmentAllowedFileType type = getType(file);
			if(type==null) {
				throw new AttachmentUploadException(getBundle().getString("attachment.validation.message.notcorrecttype"));
			}
		} catch (IOException e) {
			logger.log(Level.WARNING, e.getMessage());
		}
		return true;
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
	
	private static String getFileHeader(MultipartFile file) throws IOException {
		byte[] b = new byte[28];
		InputStream inputStream = null;
		inputStream = file.getInputStream();
		inputStream.read(b, 0, 28);
		inputStream.close();
		return bytes2hex(b);
	}
	
	public AttachmentAllowedFileType getType(MultipartFile file) throws IOException{
		String fileHead = getFileHeader(file);
		if(fileHead == null || fileHead.length() == 0) {
			return null;
		}
		fileHead = fileHead.toUpperCase();
		AttachmentAllowedFileType[] fileTypes = AttachmentAllowedFileType.values();
		for(AttachmentAllowedFileType type : fileTypes) {
			if(fileHead.startsWith(type.getValue())) {
				return type;
			}
		}
		return null;
	}
	
	public Locale getLocale() {
		return locale == null ? LocaleContextHolder.getLocale() : locale;
	}
	
	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	
	public ResourceBundle getBundle() {
		return bundle == null ? bundle = ResourceBundle.getBundle(bundleBaseName, Locale.SIMPLIFIED_CHINESE) : bundle;
	}

	public void setBundle(ResourceBundle bundle) {
		this.bundle = bundle;
	}

	public String getBundleBaseName() {
		return bundleBaseName;
	}

	public void setBundleBaseName(String bundleBaseName) {
		this.bundleBaseName = bundleBaseName;
	}
}
