package app.order.salesorder.service.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import app.order.salesorder.domain.SalesOrder;
import app.order.salesorder.model.FormSalesOrder;
import app.order.salesorder.repository.SalesOrderRepository;
import app.order.salesorder.service.SalesOrderService;
import app.order.salesorder.util.ReturnConstants;
import app.order.salesorder.util.SearchBy;

@Service
public class SalesOrderServiceImpl implements SalesOrderService {

	private static final Logger logger = LoggerFactory.getLogger(SalesOrderServiceImpl.class);
	
	private ConversionService conversionService;
	
	private SalesOrderRepository salesOrderRepo;
	
	public SalesOrderServiceImpl(ConversionService conversionService, SalesOrderRepository salesOrderRepo) {
		this.conversionService = conversionService;
		this.salesOrderRepo = salesOrderRepo;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String saveOrder(FormSalesOrder formSalesOrder) {
		logger.info("saveOrder called with:"+formSalesOrder.toString());
		try {
			SalesOrder so = conversionService.convert(formSalesOrder, SalesOrder.class);
			
			so.setId(buildId(so.getRep(),so.getRegion(),so.getOrderDate()));
			SalesOrder savedOrder = salesOrderRepo.save(so);
			return savedOrder.getId();
		} catch (Exception exp) {
			return ReturnConstants.FAILURE;
		}
	}

	private String buildId(String rep, String region, Timestamp orderDate) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		sb.append(rep.substring(0, 2).toUpperCase());
		sb.append(region.substring(0, 2).toUpperCase());
		sb.append(orderDate.toLocalDateTime().toLocalDate().format(DateTimeFormatter.ofPattern("yyMMdd")));
		sb.append(LocalDateTime.now().toLocalTime().format(DateTimeFormatter.ofPattern("hhmmss")));
		return sb.toString();
	}

	@Override
	public List<FormSalesOrder> searchOrder(SearchBy searchByEnum, String searchText) {
		
		switch(searchByEnum) {
			case ID:
				if(!salesOrderRepo.existsById(searchText)) {
					return null;
				}
				return Arrays.asList(conversionService.convert(salesOrderRepo.findById(searchText).get(), FormSalesOrder.class));
			case REGION:
				return salesOrderRepo.findByRegion(searchText)
						.parallelStream()
						.map(t->conversionService.convert(t, FormSalesOrder.class))
						.collect(Collectors.toList());
			case REP:
				return salesOrderRepo.findBySalesRep(searchText)
						.parallelStream()
						.map(t->conversionService.convert(t, FormSalesOrder.class))
						.collect(Collectors.toList());
			case ALL:
				return salesOrderRepo.findAll()
						.parallelStream()
						.map(t->conversionService.convert(t, FormSalesOrder.class))
						.collect(Collectors.toList());
			default:
				String[] timestamps = searchText.split("--");
				Timestamp from = Timestamp.valueOf(LocalDateTime.ofInstant(Instant.ofEpochSecond(Long.valueOf(timestamps[0])), ZoneId.systemDefault()));
				Timestamp to = Timestamp.valueOf(LocalDateTime.ofInstant(Instant.ofEpochSecond(Long.valueOf(timestamps[1])), ZoneId.systemDefault()));
				return salesOrderRepo
						.findBetweenDates(from, to)
						.parallelStream()
						.map(t->conversionService.convert(t, FormSalesOrder.class))
						.collect(Collectors.toList());
		}
		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String deleteOrder(String id) {
		logger.info("deleteOrder called with id:"+id);
		SalesOrder so = salesOrderRepo.findById(id).orElse(null);
		
		if(null == so) {
			return ReturnConstants.FAILURE;
		} else {
			salesOrderRepo.deleteById(id);
			return ReturnConstants.SUCCESS;
		}
	}

}
