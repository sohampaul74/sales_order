package app.order.salesorder.converter;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import app.order.salesorder.domain.SalesOrder;
import app.order.salesorder.model.FormSalesOrder;

@Component
public class FormToEntityConverter implements Converter<FormSalesOrder, SalesOrder> {

	@Override
	public SalesOrder convert(FormSalesOrder source) {
		// TODO Auto-generated method stub
		SalesOrder salesOrder = new SalesOrder();
		salesOrder.setItem(source.getItem());
		salesOrder.setRegion(source.getRegion());
		salesOrder.setRep(source.getRep());
		salesOrder.setOrderDate(Timestamp.valueOf(LocalDateTime.ofInstant(Instant.ofEpochSecond(source.getOrderDate()), ZoneId.systemDefault())));
		salesOrder.setUnits(source.getUnits());
		salesOrder.setUnitCost(Double.valueOf(source.getUnitCost()));
		return salesOrder;
	}
	
}
