package app.order.salesorder.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import app.order.salesorder.domain.SalesOrder;

@Repository
public interface SalesOrderRepository extends JpaRepository<SalesOrder, String> {
	public List<SalesOrder> findByRegion(String region);

	@Query(value = "from SalesOrder where rep like %?1%")
	public List<SalesOrder> findBySalesRep(String searchText);
	
	@Query(value = "from SalesOrder where orderDate >= ?1 and orderDate<=?2")
	public List<SalesOrder> findBetweenDates(Timestamp from, Timestamp to);
}
