package br.eti.ricardocosta.util;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateUtil {

	public static LocalDate zeroDay() {
		return LocalDate.of(1970, 1, 1);
	}

	public static LocalDate today() {
		return LocalDate.now();
	}
	
	public static LocalDateTime now() {
		return LocalDateTime.now();
	}

	public static LocalDate yesterday() {
		return LocalDate.now().minusDays(1);
	}
}
