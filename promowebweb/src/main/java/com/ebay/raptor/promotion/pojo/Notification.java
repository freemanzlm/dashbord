package com.ebay.raptor.promotion.pojo;

import java.util.Date;

/**
 * 
 * @author lyan2
 */
public class Notification {
	// larger integer has higher priority.
	private int priority;
	
	// unique id for this notification.
	private String id;
	
	// which module's notification.
	private String module;
	
	private String title;
	
	private String content;
	
	// whether this notification is always published.
	private boolean always;
	
	private boolean needsFeedback;
	
	private String feedbackURL;
	
	private String feedbackMethod = "GET";
	
	private String feedbackRequestContentType = "application/x-www-form-urlencoded";
	
	// when to open this notification.
	private Date openDate;
	
	// when to close this notification. 
	private Date closeDate;
	
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public Date getCloseDate() {
		return closeDate;
	}
	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}
	public String getFeedbackRequestContentType() {
		return feedbackRequestContentType;
	}
	public void setFeedbackRequestContentType(String feedbackRequestContentType) {
		this.feedbackRequestContentType = feedbackRequestContentType;
	}
	public String getFeedbackMethod() {
		return feedbackMethod;
	}
	public void setFeedbackMethod(String feedbackMethod) {
		this.feedbackMethod = feedbackMethod;
	}
	public String getFeedbackURL() {
		return feedbackURL;
	}
	public void setFeedbackURL(String feedbackURL) {
		this.feedbackURL = feedbackURL;
	}
	
	public boolean isAlways() {
		return always;
	}
	public void setAlways(boolean always) {
		this.always = always;
	}
	public String getTitle() {
		return title;
	}
	public boolean isNeedsFeedback() {
		return needsFeedback;
	}
	public void setNeedsFeedback(boolean needsFeedback) {
		this.needsFeedback = needsFeedback;
	}
	public Date getOpenDate() {
		return openDate;
	}
	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
