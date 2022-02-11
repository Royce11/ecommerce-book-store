package com.sapestore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sapestore.common.SapeStoreLogger;
import com.sapestore.dao.ProductDao;
import com.sapestore.exception.SapeStoreException;
import com.sapestore.exception.SapeStoreSystemException;
import com.sapestore.service.ShoppingCartService;
import com.sapestore.vo.BookVO;
import com.sapestore.vo.ShoppingCartVO;

/**
 * Service class for Add to Cart functionality.
 * 
 * CHANGE LOG 
 * VERSION 	DATE 		AUTHOR 	MESSAGE 
 * 1.0 		20-06-2014 	SAPIENT Initial version
 */

@Service("shoppingCartService")
public class ShoppingCartServiceImpl implements ShoppingCartService {

	private final static SapeStoreLogger LOGGER = SapeStoreLogger.getLogger(ShoppingCartServiceImpl.class.getName());
	
	@Autowired
	private ProductDao productDao;
	
	/**
	 * Adds books to cart
	 */
	@Override
	public ShoppingCartVO addBookToCart(ShoppingCartVO shoppingCart, String isbn) throws SapeStoreException {
		LOGGER.debug("addBookToCart method: START");
		
		BookVO addToCart = null;
		BookVO existingBookBean = null;
		int bookPosition = 0;
		int quantity = 0;

		addToCart = this.getBookInfo(isbn);
		if (shoppingCart != null) {
			bookPosition = shoppingCart.getBooksInCart().indexOf(addToCart);
			if (bookPosition == -1) {
				shoppingCart.setBooksInCart(addToCart);
			} else {
				existingBookBean = shoppingCart.getBooksInCart().remove(
						bookPosition);
				quantity = existingBookBean.getQuantity();
				existingBookBean.setQuantity(++quantity);
				shoppingCart.setBooksInCart(existingBookBean);
			}
		} else {
			shoppingCart = new ShoppingCartVO();
			shoppingCart.setBooksInCart(addToCart);
		}
		LOGGER.debug("addBookToCart method: END");
		return shoppingCart;
	}

	/**
	 * Get book information on the basis of the ISBN provided
	 * @param isbn
	 * @return
	 * @throws SapeStoreSystemException
	 */
	private BookVO getBookInfo(String isbn) throws SapeStoreException {		
		LOGGER.debug("getBookInfo method: START");
		BookVO addToCartbean = null;
		addToCartbean = productDao.getBookDetails(isbn);	
		LOGGER.debug("getBookInfo method: END");
		return addToCartbean;
	}

}
