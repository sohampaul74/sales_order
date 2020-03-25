package app.order.salesorder.service;

import java.util.List;

import app.order.salesorder.model.FormSalesOrder;
import app.order.salesorder.util.SearchBy;

public interface SalesOrderService {

	String saveOrder(FormSalesOrder formSalesOrder);

	List<FormSalesOrder> searchOrder(SearchBy searchByEnum, String searchText);

	String deleteOrder(String id);
	
}
