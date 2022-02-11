package com.sapestore.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.sapestore.common.SapeStoreLogger;
import com.sapestore.exception.SapeStoreException;
import com.sapestore.hibernate.entity.User;
import com.sun.mail.iap.ConnectionException;

/**
 * DAO class to fetch user's login details
 * 
 * CHANGE LOG 
 * VERSION 	DATE 		AUTHOR 	MESSAGE 
 * 1.0 		20-06-2014 	SAPIENT Initial version
 */
@Repository
public class AccountDao {

	/**
	 * Logger for log messages.
	 */
	private final static SapeStoreLogger LOGGER = SapeStoreLogger.getLogger(AccountDao.class.getName());
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	/**
	 * Method to fetch user login details based on the parameters provided.
	 * 
	 * @param userName
	 * @param password
	 * @return
	 * @throws ConnectionException
	 * @throws TransactionExecutionException
	 */
	@SuppressWarnings("unchecked")
	public User getUserDetails(String userId, String password) throws SapeStoreException{		
		LOGGER.debug("");
		String[] namedParams = {"userId","password"};
		Object[] paramValues = {userId,password};		
		
		List<User> listUser = (List<User>) hibernateTemplate.findByNamedQueryAndNamedParam("User.findByUserIdandPassword", namedParams, paramValues);
		if(!listUser.isEmpty()){
			return listUser.get(0);
		}else return null;	
	}

}
