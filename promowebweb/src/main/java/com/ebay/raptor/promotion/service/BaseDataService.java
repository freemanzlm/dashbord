package com.ebay.raptor.promotion.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebay.app.raptor.cbtcommon.util.EnviromentUtil;
import com.ebay.app.raptor.promocommon.CommonLogger;
import com.ebay.app.raptor.promocommon.httpRequest.HttpRequestException;
import com.ebay.app.raptor.promocommon.httpRequest.HttpRequestService;
import com.ebay.app.raptor.promocommon.httpRequest.HttpResponseData;
import com.ebay.app.raptor.promocommon.pojo.db.Audit;
import com.ebay.app.raptor.promocommon.pojo.db.AuditType;
import com.ebay.app.raptor.promocommon.pojo.db.Parameter;
import com.ebay.app.raptor.promocommon.pojo.db.ParameterType;
import com.ebay.app.raptor.promocommon.util.CommonConstant;
import com.ebay.kernel.util.FastURLEncoder;
import com.ebay.raptor.promotion.config.AppConfig;
import com.google.gson.reflect.TypeToken;

@Service
public class BaseDataService {
    protected static final String POST_METHOD = "POST";
    protected static final String GET_METHOD = "GET";
    protected static final String NO_ENCRIPTION_PARAM = "?encrypt=false";

    protected static String selectSdParameterUrl = "listing/parameter/";
    protected static String updateSdParameterUrl = "listing/parameter/save";
    protected static String selectSdAuditUrl = "listing/audit/";
    protected static String insertSdAuditUrl = "listing/audit/save";
    protected static String authorization;
    
    // biz report service
    private static String isAbleToAccessBizUrl;
    protected static String bizAuthorization;
    
    protected static final CommonLogger logger = CommonLogger
            .getInstance(BaseDataService.class);

    @Autowired
    protected HttpRequestService httpRequestService;

    static {
        if (EnviromentUtil.isProduction()) {
        	isAbleToAccessBizUrl = "http://www.bizser.stratus.ebay.com/br/v1/userValidation/user/";
        	bizAuthorization = "";
            authorization = "IAF v^1.1#i^1#p^1#d^2015-05-27T22:24:47.778Z#r^0#I^2#f^0#t^H4sIAAAAAAAAAKVUfWgbZRhvPtpRstY/VnTrqsbbpq7zkvcud/k4mtSs7dbYbKtLUruOQu8ub5pzubt47xvbVBhd3QqOTkeLWkRHB1OKQxwDddT5NRgo6Cb4iTClf2hdWVXKYGVS5+WaZknVKnh/vMf7Pr/nfX7P73neBwxWVNYPtw7fqDKtMU8MgkGzyUTZQGVF+bZqi7m2vAwUAUwTg5sHrUOWnxsQL6fS3F6I0qqCoD3U7CcSgg+KdILnodfnYmmRsIeUZUBU9ROMh3bTLOMTfYLgdbOUbkcoA0MKwryC/QQNKBdJUSTtjgKKoxmO8Tg8Hm8XYe+AGpJURYc4AGHvl1MK4gwCfiKjKZzKIwlxCi9DxGGRiwR3hTkdyaU1FauimiICBl3OCKcV+a/uziMENazHJQKh4I6mSIOz6JZAXoEI5nEGle6a1Di0d/CpDFw9ADLQXCQjihAhwhlYilB6KRdcpmGITPMsKwIgCD4Rej3u+P8X8T+LUCLiDlWTeby6b+5EipMJA8pBBUs4+89a6joIj0MR53e7dedQsz33ezTDp6SEBDU/0bI9uC/Y3k4EcnGhwGdJmdcOQJxO8SIkRb3VMjLUpDgn+lgoeDwC6RUTbpJxUV7S59UX3uUVGcHFxt1xNk9iKVJe/BUsmlQlLuXSR/bdKt4O9VzgSpGpIpF10B5ljxZM4BxbHceSgCVpT5Smi4vhXK5tBieVXLWhrMtjN7b/XsqCN8aaJGQwLNyw0mDI5yf4dFqKEyuNRofmG6Ef+YkkxmnO6ezr63P0uRyq1uukgfXQBUA5O3eFI2ISyjxRgEt/jy8Gk5KRiQh1LyRxOJvWqfTrTafHV3qJAOWiGNaVl72UVWDl6V8OilJ2lj6Twitanj2BMuOjhkzvgyHTlD7fgBc4qIdAfYUlZrWs3YQkDB0Sn3CIyIGkXkV/fBp0HIDZNC9pHEcDxmeuMDXGb9T0FM3FiW6wvjAZKy2UrWhMgrrblnLqjruq9Fwp2g0ommE8XWDTbauVutNasz41s2XtH5Fzctn0m8nTo68uXAMPgKoCyGQqL7MOmcpivt6veuLtZ64fZBYrLm8Ud5odv1XXfbr3xY5zVOyjU1O36Fu15082Zn88/sLF+x4uj8T2v/tsrPWV5qvM7L6Np8O1MZvtl3cuytEvLj3ff/KxxYHJ0YWWe61qdLY+8vvRl+em939+/xuzzYezj6Q/aVkIN2577ubbZ8bGBo5fmahvmxiemr/y2ejZE52HvvPboutaB6yXD9/z9YXrVzts4ymt84nkpZda5oM/PXUk88zowMFj61T7+Mfz3xy5tnXN4luvVQs1ZzfsfHDmaDjyuqWr58PumcnIt23MmHnrD+a2pxu6p+/ectM3xzqPfbl5cfKDubqR73vr6mLvjZ9vOzES+nWk9cnqmLzh1FId/wTAAj0GsQYAAA==";
        } else {
        	isAbleToAccessBizUrl = "http://sdbrws.stratus.qa.ebay.com/br/v1/userValidation/user/";
        	bizAuthorization = "";
            authorization = "IAF v^1.1#i^1#I^2#f^0#p^1#d^2016-10-25T09:50:56.462Z#r^0#t^H4sIAAAAAAAAAKVUXWgcVRTO7E8kTVOLfxGpsk6t2NSZuXd2ZrJzySzdbBtcbPrjpukP+nB35m4yZueHuXdJQlG2QSoq+FIQFakBlYAQRLC1pS8VjPigqBRtHwTFJ6nQB0Hto3cnm+3uSqPgPNzh3vOde77znXMPaPQPjJx56sxfQ8JdieUGaCQEAQ6Cgf70nm3JxEPpPtABEJYbjzVSS8lfxyj2aiF6htAw8CnJlPZZYs7BOhjVCXAcDaqmI2ZK/gZgKrDEUYPYOQJNFejQqBhZbqe0Tko+ZdhnlqgCqEtAk1RjCqpIB0g3ZM1QT4qZaRJRN/A5RAZiZsGr+RTFBCyxHvkowNSlyMceoYjZqFyYPIA4EoVRwAI7qIn5mC6Kw0Ud/pu7Y0pJxHhcMV8qTBTLY0rHLfmWAmWGWZ1274qBQzLTuFYnmwegMRqV67ZNKBWV/HqE7ktRYYNGLHIVa7aJTcOBwDZ18/9r+J816NJwIog8zDb3bZ64jlSNoYj4zGWLd5aSy1B5ntistTvInUv7Ms3fkTquuVWXRJa4f7xwonD4sJhvxiUVvCh5OJojLKxhm0g277S6RyLXQVwbUhkdrUg5u2pIWhbmJDPHF5zN2VolqzuGo7dIrEdqad/Dohj4jttMn2YOBmyc8FxIr8hah8gcdMg/FBWqrMmW4wwJAknVp4DZWQxlo7R1Nus3i008Lk8m3v57KdvejEVupc5I+4ZeQyyfJeIwdB2x1xg3aKsRFqglzjIWIkWZn5+X57NyEM0oKgCp01egcnzyQNmeJR4W23D3DvgOsOTGmdiEe1EXscWQU1ngTcfj+zNiXjOyZq6lejepfO/pPw46Mla6H0n7DW1Mnnxf/MElwQJLgsmnG8gBGT4JRvqTR1PJrTupy4js4qpsU5m6Mz5/ehGR58hiiN0IITNrqol+oTD2sv5tx1Rcfg482J6LA0k42DEkwY7bljS8e3gI6kBTDajqQDdOgp23rSn4QOq+cA7+uf2RUxNfnvvupnjt7PaVN++5DIbaIEFI96VON148tTKd//rdo1+cn9wr/1De+tt15fhaefgdvfLjiceveL+/9f0T2xqfDd7Y8vM3CUu7eqv40se18yupsY/2v3/p892v/bS2540jz3517d7XVy+cffSFq+O5qV1p+cPhva+uGm//kvzklfeW2adrpR3WscveH6tLIwM3PzhX3OLe2rX7wsMXwxtP378u49+iJ9FVLgYAAA==";
        }
    }

    protected String buildServiceUrl(String host, String baseUrl, Object... args){
        if(null != host && !"".equals(host)){
            for(int pos = 0; pos < args.length; pos = pos + 2){
                if(null == args[pos + 1]){
                    continue;
                }
                baseUrl = baseUrl.replace(args[pos].toString(), args[pos + 1].toString());
            }

            return host + baseUrl;
        } else {
            throw new RuntimeException("Failed to build the URL for " + baseUrl + ", host is null");
        }
    }

    protected Map<String, String> prepareHeaders(){
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", authorization);
        headers.put("Content-Type", CommonConstant.JSON_CONTENT_TYPE);
        return headers;
    }
    
    /**
     * Get single configuration property from server.
     * @param paramType
     * @param paramStatus
     * @param paramKey
     * @return
     * @throws HttpException
     */
    public Parameter getSdParameter(ParameterType paramType, int paramStatus,
            String paramKey) throws HttpRequestException {
    	Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", authorization);
        String json = httpRequestService.doHttpRequest(
                buildServiceUrl(AppConfig.getSellerDashboardServicePrefix(), selectSdParameterUrl)
                + paramType.getType() + '/' + paramStatus + '/' + paramKey,
                GET_METHOD, null, headers);

        TypeToken<HttpResponseData<Parameter>> type = new TypeToken<HttpResponseData<Parameter>>() {
        };
        HttpResponseData<Parameter> response = httpRequestService
                .getResponseData(json, type);

        if (response != null) {
            if (response.getIsSuccess()) {
                return response.getData();
            } else {
                logger.warn("Retrieving paramter from DB returns error, with error msg: "
                        + response.getErrorMmsg());
            }
        } else {
            logger.warn("Unable to get parameter.");
        }

        return null;
    }

    /**
     * Get SellerDashboard configurations from back-end server.
     * @param paramType
     * @param paramStatus
     * @return
     * @throws HttpException
     */
    public List<Parameter> getSdParameters(ParameterType paramType,
            int paramStatus) throws HttpRequestException {
    	Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", authorization);
        String json = httpRequestService.doHttpRequest(
                buildServiceUrl(AppConfig.getSellerDashboardServicePrefix(), selectSdParameterUrl)
                + paramType.getType() + '/' + paramStatus, GET_METHOD, null,
                headers);

        TypeToken<HttpResponseData<List<Parameter>>> type = new TypeToken<HttpResponseData<List<Parameter>>>() {};
        HttpResponseData<List<Parameter>> response = httpRequestService
                .getResponseData(json, type);

        if (response != null) {
            if (response.getIsSuccess()) {
                return response.getData();
            } else {
                logger.warn("Retrieving paramters from DB returns error, with error msg: "
                        + response.getErrorMmsg());
            }
        } else {
            logger.warn("Unable to get parameters.");
        }

        return null;
    }

    public String getSdParamterValue(ParameterType paramType, int paramStatus,
            String paramKey) {
        Parameter param = null;
        try {
            param = getSdParameter(paramType, paramStatus, paramKey);
        } catch (HttpRequestException e) {
            logger.warn(String.format(
                    "Unable to get the parameter [type=%d, key=%s]",
                    paramType.getType(), paramKey), e);
        }

        if (param != null) {
            return param.getValue();
        }

        return null;
    }

    public void putSdParameter(Parameter p) throws HttpRequestException {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", authorization);

        String content = String
                .format("parametertype=%d&parameterstatus=%s&parameterkey=%s&parametervalue=%s",
                        p.getType(), p.getStatus(),
                        FastURLEncoder.encode(p.getKey()),
                        FastURLEncoder.encode(p.getValue()));

        String json = httpRequestService.doHttpRequest(
                buildServiceUrl(AppConfig.getSellerDashboardServicePrefix(), updateSdParameterUrl),
                POST_METHOD, content, headers);

        TypeToken<HttpResponseData<String>> type = new TypeToken<HttpResponseData<String>>() {
        };
        HttpResponseData<String> response = httpRequestService.getResponseData(
                json, type);

        if (response != null) {
            if (!response.getIsSuccess()) {
                logger.warn("Inserting paramter to DB returns error, with error msg: "
                        + response.getErrorMmsg());
            }
        }
    }

    /*public void insertPromoAudit(Audit audit) throws HttpRequestException {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", authorization);

        String content = "";
        String lastModifiedBy = audit.getLastModifiedBy();
        if (!StringUtil.isEmpty(lastModifiedBy)) {
            content = String.format("type=%d&createdby=%s&message=%s&lastmodifiedby=%s",
                    audit.getType(), audit.getCreatedBy(), audit.getMessage(), lastModifiedBy);
        } else {
            content = String.format("type=%d&createdby=%s&message=%s",
                    audit.getType(), audit.getCreatedBy(), audit.getMessage());
        }

        String json = httpRequestService.doHttpRequest(
                buildServiceUrl(AppConfig., insertSdAuditUrl),
                POST_METHOD, content, headers, false);

        TypeToken<HttpResponseData<String>> type = new TypeToken<HttpResponseData<String>>() {
        };
        HttpResponseData<String> response = httpRequestService.getResponseData(
                json, type);

        if (response != null) {
            if (!response.getIsSuccess()) {
                logger.warn("Can't insert the audit data in DB, with error msg: "
                        + response.getErrorMmsg());
            }
        }
    }

    public List<Audit> getPromoAudits(AuditType auditType, String startDate,
            String endDate) throws HttpRequestException {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", authorization);
        String json = httpRequestService.doHttpRequest(
                buildServiceUrl(PromotionUtil._promoServicePrefix, selectSdAuditUrl)
                + auditType.getType() + '/' + startDate + '/' + endDate,
                GET_METHOD, null, headers);

        TypeToken<HttpResponseData<List<Audit>>> type = new TypeToken<HttpResponseData<List<Audit>>>() {
        };
        HttpResponseData<List<Audit>> response = httpRequestService
                .getResponseData(json, type);

        if (response != null) {
            if (response.getIsSuccess()) {
                return response.getData();
            } else {
                logger.warn("Retrieving audit from DB returns error, with error msg: "
                        + response.getErrorMmsg());
            }
        } else {
            logger.warn("Unable to get audits.");
        }

        return null;
    }*/
    
	public boolean isUserAbleToAccessBizReport (Long userId) throws HttpRequestException {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Authorization", bizAuthorization);

		String json = httpRequestService.doHttpRequest(isAbleToAccessBizUrl + userId, GET_METHOD, null, headers);

		TypeToken<HttpResponseData<Boolean>> type = new TypeToken<HttpResponseData<Boolean>>() {};
		HttpResponseData<Boolean> response = httpRequestService
				.getResponseData(json, type);
		
		if (response != null) {
			if (response.getIsSuccess()) {
				return response.getData();
			}
		} else {
			logger.warn("Unable to check if user could access biz report.");
		}

		return false;
	}
}
