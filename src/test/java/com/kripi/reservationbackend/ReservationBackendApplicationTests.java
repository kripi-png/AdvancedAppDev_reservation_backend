package com.kripi.reservationbackend;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(locations = {
"classpath*:spring/applicationContext.xml",
"classpath*:spring/applicationContext-jpa.xml",
"classpath*:spring/applicationContext-security.xml" })
class ReservationBackendApplicationTests {

	@Test
	void contextLoads() {
	}

}
