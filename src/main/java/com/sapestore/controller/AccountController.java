package com.sapestore.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;

import com.sapestore.common.SapeStoreLogger;
import com.sapestore.exception.SapeStoreException;
import com.sapestore.exception.SapeStoreSystemException;
import com.sapestore.hibernate.entity.User;
import com.sapestore.service.AccountService;

/**
 * This is a controller class for the login functionality.
 *
 * CHANGE LOG
 *      VERSION    DATE          AUTHOR       MESSAGE               
 *        1.0    20-06-2014     SAPIENT      Initial version
 */

@Controller
@SessionAttributes(value = {"userId", "username"})
public class AccountController {
	
	private final static SapeStoreLogger LOGGER = SapeStoreLogger.getLogger(AccountController.class.getName());
	
	@Autowired
	private AccountService accountService;
	
	@RequestMapping(value="/beforelogin",method=RequestMethod.GET)
	public String beforeLogin(ModelMap modelMap) throws SapeStoreException
	{
		LOGGER.debug(" AccountController.beforeLogin method: START ");
		modelMap.addAttribute("user", new User());
		LOGGER.debug(" AccountController.beforeLogin method: END ");
		return "index";
	}
	
	/**
	 * Processes the login requests
	 * @param userlogin
	 * @param modelMap
	 * @return
	 * @throws SapeStoreSystemException
	 */
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(@ModelAttribute("user") User user,  ModelMap modelMap,HttpServletRequest httpServletRequest,HttpSession httpSession) throws SapeStoreException
	{
		LOGGER.debug("login method: START");
        String forwardStr = null;
        User localUserlogin = null;
        User userLogin = (User) user;
        String userId = null;

        localUserlogin = accountService.authenticate(userLogin);    
		if (localUserlogin != null && localUserlogin.getIsAdmin()!=null) {
		    if (localUserlogin.getIsAdmin().equalsIgnoreCase("Y")) {
		        forwardStr = "redirect:/manageInventory";
		    } else {
		        forwardStr = "redirect:/bookListByCat?categoryId=0&categoryName=Top Rated&checkMe="+httpSession.getAttribute("checkMe");
		    }
			userId = localUserlogin.getUserId();
			modelMap.addAttribute("userId", userId);
			modelMap.addAttribute("username", localUserlogin.getName());
		} else {
		    forwardStr = "index";
		}
		LOGGER.debug("login method: END");
        return forwardStr;
	}
	
	/**
	 * Processes the Logout requests
	 * @param request
	 * @param status
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(WebRequest request, SessionStatus status,ModelMap modelMap,HttpServletRequest httpServletRequest,HttpSession httpSession) throws SapeStoreException {
		LOGGER.debug("logout method: START");
		status.setComplete();
		request.removeAttribute("userId", WebRequest.SCOPE_SESSION);
		request.removeAttribute("ShoppingCart", WebRequest.SCOPE_SESSION);
		request.removeAttribute("checkMe", WebRequest.SCOPE_SESSION);
		LOGGER.debug("logout method: END");
		return "redirect:/welcome?checkMe=false";
	}

}
