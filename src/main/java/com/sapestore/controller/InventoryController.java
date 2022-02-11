package com.sapestore.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.sapestore.common.ApplicationConstants;
import com.sapestore.common.SapeStoreLogger;
import com.sapestore.exception.SapeStoreException;
import com.sapestore.service.InventoryService;
import com.sapestore.vo.BookVO;

/**
 * This is a controller class for updating inventory. 
 *
 * CHANGE LOG
 *      VERSION    DATE          AUTHOR       MESSAGE               
 *        1.0    20-06-2014     SAPIENT      Initial version
 */

@Controller
public class InventoryController {
	
	private final static SapeStoreLogger LOGGER = SapeStoreLogger.getLogger(InventoryController.class.getName());

	@Autowired
	private InventoryService inventoryService;
	
	@Autowired(required=false)
	ServletContext servletContext;
	/**
	 * Processes the inventory updation requests.
	 * @param updateInventory
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateInventory", method = RequestMethod.POST)
	public String updateInventory(@ModelAttribute("updateInv") BookVO updateInventory,	ModelMap modelMap) throws SapeStoreException {
		LOGGER.debug("updateInventory method: START");
		String forwardStr = null;
		String thumbPath = null;
		String fullPath = null;
		String thumbImageFileName = null;
		String fullImageFileName = null;
		File thumbUploadDir = null;
		File fullUploadDir = null;
		try {
			thumbPath = servletContext.getRealPath(ApplicationConstants.THUMB_IMG_URL);
			fullPath = servletContext.getRealPath(ApplicationConstants.FULL_IMG_URL);
			thumbUploadDir = new File(thumbPath);
			fullUploadDir = new File(fullPath);
			if (thumbUploadDir.exists() == false) {
				thumbUploadDir.mkdirs();
			}
            if (fullUploadDir.exists() == false) {
                fullUploadDir.mkdirs();
            }
			if (null != updateInventory) {
				MultipartFile multipartFile = updateInventory.getThumbImage();
				if (null != updateInventory.getThumbImage() && updateInventory.getThumbImage().getSize()>0) {
					thumbImageFileName = multipartFile.getOriginalFilename();
					File thumbFile = new File(thumbPath, multipartFile.getOriginalFilename());
					byte[] bytes = multipartFile.getBytes();
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(thumbFile));
					stream.write(bytes);
					stream.close();
					updateInventory.setThumbPath(ApplicationConstants.THUMB_IMG_URL	+ thumbImageFileName);
				}
				MultipartFile multipartFileFullImage = updateInventory.getFullImage();
				if (null != updateInventory.getFullImage() && updateInventory.getFullImage().getSize()>0) {
					fullImageFileName = multipartFileFullImage.getOriginalFilename();
					File fullFile = new File(fullPath, multipartFileFullImage.getOriginalFilename());
					byte[] bytes = multipartFileFullImage.getBytes();
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(fullFile));
					stream.write(bytes);
					stream.close();
					updateInventory.setFullPath(ApplicationConstants.FULL_IMG_URL + fullImageFileName);
				}
				inventoryService.updateBooks(updateInventory);
				forwardStr = "redirect:/manageInventory";
			} else {
				forwardStr = ApplicationConstants.FAILURE;
			}
		} catch (IOException e) {
			LOGGER.error("updateInventory method: ERROR: " + e);
			forwardStr = ApplicationConstants.FAILURE;
		}
		LOGGER.debug("updateInventory method: END");
		return forwardStr;
	}
	
	@RequestMapping(value = "/updateInventory", method = RequestMethod.POST, params="manageInv")
	public String cancelUpdate(@ModelAttribute("updateInv") BookVO updateInventory, ModelMap modelMap) throws SapeStoreException {
		return "redirect:/manageInventory";
	}
	
	/**
	 * Processes the manage inventory page requests and returns the data for the page.
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/manageInventory", method = RequestMethod.GET)
	public String manageInventory(ModelMap modelMap) throws Exception {
		LOGGER.debug("manageInventory method: START");
		try {
			modelMap.addAttribute("adminInventoryList",	inventoryService.getAdminInventory());
		} catch (Exception ex) {
			LOGGER.error("manageInventory method: ERROR: " + ex);
			return ApplicationConstants.FAILURE;
		}
		LOGGER.debug("manageInventory method: END");
		return "ManageInventory";
	}

}
