package com.sapestore.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sapestore.common.SapeStoreLogger;
import com.sapestore.dao.OrderDao;
import com.sapestore.exception.SapeStoreException;
import com.sapestore.hibernate.entity.OrderItemInfo;
import com.sapestore.service.OrderService;
import com.sapestore.vo.DispatchSlip;
import com.sapestore.vo.OrderVO;
import com.sapestore.vo.RentedUpdate;

/**
 * Service class for updating rent information.
 * 
 * CHANGE LOG 
 * VERSION 	DATE 		AUTHOR 	MESSAGE 
 * 1.0 		20-06-2014 	SAPIENT Initial version
 */

@Service("orderService")
public class OrderServiceImpl implements OrderService {

	private final static SapeStoreLogger LOGGER = SapeStoreLogger.getLogger(OrderServiceImpl.class.getName());
	
	@Autowired
	private OrderDao orderDao;

	@Override
	public List<Integer> updateDispatch(List<RentedUpdate> rentedUpdateList) throws SapeStoreException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("updateDispatch method: START");
		}
		List<OrderItemInfo> orderItemInfoList = orderDao.getRentedOrders();		
		List<OrderVO> rentedOrderBeans = setRentedOrders(orderItemInfoList);
							
		List<Integer> orderNums = null;
		List<Boolean> orgListDispatch = null;
		List<Boolean> orgListReturn = null;
		List<Boolean> newListDispatch = null;
		List<Boolean> newListReturn = null;

		if (rentedOrderBeans != null && rentedOrderBeans.size() > 0) {
			orderNums = new ArrayList<Integer>();
			orgListDispatch = new ArrayList<Boolean>();
			orgListReturn = new ArrayList<Boolean>();
			for (OrderVO r : rentedOrderBeans) {
				orgListDispatch.add(r.isOrderStatus());
				orgListReturn.add(r.isReturnReceived());
				orderNums.add(r.getOrderNumber());
			}
		}
		if (rentedUpdateList != null && rentedUpdateList.size() > 0) {
			newListDispatch = new ArrayList<Boolean>();
			newListReturn = new ArrayList<Boolean>();
			for (RentedUpdate r : rentedUpdateList) {
				newListDispatch.add(r.getDispatchStatus());
				newListReturn.add(r.getReturnStatus());
			}
		}
		List<Integer> dispatchedOrders = orderDao.updateDispatch(orgListDispatch, orgListReturn, newListDispatch, newListReturn, orderNums);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("updateDispatch method: END");
		}
		return dispatchedOrders;
	}
	
	@Override
	public void updateReturn(List<RentedUpdate> rentedUpdateList) throws SapeStoreException	{
		List<OrderItemInfo> orderItemInfoList = orderDao.getRentedOrders();		
		List<OrderVO> rentedOrderBeans = setRentedOrders(orderItemInfoList);
		
		List<Integer> orderNums=new ArrayList<Integer>();		
		List<Boolean> orgListDispatch=new ArrayList<Boolean>();
		List<Boolean> orgListReturn=new ArrayList<Boolean>();		
		List<Boolean> newListDispatch=new ArrayList<Boolean>();
		List<Boolean> newListReturn=new ArrayList<Boolean>();
		
		for(OrderVO r:rentedOrderBeans){
			orgListDispatch.add(r.isOrderStatus());
			orgListReturn.add(r.isReturnReceived());
			orderNums.add(r.getOrderNumber());
		}	
		for(RentedUpdate r:rentedUpdateList){
			newListDispatch.add(r.getDispatchStatus());
			newListReturn.add(r.getReturnStatus());
		}		
		orderDao.updateReturn(orgListDispatch, orgListReturn, newListDispatch, newListReturn, orderNums);		
	}
	
	
	/**
	 * set the status of rented books from the admin console
	 * 
	 * @param list
	 * @return beans
	 */
	private List<OrderVO> setRentedOrders(List<OrderItemInfo> orderItemInfoList) {
		List<OrderVO> beans = null;

		if (orderItemInfoList != null && !orderItemInfoList.isEmpty()) {
			beans = new ArrayList<OrderVO>();
			for (int i = 0; i < orderItemInfoList.size(); i++) {
				OrderVO tempList = new OrderVO();
				tempList.setOrderNumber(orderItemInfoList.get(i).getOrderId());
				tempList.setItemName(orderItemInfoList.get(i).getBookTitle());
				tempList.setRentAmount(orderItemInfoList.get(i).getRentPrice());

				String sd = orderItemInfoList.get(i).getOrderStatus();
				if (sd.equalsIgnoreCase("Dispatched")) {
					tempList.setOrderStatus(true);
				} else {
					tempList.setOrderStatus(false);
				}
				String sr = orderItemInfoList.get(i).getReturnStatus();
				if (sr.equalsIgnoreCase("Returned")) {
					tempList.setReturnReceived(true);
				} else {
					tempList.setReturnReceived(false);
				}
				if (orderItemInfoList.get(i).getExpectedReturnDate() == null) {

				} else {
					tempList.setExpectedReturnDate(orderItemInfoList.get(i).getExpectedReturnDate());
				}
				if (orderItemInfoList.get(i).getActualReturnDate() == null) {

				} else {
					tempList.setActualReturnDate(orderItemInfoList.get(i).getExpectedReturnDate());
				}
				tempList.setLateFee(orderItemInfoList.get(i).getLateFee());
				beans.add(tempList);
			}
		}
		return beans;
	}
	
	@Override
	public List<DispatchSlip> getDispatchedOrders(List<Integer> list) throws SapeStoreException {
		LOGGER.debug("getDispatchedOrders method: START");
		List<DispatchSlip> dispatchList = orderDao.returnDispatchedSlips(list);
		LOGGER.debug("getDispatchedOrders method: END");
		return dispatchList;	
	}


}
