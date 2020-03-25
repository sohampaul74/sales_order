package app.order.salesorder.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Range;

import app.order.salesorder.util.PastOrPresentTimestamp;
import app.order.salesorder.util.ValidationConstants;

public class FormSalesOrder {
	
	@NotNull(message = ValidationConstants.ORDER_DATE_INVALID)
	@PastOrPresentTimestamp(message = ValidationConstants.ORDER_DATE_INVALID)
	private Long orderDate;
	
	@NotEmpty(message = ValidationConstants.REGION_CANNOT_BE_NULL)
	private String region;

	@NotEmpty(message = ValidationConstants.REP_CANNOT_BE_NULL)
	private String rep;

	@NotEmpty(message = ValidationConstants.ITEM_CANNOT_BE_NULL)
	private String item;

	@Range(min = 2, max = 100, message = ValidationConstants.UNITS_VALUE_INVALID)
	private Integer units;

	@Pattern(regexp = "[0-9\\.]{2,10}",message=ValidationConstants.UNIT_COST_INVALID)
	private String unitCost;
	
	public FormSalesOrder() {
		super();
	}
	
	public FormSalesOrder(Long orderDate, String region, String rep, String item, Integer units, String unitCost) {
		super();
		this.orderDate = orderDate;
		this.region = region;
		this.rep = rep;
		this.item = item;
		this.units = units;
		this.unitCost = unitCost;
	}
	
	public Long getOrderDate() {
		return orderDate;
	}
	public String getRegion() {
		return region;
	}
	public String getRep() {
		return rep;
	}
	public String getItem() {
		return item;
	}
	public Integer getUnits() {
		return units;
	}
	public String getUnitCost() {
		return unitCost;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((item == null) ? 0 : item.hashCode());
		result = prime * result + ((orderDate == null) ? 0 : orderDate.hashCode());
		result = prime * result + ((rep == null) ? 0 : rep.hashCode());
		result = prime * result + ((units == null) ? 0 : units.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FormSalesOrder other = (FormSalesOrder) obj;
		if (item == null) {
			if (other.item != null)
				return false;
		} else if (!item.equals(other.item))
			return false;
		if (orderDate == null) {
			if (other.orderDate != null)
				return false;
		} else if (!orderDate.equals(other.orderDate))
			return false;
		if (rep == null) {
			if (other.rep != null)
				return false;
		} else if (!rep.equals(other.rep))
			return false;
		if (units == null) {
			if (other.units != null)
				return false;
		} else if (!units.equals(other.units))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FormSalesOrder [orderDate=" + orderDate + ", region=" + region + ", rep=" + rep + ", item=" + item
				+ ", units=" + units + ", unitCost=" + unitCost + "]";
	}
	
}
