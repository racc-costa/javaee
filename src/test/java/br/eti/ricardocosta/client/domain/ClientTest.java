package br.eti.ricardocosta.client.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.powermock.reflect.Whitebox.getInternalState;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.eti.ricardocosta.exception.ErrorCode;
import br.eti.ricardocosta.exception.RequiredException;
import br.eti.ricardocosta.exception.ValidationException;
import br.eti.ricardocosta.util.DateUtil;

public class ClientTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Rule
	public ErrorCollector errorCollector = new ErrorCollector();

	@Test
	public void testClient() throws RequiredException, ValidationException {
		Client client = new ClientDataBuilder().build();
		client.validate();
		errorCollector.checkThat(client.getId(), equalTo(null));
		errorCollector.checkThat(client.getLastAccessDate(), equalTo(null));
		errorCollector.checkThat(client.getName(), equalTo(ClientDataBuilder.NAME));
		errorCollector.checkThat(client.getEmail(), equalTo(ClientDataBuilder.EMAIL));
		errorCollector.checkThat(client.getRegistrationDate(), equalTo(ClientDataBuilder.REGISTRATION_DATE));
		errorCollector.checkThat(client.isAccessAllowed(), equalTo(false));
	}
	
	@Test
	public void testEqualsAndHashCode() throws Exception {
		Client client = new ClientDataBuilder().build();
		Client client2 = new ClientDataBuilder().build();
		assertTrue(client.equals(client2));
		assertFalse(client.equals(null));
		assertFalse(client.equals(String.class));
		assertThat(client.hashCode(), equalTo(client2.hashCode()));
	}

	@Test(expected = RequiredException.class)
	public void testNameIsNull() throws RequiredException, ValidationException {
		Client client = new ClientDataBuilder().withName(null).build();
		client.validate();
	}

	@Test(expected = RequiredException.class)
	public void testNameIsEmpty() throws RequiredException, ValidationException {
		Client client = new ClientDataBuilder().withName("").build();
		client.validate();
	}

	@Test
	public void testEmailIsNull() throws ValidationException {
		Client client = new ClientDataBuilder().withEmail(null).build();
		try {
			client.validate();
			fail("E-mail validation fail.");
		} catch (RequiredException e) {
			assertThat(e.getMessage(), equalTo("E-mail is required."));
		}
	}

	@Test
	public void testEmailIsEmpty() throws ValidationException {
		Client client = new ClientDataBuilder().withEmail("").build();
		try {
			client.validate();
			fail("E-mail validation fail.");
		} catch (RequiredException e) {
			assertThat(e.getMessage(), equalTo("E-mail is required."));
		}
	}

	@Test
	public void testRegistrationDateIsNull() throws RequiredException, ValidationException {
		Client client = new ClientDataBuilder().withRegistrationDate(null).build();
		exception.expect(RequiredException.class);
		exception.expectMessage("Registration date is required.");
		client.validate();
	}

	@Test
	public void testRegistrationDateIsBeforeToday() throws RequiredException, ValidationException {
		Client client = new ClientDataBuilder().withRegistrationDate(DateUtil.yesterday()).build();
		exception.expect(ValidationException.class);
		exception.expectMessage("Invalid registration date.");
		exception.expect(hasProperty("errorCode", equalTo(ErrorCode.INVALID_REGISTRATION_DATE)));
		client.validate();
	}

	@Test
	public void testAllowAccess() {
		Client client = new ClientDataBuilder().build();
		assertFalse(client.isAccessAllowed());
		client.allowAccess(ClientDataBuilder.PASSWORD);
		assertTrue(client.isAccessAllowed());
		assertThat(getInternalState(client, "password"), equalTo(ClientDataBuilder.PASSWORD));
	}

	@Test
	public void testBlockAccess() {
		Client client = new ClientDataBuilder().allowed().build();
		assertTrue(client.isAccessAllowed());
		client.blockAccess();
		assertFalse(client.isAccessAllowed());
	}

	@Test
	public void testWrongPassword() {
		Client client = new ClientDataBuilder().allowed().build();
		assertFalse(client.verifyPassword("123"));
	}

	@Test
	public void testPassword() {
		Client client = new ClientDataBuilder().allowed().build();
		assertTrue(client.verifyPassword(ClientDataBuilder.PASSWORD));
	}
	
	@Test
	public void testUpdateLastAccessDate() {
		Client client = new ClientDataBuilder().allowed().build();
		client.updateLastAccessDate();
	}
}
