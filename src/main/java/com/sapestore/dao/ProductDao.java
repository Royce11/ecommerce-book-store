package com.sapestore.dao;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.sapestore.common.SapeStoreLogger;
import com.sapestore.exception.SapeStoreException;
import com.sapestore.exception.SapeStoreSystemException;
import com.sapestore.hibernate.entity.Book;
import com.sapestore.hibernate.entity.BookCategory;
import com.sapestore.vo.BookVO;
import com.sun.mail.iap.ConnectionException;

/**
 * DAO class for retrieving the book's list from the database. 
 *
 * CHANGE LOG
 *      VERSION    DATE          AUTHOR       MESSAGE               
 *        1.0    20-06-2014     SAPIENT      Initial version
 */
@Repository
public class ProductDao {
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	/**
	 * Logger for log messages.
	 */
	private final static SapeStoreLogger LOGGER = SapeStoreLogger.getLogger(ProductDao.class.getName());
    
    /**
     * Method to fetch the book list from the database.
     * 
     * @param categoryId
     * @param checkMeFromSession 
     * @return
     * @throws ConnectionException
     * @throws TransactionExecutionException
     */
	@SuppressWarnings("unchecked")
	public List<Book> getBookList(int categoryId, Object checkMeFromSession) throws SapeStoreException {		
		List<Book> listBook = null;
		if(categoryId == 0) {
			listBook = (List<Book>) hibernateTemplate.findByNamedQuery("Book.findAll");
		}
		else		
		listBook = (List<Book>) hibernateTemplate.findByNamedQueryAndNamedParam("Book.findByCategoryId", "categoryId", categoryId);
	
		return listBook;		
	}
	
	 /**
     * Method to fetch the book's category list.
     * 
     * @return
     * @throws ConnectionException
     * @throws TransactionExecutionException
     */
	@SuppressWarnings("unchecked")
	public List<BookCategory> getBookCategoryList()	throws SapeStoreException {
		List<BookCategory> listBookCategories = (List<BookCategory>) hibernateTemplate.findByNamedQuery("BookCategory.findAll");
		return listBookCategories;		
	}
	
	/**
	 * deleteBook method updates the quantity of the selected book to zero in the database
	 * 
	 * @param isbn
	 * @throws ConnectionException
	 * @throws TransactionExecutionException
	 */
	public void deleteBook(String isbn) throws SapeStoreException {
		LOGGER.debug(" BookDao.deleteBook method: START");
		Book book = hibernateTemplate.get(Book.class, isbn.trim());
		book.setQuantity(0);		
		hibernateTemplate.saveOrUpdate(book);
	}
	
	/**
	 * Method to add a new book to the database.
	 * 
	 * @param vo
	 * @throws ConnectionException
	 * @throws TransactionExecutionException
	 */	
	public void addNewBooks(BookVO vo) throws SapeStoreException {
		LOGGER.debug(" ProductDao.addNewBooks method: START");
		try{
			Book book = new Book();
			book.setIsbn(vo.getIsbn());
			book.setPublisherName(vo.getPublisherName());
			book.setCategoryId(Integer.parseInt(vo.getCategoryId()));
			book.setBookTitle(vo.getBookTitle());		
			book.setQuantity(vo.getQuantity());
			book.setBookAuthor(vo.getBookAuthor());		
			book.setBookThumbImage(vo.getThumbPath());		
			book.setBookFullImage(vo.getFullPath());
			book.setBookPrice(new BigDecimal(vo.getBookPrice()));		
			book.setBookShortDescription(vo.getBookShortDesc());		
			book.setBookDetailDescription(vo.getBookDetailDesc());		
			book.setRentAvailability(vo.getRentAvailable());		
			book.setRentPrice(new BigDecimal(vo.getRentPrice()));		
			book.setCreatedDate(new java.util.Date());		
			book.setUpdatedDate(new java.util.Date());		
			book.setIsActive("Y");		
			hibernateTemplate.save(book);
		}		
		catch (SapeStoreSystemException se) {
				LOGGER.fatal("A DB exception occured while inserting the book's information", se);
			}
		LOGGER.debug(" ProductDao.addNewBooks method: END ");
	}

		
	/**
	 * Update Book method updates the detail of the corresponding book.
	 * 
	 * @param updateInventoryBean
	 * @throws ConnectionException
	 * @throws TransactionExecutionException
	 */
	public void updateBooks(BookVO updateInventoryBean) throws SapeStoreException {
		LOGGER.debug(" ProductDao.updateBooks method: START ");
	
		try {
			Book book = hibernateTemplate.get(Book.class, updateInventoryBean.getOldIsbn());
			if(book != null){		
				book.setIsbn(updateInventoryBean.getIsbn());
				book.setPublisherName(updateInventoryBean.getPublisherName());				
				book.setBookTitle(updateInventoryBean.getBookTitle());			
				book.setQuantity(updateInventoryBean.getQuantity());					
				book.setBookAuthor(updateInventoryBean.getBookAuthor());			
				book.setBookPrice(new BigDecimal(updateInventoryBean.getBookPrice()));			
				book.setBookShortDescription(updateInventoryBean.getBookShortDesc());
				book.setRentAvailability(updateInventoryBean.getRentAvailable());			
				book.setRentPrice(new BigDecimal(updateInventoryBean.getRentPrice()));			
				book.setBookDetailDescription(updateInventoryBean.getBookDetailDesc());						
				book.setCategoryId(Integer.parseInt(updateInventoryBean.getCategoryId()));			
				book.setBookThumbImage(updateInventoryBean.getThumbPath());			
				book.setBookFullImage(updateInventoryBean.getFullPath());			
				book.setUpdatedDate(new java.util.Date());				
				hibernateTemplate.update(book);			
				LOGGER.debug(" Book is updated ");
			}
		}catch (SapeStoreSystemException se) {
			LOGGER.fatal("A DB exception occured while inserting the book's information", se);
		}
		LOGGER.debug(" ProductDao.updateBooks method: END ");
	}
	
	/**
     * Method to get book details from the database.
     *
     * @param isbn
     * @return
     * @throws ConnectionException
     * @throws TransactionExecutionException
     */
    public BookVO getBookDetails(String isbn) throws SapeStoreException {    	    	
		LOGGER.debug(" ProductDao.getBookDetails method: START");
		Book book = null;
		try{
			book = hibernateTemplate.get(Book.class, isbn.trim());
		}
		catch (SapeStoreSystemException se) {
			LOGGER.fatal("A DB exception occured while inserting the book's information", se);
	    }
    	return setBookDetailBean(book);    	
    }

	/**
	 * Converts the Map representation of book details HashMap to its DO
	 * representation
	 * 
	 * @param hash
	 * @return
	 */
    private BookVO setBookDetailBean(Book book) {
    	BookVO vo = null;
        if (book != null) {
        	vo = new BookVO();
            vo.setIsbn(book.getIsbn());
            vo.setBookTitle(book.getBookTitle());
            vo.setBookAuthor(book.getBookAuthor());     
            vo.setBookPrice(book.getBookPrice().toString());
            vo.setThumbPath(book.getBookThumbImage());
            vo.setQuantity(book.getQuantity());
        } 
        return vo;
    }
	
}
