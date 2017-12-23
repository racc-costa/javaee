package br.eti.ricardocosta.client.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import br.eti.ricardocosta.client.dao.ClientDAOImpl;
import br.eti.ricardocosta.client.domain.Client;
import br.eti.ricardocosta.client.domain.ClientDataBuilder;
import br.eti.ricardocosta.client.service.ClientService;
import br.eti.ricardocosta.exception.BusinessException;
import br.eti.ricardocosta.exception.InfrastructureException;
import br.eti.ricardocosta.exception.NotFoundException;

@RunWith(PowerMockRunner.class)
@PrepareForTest(value = ClientService.class)
public class ClientServicePowerMockTest {

	@Mock
	private ClientDAOImpl clientDAO;

	@InjectMocks
	private ClientService clientService;

	@Test
	public final void testAllowAccess() throws BusinessException, NotFoundException, InfrastructureException {
		Client client = Mockito.mock(Client.class);
		when(clientDAO.findByEmail(ClientDataBuilder.EMAIL)).thenReturn(client);
		ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);

		//TODO PowerMockito.mockStatic(Math.class);
		// Mockito.when(Math.random()).thenReturn(Double.valueOf(0.655));

		clientService.allowAccess(ClientDataBuilder.EMAIL, ClientDataBuilder.PASSWORD);
		verify(client, times(1)).allowAccess((argument.capture()));
		assertThat("827ccbeea8a706c4c34a16891f84e7b", equalTo(argument.getValue()));
	}
}
