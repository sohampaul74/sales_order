package app.order.salesorder.controller.advice;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import app.order.salesorder.exception.ApplicationException;
import app.order.salesorder.model.AppResponse;
import app.order.salesorder.util.MessageConstants;

@RestControllerAdvice
public class SalesOrderExceptionHandler {
	
	@Autowired
	private MessageSource messageSource;
	
	@ExceptionHandler(ApplicationException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public AppResponse<String> handleApplicationException(ApplicationException ae) {
		return new AppResponse<String>(ae.getLocalizedMessage());
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public AppResponse<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException ae, Locale locale) {
		return new AppResponse<String>(messageSource.getMessage(MessageConstants.INVALID_CHARACTER_IN_DATA.name(),new Object[0],locale));
	}
	
}
