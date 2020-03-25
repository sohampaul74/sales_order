package app.order.salesorder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@SpringBootApplication
public class SalesOrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalesOrderApplication.class, args);
	}
	
	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource rrms = new ReloadableResourceBundleMessageSource();
		rrms.setBasename("classpath:messages");
		rrms.setDefaultEncoding("utf-8");
		return rrms;
	}
	
}
