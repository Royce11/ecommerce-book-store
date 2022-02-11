package com.sapestore.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.sapestore.common.ApplicationConstants;
import com.sapestore.common.SapeStoreLogger;
import com.sapestore.exception.SapeStoreSystemException;
import com.sapestore.service.ShoppingCartService;
import com.sapestore.vo.ShoppingCartVO;
import com.sapestore.vo.UserVO;

/**
 * This is a controller class for the shopping cart.
 *
 * CHANGE LOG
 *      VERSION    DATE          AUTHOR       MESSAGE               
 *        1.0    20-06-2014     SAPIENT      Initial version
 */

@Controller
@SessionAttributes("ShoppingCart")
public class ShoppingCartController {
	
	private final static SapeStoreLogger LOGGER = SapeStoreLogger.getLogger(ShoppingCartController.class.getName());

	@Autowired(required=true)
	public ShoppingCartService shoppingCartService;

	/**
	 * Processes the Add to Cart requests.
	 * @param categoryId
	 * @param categoryName
	 * @param checkMe
	 * @param isbn
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addToShoppingCart", method = RequestMethod.GET)
	public String addToShoppingCart(@RequestParam("categoryId") int categoryId,
			@RequestParam("categoryName") String categoryName,
			@RequestParam(value="checkMe",required=false) boolean checkMe,
			@RequestParam("isbn") String isbn, ModelMap modelMap,HttpServletRequest httpServletRequest,HttpSession httpSession)
			throws Exception {
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("addToShoppingCart method: START");
		}

		ShoppingCartVO shoppingCart = null;
		String forwardStr = null;

		try {

			shoppingCart = (ShoppingCartVO) modelMap.get("ShoppingCart");

			shoppingCart = shoppingCartService.addBookToCart(shoppingCart, isbn);
			modelMap.addAttribute("ShoppingCart", shoppingCart);
			modelMap.addAttribute("userlogin", new UserVO());
			if (categoryName.equalsIgnoreCase("Top Rated")) {
				forwardStr = "redirect:/bookListByCat?categoryId=0&categoryName=Top Rated&checkMe="
						+ httpSession.getAttribute("checkMe");
			} else {
				forwardStr = "redirect:/bookListByCat?categoryId=" + categoryId
						+ "&categoryName=" + categoryName + "&checkMe="
						+ httpSession.getAttribute("checkMe");
			}
		} catch (SapeStoreSystemException ex) {
			LOGGER.error("addToShoppingCart method: ERROR: " + ex);
			forwardStr = ApplicationConstants.FAILURE;
		}
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("addToShoppingCart method: END");
		}

		return forwardStr;

	}

}
