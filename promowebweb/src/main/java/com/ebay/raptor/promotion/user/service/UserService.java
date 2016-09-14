package com.ebay.raptor.promotion.user.service;

import org.springframework.stereotype.Service;

import com.ebay.app.raptor.promocommon.CommonLogger;
import com.ebay.integ.dal.dao.FinderException;
import com.ebay.integ.user.User;
import com.ebay.integ.user.UserDAO;

/**
 * Don't use this service, we should get user information from CSApiService.
 */
@Deprecated
@Service
public class UserService {
	private static final CommonLogger _logger = CommonLogger.getInstance(UserService.class);
	
	/**
	 * Get user by user name. 
	 * Note: you can only use it in production environment, because there is no data in QA database.
	 * @param userName
	 * @return
	 * @throws FinderException 
	 */
	public User getUserByNameFromDal(String userName) throws FinderException{
		return UserDAO.getInstance().findCompactUserByName(userName, true, true);
	}
	
	/**
	 * Get user id from user name.
	 * Note: you can only use it in production environment, because there is no data in QA database.
	 * @param userName
	 * @return
	 */
	public long getUserIdByNameFromDal(String userName){
		try {
			User usr = getUserByNameFromDal(userName);
			return usr.getUserId();
		} catch (FinderException e) {
			_logger.error("Failed to load user ID by user name via DAL, uname: " + userName);
		}
		return -1;
	}
}
