package com.ebay.raptor.promotion.service;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response.Status;

import org.ebayopensource.ginger.client.GingerClientResponse;
import org.ebayopensource.ginger.client.GingerWebTarget;

public class BaseService {

	protected String params(String url, Object... objs){
		for(int pos = 0; pos < objs.length; pos = pos + 2){
            if(null == objs[pos + 1]){
                continue;
            }
            url = url.replace(objs[pos].toString(), objs[pos + 1].toString());
        }
		return url;
	}
	
	protected String secureUrl(String url){
		return "/promoser/" + url;
	}

	protected static MultivaluedHashMap<String, Object> nonAuthHeaders(){
		MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
		return headers;
	}

	protected MultivaluedHashMap<String, Object> authHeaders(String token){
		MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON);	
		headers.add(HttpHeaders.AUTHORIZATION, ResourceProvider.TOKEN_TYPE + token);
		return headers;
	}
	
	protected GingerClientResponse httpGet(String url){
		GingerWebTarget target = PromoClient.getClient().target(url);
		GingerClientResponse resp = (GingerClientResponse) target.request().headers(authHeaders(TokenService.getIAFToken())).get();
		if(Status.OK.getStatusCode() == resp.getStatus()){
			return resp;
		}
		return resp;
	}
	
	protected GingerClientResponse httpPost(String url, Object postObj){
		GingerWebTarget target = PromoClient.getClient().target(url);
		GingerClientResponse resp = (GingerClientResponse) target.request().headers(authHeaders(TokenService.getIAFToken())).post(Entity.json(postObj));
		if(Status.OK.getStatusCode() == resp.getStatus()){
			return resp;
		}
		return resp;
	}
	
	protected <T> GenericType<T> buildGenaricType(T t){
		GenericType<T> type = new GenericType<T>(){};
		return type;
	}
	
}
