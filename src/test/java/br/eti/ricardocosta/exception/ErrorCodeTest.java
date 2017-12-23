package br.eti.ricardocosta.exception;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import org.junit.Test;

import br.eti.ricardocosta.exception.ErrorCode;


public class ErrorCodeTest {
   @Test
   public void testValues() {
      ErrorCode[] values = ErrorCode.values();
      assertThat(values[0], equalTo(ErrorCode.INVALID_REGISTRATION_DATE));
   }

   @Test
   public void testValueOf() {
      assertThat(ErrorCode.valueOf("INVALID_REGISTRATION_DATE"), equalTo(ErrorCode.INVALID_REGISTRATION_DATE));
   }
}
