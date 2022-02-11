package com.sapestore.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Bean class for keeping shopping cart information. 
 *
 * CHANGE LOG
 *      VERSION    DATE          AUTHOR       MESSAGE               
 *        1.0    20-06-2014     SAPIENT      Initial version
 */

public class ShoppingCartVO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private List<BookVO> booksInCart;	
	private int totalPrice;	
	private int totalQuantity;

	/**
	 * Add book to the list of books in shooping cart	
	 * @param book
	 */
	public void setBooksInCart(BookVO book) {
		if (this.getBooksInCart() == null) {
			this.booksInCart = new ArrayList<BookVO>();
		}
		 this.booksInCart.add(book);
		 this.totalPrice = this.totalPrice + Integer.parseInt(book.getBookPrice());
		 this.totalQuantity ++;
		 
	}
	
	/**
	 * 
	 * @return the list of books in shopping cart.
	 */
	public List<BookVO> getBooksInCart() {
        return booksInCart;
    }

	/**
	 * 
	 * @return totalPrice.
	 */
	public int getTotalPrice() {
		return totalPrice;
	}

	/**
	 * 
	 * @return totalQuantity.
	 */
	public int getTotalQuantity() {
		return totalQuantity;
	}

}
