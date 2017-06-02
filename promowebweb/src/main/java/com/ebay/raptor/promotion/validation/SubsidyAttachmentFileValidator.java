package com.ebay.raptor.promotion.validation;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.web.multipart.MultipartFile;

import com.ebay.raptor.promotion.enums.AttachmentAllowedFileType;
import com.ebay.raptor.promotion.enums.SubsidyAttachmentAllowedType;
import com.ebay.raptor.promotion.excep.AttachmentUploadException;
import com.ebay.raptor.promotion.util.LocaleUtil;

/**
 * 
 * @author xiatu
 */
public class SubsidyAttachmentFileValidator {
	private Logger logger = Logger.getLogger(SubsidyAttachmentFileValidator.class.getName());
	private String bundleBaseName = "Message";
	private ResourceBundle bundle;
	private Locale locale;
	private static SubsidyAttachmentFileValidator attachmentFileValidator = null;

	private SubsidyAttachmentFileValidator() {
	}

	public static SubsidyAttachmentFileValidator getInstance() {
		if (attachmentFileValidator == null) {
			attachmentFileValidator = new SubsidyAttachmentFileValidator();
		}
		return attachmentFileValidator;
	}

	public boolean validate(MultipartFile[] files) throws AttachmentUploadException {
		for (MultipartFile file : files) {
			return validate(file);
		}
		return true;
	}

	public boolean validate(MultipartFile file) throws AttachmentUploadException {
		if (file.isEmpty()) {
			throw new AttachmentUploadException(getBundle().getString("attachment.validation.message.notnull"));
		}
		if (file.getSize() > 5 * 1024 * 1024) {
			throw new AttachmentUploadException(getBundle().getString("attachment.validation.message.toolarge"));
		}
		try {
			SubsidyAttachmentAllowedType type = getType(file);
			if (type == null) {
				throw new AttachmentUploadException(getBundle().getString(
						"attachment.validation.message.notcorrecttype"));
			}
		} catch (IOException e) {
			logger.log(Level.WARNING, e.getMessage());
		}
		return true;
	}
	
	private static String bytes2hex(byte[] bytes) {
		StringBuffer hex = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			String temp = Integer.toHexString(bytes[i] & 0xFF);
			if (temp.length() == 1) {
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

	public SubsidyAttachmentAllowedType getType(MultipartFile file) throws IOException {
		String fileHead = getFileHeader(file);
		if (fileHead == null || fileHead.length() == 0) {
			return null;
		}
		fileHead = fileHead.toUpperCase();
		SubsidyAttachmentAllowedType[] fileTypes = SubsidyAttachmentAllowedType.values();
		for (SubsidyAttachmentAllowedType type : fileTypes) {
			if (fileHead.startsWith(type.getValue())) {
				if (type.toString().equalsIgnoreCase(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1))) {
					return type;
				}
			}
		}
		return null;
	}

	public Locale getLocale() {
		return locale == null ? LocaleUtil.getCurrentLocale() : locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public ResourceBundle getBundle() {
		return bundle == null ? bundle = ResourceBundle.getBundle(bundleBaseName, locale) : bundle;
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
