package br.eti.ricardocosta.client.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

import br.eti.ricardocosta.client.domain.ClientAccess;

public class ClientAccessTest {

	@Test
	public void testValues() {
		ClientAccess[] values = ClientAccess.values();
		assertThat(values[0], equalTo(ClientAccess.ALLOWED));
		assertThat(values[1], equalTo(ClientAccess.BLOCKED));
	}

	@Test
	public void testValueOf() {
		assertThat(ClientAccess.valueOf("ALLOWED"), equalTo(ClientAccess.ALLOWED));
		assertThat(ClientAccess.valueOf("BLOCKED"), equalTo(ClientAccess.BLOCKED));
	}
}
