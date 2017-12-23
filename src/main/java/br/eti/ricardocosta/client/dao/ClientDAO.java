package br.eti.ricardocosta.client.dao;

import java.util.List;

import br.eti.ricardocosta.client.domain.Client;
import br.eti.ricardocosta.exception.NotFoundException;

public interface ClientDAO {

	Client save(Client client);

	Client findById(Long clientID);

	Client findByEmail(String email) throws NotFoundException;

	List<Client> getAll();

	void update(Client client);

	void remove(Long clientId) throws NotFoundException;
}
