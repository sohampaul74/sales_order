package app.order.salesorder.controller;

import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import app.order.salesorder.exception.ApplicationException;
import app.order.salesorder.model.AppResponse;
import app.order.salesorder.model.FormSalesOrder;
import app.order.salesorder.service.SalesOrderService;
import app.order.salesorder.util.MessageConstants;
import app.order.salesorder.util.ReturnConstants;
import app.order.salesorder.util.SearchBy;

@RestController
@RequestMapping( value = "order" )
public class SalesOrderController {
	
	private static final Logger logger = LoggerFactory.getLogger(SalesOrderController.class);
	
	private SalesOrderService salesOrderService;
	
	@Autowired
	private MessageSource messageSource;
	
	public SalesOrderController(SalesOrderService salesOrderService) {
		this.salesOrderService = salesOrderService;
	}
	
	@PostMapping(value="",consumes = MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ResponseStatus(code = HttpStatus.OK)
	public AppResponse<String> saveOrder(@RequestBody @Valid FormSalesOrder formSalesOrder, BindingResult br, @RequestHeader(name = "Accept-Language",required = true, defaultValue = "en-EN")Locale locale) {
		logger.info("saveOrder called with "+formSalesOrder.toString()+" with locale:"+locale.getCountry());
		if(br.hasErrors()) { throw new ApplicationException(br.getAllErrors().parallelStream().map(t->t.getDefaultMessage()).reduce((t1,t2)->(t1+", "+t2)).orElse(null)); }
		String id = salesOrderService.saveOrder(formSalesOrder);
		
		
		if(id == ReturnConstants.FAILURE) {
			throw new ApplicationException(messageSource.getMessage(MessageConstants.UNKNOWN_ERROR_OCCURED.name(),new Object[0],locale));
		}
		String url = ServletUriComponentsBuilder.fromCurrentRequest().path("/id/{id}").buildAndExpand(id).toUriString();
		AppResponse<String> appResponse = new AppResponse<String>(url, messageSource.getMessage(MessageConstants.SUCCESS_CREATE.name(),new Object[0],locale));
		return appResponse;
	}
	
	@GetMapping(value="{searchBy}/{searchText}",produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ResponseStatus(code = HttpStatus.OK)
	public AppResponse<List<FormSalesOrder>> getOrder(@PathVariable(name = "searchBy",required = true) String searchBy, @PathVariable(name = "searchText",required = false) String searchText,@RequestHeader(name = "Accept-Language",required = true, defaultValue = "en-EN") Locale locale) {
		logger.info("getOrder called with searchBy:"+searchBy+" with searchText:"+searchText);
		
		SearchBy searchByEnum = null;
		
		try {
			searchByEnum = SearchBy.valueOf(searchBy.toUpperCase());
		} catch(IllegalArgumentException | NullPointerException iae) {
			throw new ApplicationException(messageSource.getMessage(MessageConstants.INVALID_SEARCH_PARAMETER.name(),new Object[0],locale));
		} finally {
			if(StringUtils.isEmpty(searchText) && searchByEnum != SearchBy.ALL) {
				throw new ApplicationException(messageSource.getMessage(MessageConstants.INVALID_SEARCH_PARAMETER.name(),new Object[0],locale));
			}
		}
		
		List<FormSalesOrder> lstOrders = salesOrderService.searchOrder(searchByEnum,searchText);
		if(null == lstOrders || lstOrders.size()<1) {
			throw new ApplicationException(messageSource.getMessage(MessageConstants.NO_DATA_FOUND.name(),new Object[0],locale));
		}
		return new AppResponse<List<FormSalesOrder>>(lstOrders, messageSource.getMessage(MessageConstants.SUCCESS_FETCH.name(),new Object[0],locale));
	}
	
	@DeleteMapping(value="{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.OK)
	@ResponseBody
	public AppResponse<String> deleteOrder(@PathVariable(name="id",required = true)String id, @RequestHeader(name = "Accept-Language",required = true, defaultValue = "en-EN")Locale locale) {
		logger.info("deleteOrder called with id:"+id);
		//if(br.hasErrors()) { throw new ApplicationException(br.getAllErrors().get(0).getDefaultMessage()); }
		if(StringUtils.isEmpty(id)) {
			throw new ApplicationException(messageSource.getMessage(MessageConstants.INVALID_ORDER_ID.name(),new Object[0],locale));
		}
		String status = salesOrderService.deleteOrder(id);
		if(status == ReturnConstants.FAILURE) {
			throw new ApplicationException(messageSource.getMessage(MessageConstants.FAILURE_TO_DELETE.name(),new Object[0],locale));
		}
		return new AppResponse<String>(messageSource.getMessage(MessageConstants.SUCCESS_DELETE.name(),new Object[0],locale));
	}
}
