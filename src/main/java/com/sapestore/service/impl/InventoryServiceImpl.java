package com.sapestore.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sapestore.common.SapeStoreLogger;
import com.sapestore.dao.InventoryDao;
import com.sapestore.dao.ProductDao;
import com.sapestore.exception.SapeStoreException;
import com.sapestore.hibernate.entity.Book;
import com.sapestore.service.InventoryService;
import com.sapestore.vo.BookVO;

/**
 * Service class for Manage Inventory functionality.
 * 
 * CHANGE LOG 
 * VERSION 	DATE 		AUTHOR 	MESSAGE 
 * 1.0 		20-06-2014 	SAPIENT Initial version
 */

@Service("inventoryService")
public class InventoryServiceImpl implements InventoryService {
	
	private final static SapeStoreLogger LOGGER = SapeStoreLogger.getLogger(InventoryServiceImpl.class.getName());
	
	@Autowired
	private InventoryDao inventoryDao;
	
	@Autowired
	private ProductDao bookDao;

	@Override
	public List<Book> getAdminInventory() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getAdminInventory method: START");
		}
		List<Book> adminInventoryBeanList = null;
		try {
			adminInventoryBeanList = inventoryDao.getAdminInventory();
		} catch (Exception e) {
			LOGGER.error("getAdminInventory method: ERROR: " + e);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("getAdminInventory method: END");
		}
		return adminInventoryBeanList;
	}
	
	@Override
	public void updateBooks(BookVO updateInventory) throws SapeStoreException {
		LOGGER.debug("updateBooks method: START");
		
		System.out.println("----------in updateBooks---------->");
		
        if (null != updateInventory) {
       		bookDao.updateBooks(updateInventory);	
		}
		LOGGER.debug("updateBooks method: END");
	}


}
