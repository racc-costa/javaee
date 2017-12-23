package br.eti.ricardocosta.client.security;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import br.eti.ricardocosta.client.security.AuthenticationServer;
import br.eti.ricardocosta.exception.InfrastructureException;

public class AuthenticationServerTest {

	@Test
	public void testLogin() throws InfrastructureException {
		AuthenticationServer authenticationServer = new AuthenticationServer();
		String token = authenticationServer.login("email", "pass");
		assertNotNull(token);
	}

	@Test
	public void testCryptWithMD5() throws Exception {
		// TODO
	}
}
