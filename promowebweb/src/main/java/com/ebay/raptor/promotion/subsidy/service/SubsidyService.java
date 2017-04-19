package com.ebay.raptor.promotion.subsidy.service;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import net.sf.json.JSONObject;

import org.ebayopensource.ginger.client.GingerClientResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.ebay.app.raptor.promocommon.CommonLogger;
import com.ebay.cbt.raptor.promotion.po.Subsidy;
import com.ebay.cbt.raptor.promotion.po.SubsidyAttachment;
import com.ebay.cbt.raptor.promotion.po.SubsidyCustomField;
import com.ebay.cbt.raptor.promotion.po.SubsidyLegalTerm;
import com.ebay.cbt.raptor.promotion.po.SubsidySubmission;
import com.ebay.cbt.raptor.promotion.po.WLTAccount;
import com.ebay.cbt.raptor.promotion.route.ResourceProvider;
import com.ebay.kernel.util.URLDecoder;
import com.ebay.raptor.promotion.excep.PromoException;
import com.ebay.raptor.promotion.pojo.service.resp.BaseServiceResponse.AckValue;
import com.ebay.raptor.promotion.pojo.service.resp.GeneralDataResponse;
import com.ebay.raptor.promotion.service.BaseService;
import com.ebay.raptor.promotion.util.StringUtil;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;

@Component
public class SubsidyService extends BaseService {
	private CommonLogger logger = CommonLogger
			.getInstance(SubsidyService.class);

	@Autowired
	ResourceBundleMessageSource msgResource;
	private Locale locale;

	private String url(String url) {
		return secureUrl(ResourceProvider.SubsidyRes.base) + url;
	}

	/**
	 * Get subsidy detail.
	 * 
	 * @param promoId
	 * @param userId
	 * @return
	 * @throws PromoException
	 */
	public Subsidy getSubsidy(String promoId, Long userId)
			throws PromoException {
		String uri = url(params(ResourceProvider.SubsidyRes.getSubSidy,
				new Object[] { "{promoId}", promoId, "{uid}", userId }));
		GingerClientResponse resp = httpGet(uri);
		if (Status.OK.getStatusCode() == resp.getStatus()) {
			GenericType<GeneralDataResponse<Subsidy>> type = new GenericType<GeneralDataResponse<Subsidy>>() {
			};
			GeneralDataResponse<Subsidy> response = resp.getEntity(type);
			if (null != response && AckValue.SUCCESS == response.getAckValue()) {
				return response.getData();
			} else {
				if (null != response && null != response.getErrorMessage()
						&& null != response.getErrorMessage().getError()) {
					throw new PromoException(response.getErrorMessage()
							.getError().toString());
				}
			}
		} else {
			throw new PromoException("Internal Error happens.");
		}
		return null;
	}

	/**
	 * 
	 * @param paymentType
	 * @return
	 * @throws PromoException
	 */
	public SubsidyLegalTerm getSubsidyLegalTerm(Integer paymentType,
			String country) throws PromoException {
		String uri = url(params(
				ResourceProvider.SubsidyRes.getSubsidyLegalTerm, new Object[] {
						"{paymentType}", paymentType, "{country}", country }));
		GingerClientResponse resp = httpGet(uri);
		if (Status.OK.getStatusCode() == resp.getStatus()) {
			GenericType<GeneralDataResponse<SubsidyLegalTerm>> type = new GenericType<GeneralDataResponse<SubsidyLegalTerm>>() {
			};
			GeneralDataResponse<SubsidyLegalTerm> response = resp
					.getEntity(type);
			if (null != response && AckValue.SUCCESS == response.getAckValue()) {
				return response.getData();
			}
		} else {
			throw new PromoException("Internal Error happens.");
		}
		return null;
	}

	public SubsidySubmission getSubsidySubmission(String promoId, Long id)
			throws PromoException {
		String uri = url(params(
				ResourceProvider.SubsidyRes.getSubsidySubmission, new Object[] {
						"{promoId}", promoId, "{id}", id }));
		GingerClientResponse resp = httpGet(uri);
		if (Status.OK.getStatusCode() == resp.getStatus()) {

			GenericType<GeneralDataResponse<SubsidySubmission>> type = new GenericType<GeneralDataResponse<SubsidySubmission>>() {
			};
			GeneralDataResponse<SubsidySubmission> response = resp
					.getEntity(type);
			if (null != response && AckValue.SUCCESS == response.getAckValue()) {
				return response.getData();
			}

		} else {
			throw new PromoException("Internal Error happens.");
		}
		return null;
	}

	public boolean updateSubsidySubmission(SubsidySubmission subsidySubmission)
			throws PromoException {
		boolean flag = false;
		String uri = url(params(ResourceProvider.SubsidyRes.updateSubsidySubmission));
		GingerClientResponse resp = httpPost(uri, subsidySubmission);
		if (Status.OK.getStatusCode() == resp.getStatus()) {
			GenericType<GeneralDataResponse<SubsidySubmission>> type = new GenericType<GeneralDataResponse<SubsidySubmission>>() {
			};
			GeneralDataResponse<SubsidySubmission> response = resp
					.getEntity(type);
			if (null != response && AckValue.SUCCESS == response.getAckValue()) {
				flag = true;
			}
		} else {
			throw new PromoException("Internal Error happens.");
		}
		return flag;
	}

	/**
	 * get the filling info from the subsidy submission and convert into the
	 * subsidyLegalTerm
	 * 
	 * @return
	 */
	public SubsidyLegalTerm convertSubmissionToLegalTerm(
			SubsidyLegalTerm subsidyLegalTerm,
			SubsidySubmission subsidySubmission) {
		if (subsidySubmission == null
				|| StringUtil.isEmpty(subsidySubmission.getContent())) {
			return subsidyLegalTerm;
		}
		List<SubsidyCustomField> subsidyCustomFields = subsidyLegalTerm
				.getSubsidyCustomFields();
		String content = subsidySubmission.getContent();

		JSONObject json = JSONObject.fromObject(content);

		for (SubsidyCustomField subsidyField : subsidyCustomFields) {
			if (json.containsKey(subsidyField.getKey())) {
				subsidyField.setValue(json.getString(subsidyField.getKey()));
			}
		}

		return subsidyLegalTerm;
	}

	/**
	 * Split custom fields into two kinds of fields: these for upload, and the
	 * others for non upload.
	 * 
	 * The first element of the returned array is the fields for non upload.
	 * 
	 * @param term
	 * @return
	 */
	public ArrayList<SubsidyCustomField>[] splitCustomFields(
			SubsidyLegalTerm term) {
		@SuppressWarnings("unchecked")
		ArrayList<SubsidyCustomField>[] array = (ArrayList<SubsidyCustomField>[]) Array
				.newInstance(ArrayList.class, 2);

		ArrayList<SubsidyCustomField> nonuploadFields = new ArrayList<SubsidyCustomField>();
		ArrayList<SubsidyCustomField> uploadFields = new ArrayList<SubsidyCustomField>();

		List<SubsidyCustomField> allFields = term.getSubsidyCustomFields();

		for (SubsidyCustomField field : allFields) {
			if (field.isUpload) {
				uploadFields.add(field);
			} else {
				nonuploadFields.add(field);
			}
		}

		array[0] = nonuploadFields;
		array[1] = uploadFields;

		return array;
	}

	/**
	 * Upload listing attachment into database.
	 * 
	 * @param listingId
	 * @param uploadFile
	 * @param fileType
	 * @return
	 * @throws
	 * @throws PromoException
	 * @throws IOException
	 */
	public String uploadSubsidyAttachment(String promoId, Long userId,
			String key, final MultipartFile uploadFile, String fileType)
			throws Exception {
		String url = url(ResourceProvider.SubsidyRes.uploadAttachment);
		// this method will response a the file id;
		String urlbak = url(ResourceProvider.SubsidyRes.uploadAttachmentBak);
		FormDataMultiPart multiPart = new FormDataMultiPart();
		File file = multipartToFile(uploadFile);
		FileDataBodyPart fileDataBodyPart = new FileDataBodyPart("file", file,
				MediaType.APPLICATION_OCTET_STREAM_TYPE);
		multiPart.bodyPart(fileDataBodyPart);
		multiPart.field("promoId", promoId);
		multiPart.field("userId", Long.toString(userId));
		multiPart.field("fileType", fileType);
		multiPart.field("key", key);
		String fileNameString = decodefilePathOrfileName(file.getName());
		String fileName = fileNameString.substring(0,fileNameString.lastIndexOf("."));
		multiPart.field("fileName", fileName);
		// GingerClientResponse resp = uploadMultipart(url, multiPart);
		// if (Status.OK.getStatusCode() == resp.getStatus()) {
		// GenericType<GeneralDataResponse<Boolean>> type = new
		// GenericType<GeneralDataResponse<Boolean>>() {};
		// GeneralDataResponse<Boolean> general = resp.getEntity(type);
		// if (null != general) {
		// if (AckValue.SUCCESS == general.getAckValue()) {
		// if(general.getData()){
		// return params("downloadAttachment", new Object[] { "{promoID}",
		// promoId, "{userId}", userId,
		// "{key}", key });
		// }
		// }
		// }
		// } else {
		// CalEventHelper.sendImmediate("ERROR", "SubsidyException", "1",
		// "promoId=" + promoId + ",userId=" + userId
		// + ",key=" + key + ",fileType=" + fileType);
		// }
		// return null;

		GingerClientResponse resp = uploadMultipart(urlbak, multiPart);
		if (Status.OK.getStatusCode() == resp.getStatus()) {
			GenericType<GeneralDataResponse<Long>> type = new GenericType<GeneralDataResponse<Long>>() {
			};
			GeneralDataResponse<Long> ret = resp.getEntity(type);
			if (null != ret) {
				if (AckValue.SUCCESS == ret.getAckValue()) {
					if (null != ret.getData()) {
						return params("downloadAttachment", new Object[] {
								"{id}", ret.getData() });
					}
				}
			}
		}
		return null;

	}

	/**
	 * Download attachment.
	 * 
	 * @param promoId
	 * @param userId
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public SubsidyAttachment downloadSubsidyAttachment(String promoId,
			Long id, String key) throws Exception {
		String url = url(params(ResourceProvider.SubsidyRes.downloadAttachment,
				new Object[] { "{promoId}", promoId, "{id}", id,
						"{key}", key }));
		GingerClientResponse resp = httpGet(url);
		if (Status.OK.getStatusCode() == resp.getStatus()) {
			GenericType<GeneralDataResponse<SubsidyAttachment>> type = new GenericType<GeneralDataResponse<SubsidyAttachment>>() {
			};
			GeneralDataResponse<SubsidyAttachment> response = resp
					.getEntity(type);
			if (null != response && AckValue.SUCCESS == response.getAckValue()) {
				return response.getData();
			}
		} else {
			throw new PromoException("Internal Error happens.");
		}
		return null;
	}
	
	/**
	 * Download attachment.
	 * 
	 * @param promoId
	 * @param userId
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public SubsidyAttachment downloadSubsidyAttachment(Long id) throws Exception {
		String url = url(params(ResourceProvider.SubsidyRes.downloadAttachmentById,	new Object[] { "{id}", id}));
		GingerClientResponse resp = httpGet(url);
		if (Status.OK.getStatusCode() == resp.getStatus()) {
			GenericType<GeneralDataResponse<SubsidyAttachment>> type = new GenericType<GeneralDataResponse<SubsidyAttachment>>() {
			};
			GeneralDataResponse<SubsidyAttachment> response = resp
					.getEntity(type);
			if (null != response && AckValue.SUCCESS == response.getAckValue()) {
				return response.getData();
			}
		} else {
			throw new PromoException("Internal Error happens.");
		}
		return null;
	}
	
	/**
	 * get WLTAccount by ebayID
	 * 
	 * @param promoId
	 * @param userId
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public WLTAccount getWLTAccount(String ebayId) throws Exception {
		String url = url(params(ResourceProvider.SubsidyRes.getWLTAccount,	new Object[] { "{ebayId}", ebayId}));
		GingerClientResponse resp = httpGet(url);
		if (Status.OK.getStatusCode() == resp.getStatus()) {
			GenericType<GeneralDataResponse<WLTAccount>> type = new GenericType<GeneralDataResponse<WLTAccount>>() {};
			GeneralDataResponse<WLTAccount> response = resp.getEntity(type);
			if (null != response && AckValue.SUCCESS == response.getAckValue()) {
				return response.getData();
			}
		} else {
			throw new PromoException("Internal Error happens.");
		}
		return null;
	}
	
	/**
	 * bind wltaccount with ebayid
	 * 
	 * @param promoId
	 * @param userId
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public boolean saveWLTAccount(String ebayId,String wltId) throws Exception {
		boolean flag = false;
		String url = url(params(ResourceProvider.SubsidyRes.saveWLTAccount,	new Object[] { "{ebayId}", ebayId,"{wltId}",wltId}));
		GingerClientResponse resp = httpGet(url);
		if (Status.OK.getStatusCode() == resp.getStatus()) {
			GenericType<GeneralDataResponse<Boolean>> type = new GenericType<GeneralDataResponse<Boolean>>() {};
			GeneralDataResponse<Boolean> response = resp.getEntity(type);
			if (null != response && AckValue.SUCCESS == response.getAckValue()) {
				flag = response.getData();
			}
		} else {
			throw new PromoException("Internal Error happens.");
		}
		return flag;
	}

	private File multipartToFile(MultipartFile multipartFile)
			throws IllegalStateException, IOException {
		File file = new File(multipartFile.getOriginalFilename());
		multipartFile.transferTo(file);
		return file;
	}

	private String decodefilePathOrfileName(String value) {
		try {
			return URLDecoder.decode(new String(value.getBytes(), "UTF-8"),
					"UTF-8");
		} catch (UnsupportedEncodingException e) {
			return value;
		}
	}

	public Locale getLocale() {
		return locale == null ? LocaleContextHolder.getLocale() : locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	private String getMessage(String key) {
		return msgResource.getMessage(key, null, getLocale());
	}
}
