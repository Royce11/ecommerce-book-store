package com.sapestore.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.sapestore.common.SapeStoreLogger;
import com.sapestore.exception.SapeStoreException;
import com.sapestore.hibernate.entity.Book;
import com.sun.mail.iap.ConnectionException;

/**
 * DAO class for admin report.
 * 
 * CHANGE 	LOG 
 * VERSION 	DATE 			AUTHOR MESSAGE 
 * 1.0 		20-06-2014 		SAPIENT Initial version
 */
@Repository
public class InventoryDao {
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
		
	/**
	 * Logger for log messages.
	 */
	private final static SapeStoreLogger LOGGER = SapeStoreLogger.getLogger(InventoryDao.class.getName());

	/**
	 * Method to fetch admin report from the database.
	 * 
	 * @return
	 * @throws ConnectionException
	 * @throws TransactionExecutionException
	 */
	
	@SuppressWarnings("unchecked")
	public List<Book> getAdminInventory() throws SapeStoreException {
		LOGGER.debug("InventoryDao.getBookDetails method: START");
		List<Book> listBook = null;
		listBook = (List<Book>) hibernateTemplate.findByNamedQuery("Book.findAllInventory");		
		return listBook;
	}	
	
}
