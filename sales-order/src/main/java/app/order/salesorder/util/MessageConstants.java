package app.order.salesorder.util;

public enum MessageConstants {

	/**
	public static final String ORDER_DATE_INVALID = "Order date is not acceptable.";
	public static final String REGION_CANNOT_BE_NULL = "Region name cannot be null.";
	public static final String REP_CANNOT_BE_NULL = "Sales representative name cannot be null.";
	public static final String ITEM_CANNOT_BE_NULL = "Item name cannot be null.";
	public static final String UNITS_VALUE_INVALID = "Unit count must be between 2 to 100.";
	public static final String UNIT_COST_INVALID = "Unit cost is invalid.";
	public static final String UNKNOWN_ERROR_OCCURED = "Unknown error occured while saving order.";
	public static final String SUCCESS_CREATE = "Successfully created the sales order.";
	public static final String INVALID_SEARCH_PARAMETER = "Invalid search parameter received.";
	public static final String NO_DATA_FOUND = "Sorry, No data found.";
	public static final String SUCCESS_FETCH = "Fetch success.";
	public static final String INVALID_CHARACTER_IN_DATA = "Invalid character in input data.";
	public static final String INVALID_ORDER_ID = "Invalid sales order id received.";
	public static final String FAILURE_TO_DELETE = "Failed to delete the order.";
	public static final String SUCCESS_DELETE = "Successfully deleted the Order.";
	**/
	
	ORDER_DATE_INVALID,
	REGION_CANNOT_BE_NULL,
	REP_CANNOT_BE_NULL,
	ITEM_CANNOT_BE_NULL,
	UNITS_VALUE_INVALID,
	UNIT_COST_INVALID,
	UNKNOWN_ERROR_OCCURED,
	SUCCESS_CREATE,
	INVALID_SEARCH_PARAMETER,
	NO_DATA_FOUND,
	SUCCESS_FETCH,
	INVALID_CHARACTER_IN_DATA,
	INVALID_ORDER_ID,
	FAILURE_TO_DELETE,
	SUCCESS_DELETE;
	
	public String getFinalString() {
		return this.name(); 
	}
	
}
