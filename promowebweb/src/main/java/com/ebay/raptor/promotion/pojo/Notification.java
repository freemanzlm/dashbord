package com.ebay.raptor.promotion.pojo;

import java.util.Date;

/**
 * 
 * @author lyan2
 */
public class Notification {
	// larger integer has higher priority.
	private int priority;
	
	private String id;
	
	private String title;
	
	private String content;
	
	private boolean read;
	
	// whether this notification is always published.
	private boolean always;
	
	private boolean needsFeedback;
	
	private String feedbackURL;
	
	private String feedbackMethod = "GET";
	
	private String feedbackRequestContentType = "application/x-www-form-urlencoded";
	
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
	private Date openDate;
	
	public boolean isRead() {
		return read;
	}
	public void setRead(boolean read) {
		this.read = read;
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
