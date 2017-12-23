package br.eti.ricardocosta.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.Test;

public class DateUtilTest {

	@Test
	public void testZeroDay() throws Exception {
		assertThat(DateUtil.zeroDay(), equalTo(LocalDate.of(1970, 1, 1)));
	}

	@Test
	public void testToday() throws Exception {
		assertThat(DateUtil.today(), equalTo(LocalDate.now()));
	}

	@Test
	public void testNow() throws Exception {
		assertThat(DateUtil.now(), equalTo(LocalDateTime.now()));
	}

	@Test
	public void testYesterday() throws Exception {
		assertThat(DateUtil.yesterday(), equalTo(LocalDate.now().minusDays(1)));
	}
}
