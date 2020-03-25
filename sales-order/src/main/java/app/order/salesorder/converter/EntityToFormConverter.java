package app.order.salesorder.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import app.order.salesorder.domain.SalesOrder;
import app.order.salesorder.model.FormSalesOrder;

@Component
public class EntityToFormConverter implements Converter<SalesOrder, FormSalesOrder> {

	@Override
	public FormSalesOrder convert(SalesOrder source) {
		return new ObjectMapper().convertValue(source, FormSalesOrder.class);
	}

}
