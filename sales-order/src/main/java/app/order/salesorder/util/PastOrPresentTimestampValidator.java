package app.order.salesorder.util;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

@Component
public class PastOrPresentTimestampValidator implements ConstraintValidator<PastOrPresentTimestamp, Long> {
	
	@Override
	public boolean isValid(Long value, ConstraintValidatorContext context) {
		Timestamp timestamp = Timestamp.valueOf(LocalDateTime.ofInstant(Instant.ofEpochSecond(value), ZoneId.systemDefault()));
		System.out.println(timestamp.toString());
		return !timestamp.after(new Date());
	}

}
