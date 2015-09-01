package com.ebay.raptor.promotion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebay.app.raptor.promocommon.CommonLogger;
import com.ebay.app.raptor.promocommon.concurrent.CommonExecutorService;
import com.ebay.app.raptor.promocommon.pojo.db.Audit;
import com.ebay.app.raptor.promocommon.pojo.db.AuditType;
import com.ebay.raptor.promotion.pojo.UserData;

@Service
public class TrackService{
	private static final CommonLogger _logger = CommonLogger.getInstance(TrackService.class);

	@Autowired CommonExecutorService service;
	
	public void logUserActivity (String userId, String userName,
	        AuditType type, boolean admin) {
		logUserActivity(userId, userName, type, admin, "");
	}
	
	public void logUserActivity (String userId, String userName,
	        AuditType type, boolean admin, String msg) {
	    if (admin) {
	        return;
	    }

		Audit audit = new Audit(type.getType(), userId, userName, msg);
		
		// insert into DB
	}
	
	public void logUserActivity (UserData user,
	        AuditType type) {
		logUserActivity(user.getUserId() + "", user.getUserName(), type, user.getAdmin());
	}
	
	/**
	 * Start a new thread for the tracking, so a tracking failure will not block the whole flow.
	 * @param user
	 * @param type
	 */
	public void logUserActivityAsync(final UserData user,
	        final AuditType type, final String msg) {
		service.submit(new Runnable(){

			@Override
			public void run() {
				logUserActivity(user.getUserId() + "", user.getUserName(), type, user.getAdmin(), msg);
			}
			
		});
	}
}
