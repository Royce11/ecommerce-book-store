package com.sapestore.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sapestore.common.SapeStoreLogger;
import com.sapestore.exception.SapeStoreException;
import com.sapestore.exception.SapeStoreSystemException;
import com.sapestore.hibernate.entity.OrderItemInfo;
import com.sapestore.vo.DispatchSlip;
import com.sun.mail.iap.ConnectionException;

/**
 * DAO class for order management module
 */
@Repository
@Transactional
public class OrderDao {
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
    
	/**
	 * Logger for log messages.
	 */
	private final static SapeStoreLogger LOGGER = SapeStoreLogger.getLogger(OrderDao.class.getName());
	
	/**
	 * gets the rented status of the book
	 * 
	 * @return
	 * @throws ConnectionException
	 * @throws TransactionExecutionException
	 */
	@SuppressWarnings("unchecked")
	public List<OrderItemInfo> getRentedOrders() throws SapeStoreException {
		LOGGER.debug("InventoryDao.getBookDetails method: START");
		List<OrderItemInfo> orderItemInfoList = null;
		orderItemInfoList = (List<OrderItemInfo>) hibernateTemplate.findByNamedQuery("OrderItemInfo.findByPurchaseType");		
		return orderItemInfoList;
	}	

	/**update dispatch update the status of dispatched books
	 * @param orgListDispatch
	 * @param orgListReturn
	 * @param newListDispatch
	 * @param newListReturn
	 * @param orderNums
	 * @return
	 */
	public List<Integer> updateDispatch(List<Boolean> orgListDispatch,
			List<Boolean> orgListReturn, List<Boolean> newListDispatch,
			List<Boolean> newListReturn, List<Integer> orderNums) {

		List<Integer> ordersToBeDispatched = null;
		List<Integer> ordersReturned = null;
		
		if(orderNums!=null && orderNums.size()>0){
			ordersToBeDispatched = new ArrayList<Integer>();
			ordersReturned = new ArrayList<Integer>();
			for (int i = 0; i < orderNums.size(); i++) {
				if (orgListDispatch.get(i) == false
						&& newListDispatch.get(i) == true) {
					ordersToBeDispatched.add(orderNums.get(i));
					if (newListReturn.get(i) == true) {
						ordersReturned.add(orderNums.get(i));
					}
				}
			}
		}
		return ordersToBeDispatched;
	}

	/**
	 * @param orgListDispatch
	 * @param orgListReturn
	 * @param newListDispatch
	 * @param newListReturn
	 * @param orderNums
	 */
	public void updateReturn(List<Boolean> orgListDispatch,	List<Boolean> orgListReturn, List<Boolean> newListDispatch,	
			List<Boolean> newListReturn, List<Integer> orderNums) {

		List<Integer> ordersReturned = null;		
		if(orderNums!=null && orderNums.size()>0)
		{
			ordersReturned = new ArrayList<Integer>();
			for (int i = 0; i < orderNums.size(); i++) {
				if (orgListDispatch.get(i) == true && newListReturn.get(i) == true) {
					ordersReturned.add(orderNums.get(i));
				}
			}
		}
		Date date = new Date();
		//String d = new SimpleDateFormat("dd-MMM-yy").format(date);

		/*
		 * Update return_status and actual_return_date for list of orders returned : ordersReturned
		 */
		OrderItemInfo orderItemInfo = new OrderItemInfo();
		for (Integer orderItemId : ordersReturned) {			
			orderItemInfo = hibernateTemplate.get(OrderItemInfo.class, orderItemId);
			orderItemInfo.setReturnStatus("RETURNED");		
			orderItemInfo.setActualReturnDate(date);
			hibernateTemplate.saveOrUpdate(orderItemInfo);
		}
	}
		
	/**
	 * This method sets dispatch slip.
	 * 
	 * @param list1
	 *            List of all the ordersIds to be dispatched
	 * @return List of all the orders to be dispatched
	 * @throws ConnectionException
	 * @throws TransactionExecutionException
	 */
	@SuppressWarnings("unchecked")
	public List<DispatchSlip> returnDispatchedSlips(List<Integer> list1) throws SapeStoreException {
		LOGGER.debug("returnDispatchedSlips method: START");
		List<DispatchSlip> dispatchSlipBeans = new ArrayList<DispatchSlip>();

		try {
			for (Integer i : list1) {
				String query = "select m.name,a.address_line1,a.address_line2,c.city_name,d.country_name from sapestore_members_info m,"
						+ "sapestore_members_address a,sapestore_cities c,sapestore_countries d "
						+ " where a.user_id=(select user_id from order_info "
						+ "where order_id="+i +")AND m.user_id=(select user_id from order_info "
						+ "where order_id=(select order_id from order_item_info "
						+ "where order_id="
						+ i
						+ ")) AND a.city_id=c.city_id AND"
						+ " a.country_id=d.country_id";

				List<Map<String, Object>> list = null;
				DispatchSlip dispatchSlipBean = new DispatchSlip();
				
				list = (List<Map<String, Object>>) hibernateTemplate.getSessionFactory().getCurrentSession().createSQLQuery(query);
				if (list.size() != 0) {
					LOGGER.debug("List obtained is " + list);
					dispatchSlipBean.setOrderNumber(i);
					dispatchSlipBean.setShippingAddress((String) list.get(0).get("ADDRESS_LINE1")+
							(String) list.get(0).get("ADDRESS_LINE2")+(String)list.get(0).get("CITY_NAME")+
							(String)list.get(0).get("COUNTRY_NAME"));
					dispatchSlipBean.setCustomerName((String) list.get(0).get("NAME"));
					dispatchSlipBeans.add(dispatchSlipBean);
				}
				OrderItemInfo orderItemInfo = null;
				for (Integer orderId : list1) {					
					orderItemInfo = hibernateTemplate.get(OrderItemInfo.class, orderId);
					orderItemInfo.setOrderStatus("Dispatched");		
					orderItemInfo.setDispatchDate(new Date());
				    hibernateTemplate.saveOrUpdate(orderItemInfo);
					LOGGER.debug("Orders are updated");
				}
			}
		} catch (SapeStoreSystemException se) {
			LOGGER.fatal("A DB exception occured while getting the dispatch orders list", se);
		}
		LOGGER.debug("returnDispatchedSlips method: END");
		return dispatchSlipBeans;
	}
	
}
