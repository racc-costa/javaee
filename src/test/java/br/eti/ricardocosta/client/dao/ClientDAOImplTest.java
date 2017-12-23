package br.eti.ricardocosta.client.dao;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.powermock.reflect.Whitebox;

import br.eti.ricardocosta.client.dao.ClientDAO;
import br.eti.ricardocosta.client.dao.ClientDAOImpl;
import br.eti.ricardocosta.client.domain.Client;
import br.eti.ricardocosta.client.domain.ClientDataBuilder;
import br.eti.ricardocosta.exception.NotFoundException;

public class ClientDAOImplTest {

	private static EntityManager entityManager;
	private static ClientDAO dao;

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Rule
	public ErrorCollector errorCollector = new ErrorCollector();

	@BeforeClass
	public static void setup() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("H2PersistenceUnitTest");
		entityManager = entityManagerFactory.createEntityManager();
		dao = new ClientDAOImpl();
		Whitebox.setInternalState(dao, "entityManager", entityManager);
	}

	@Before
	public void beginTransaction() {
		entityManager.getTransaction().begin();
	}

	@After
	public void clearData() {
		if (entityManager.getTransaction().isActive()) {
			entityManager.getTransaction().commit();
		}
		entityManager.getTransaction().begin();
		entityManager.createNativeQuery("DELETE FROM CLIENT").executeUpdate();
		entityManager.getTransaction().commit();
	}

	@Test
	public final void testSave() {
		Client client = dao.save(new ClientDataBuilder().build());

		assertNotNull(client.getId());
		assertThat(ClientDataBuilder.NAME, equalTo(client.getName()));
		assertThat(ClientDataBuilder.EMAIL, equalTo(client.getEmail()));
		assertThat(ClientDataBuilder.REGISTRATION_DATE, equalTo(client.getRegistrationDate()));
		assertFalse(client.isAccessAllowed());
	}

	@Test
	public final void testFindById() {
		Client client = dao.save(new ClientDataBuilder().build());
		Client clientFinded = dao.findById(client.getId());

		errorCollector.checkThat(clientFinded.getId(), equalTo(client.getId()));
		errorCollector.checkThat(clientFinded.getEmail(), equalTo(client.getEmail()));
		errorCollector.checkThat(clientFinded.getName(), equalTo(client.getName()));
		errorCollector.checkThat(clientFinded.getRegistrationDate(), equalTo(client.getRegistrationDate()));
		errorCollector.checkThat(clientFinded.isAccessAllowed(), equalTo(client.isAccessAllowed()));
	}

	@Test
	public final void testFindByEmail() throws NotFoundException {
		dao.save(new ClientDataBuilder().build());

		Client clientFinded = dao.findByEmail(ClientDataBuilder.EMAIL);
		assertThat(clientFinded.getName(), equalTo(ClientDataBuilder.NAME));
		assertThat(clientFinded.getEmail(), equalTo(ClientDataBuilder.EMAIL));
		assertThat(ClientDataBuilder.REGISTRATION_DATE, equalTo(clientFinded.getRegistrationDate()));
	}

	@Test
	public final void testFindByEmailNotFound() throws NotFoundException {
		dao.save(new ClientDataBuilder().build());

		exception.expect(NotFoundException.class);
		exception.expectMessage("Client not found.");

		dao.findByEmail("c0@notfound.com");
	}

	@Test
		public final void testGetAll() {
			dao.save(new ClientDataBuilder().build());
			dao.save(new ClientDataBuilder().withEmail("c2@notfound.com").allowed().build());
			dao.save(new ClientDataBuilder().withEmail("c3@notfound.com").withName("Luis").build());
			List<Client> clients = dao.getAll();
	
			assertThat(clients.size(), equalTo(3));
		}

	@Test
	public final void testUpdate() {
		Client client = dao.save(new ClientDataBuilder().build());
		client.setName("Mary Jane");
		client.setEmail("mj@notfound.com");
		client.blockAccess();
		dao.update(client);

		Client clientFinded = dao.findById(client.getId());

		assertThat(client.getId(), equalTo(clientFinded.getId()));
		assertThat("Mary Jane", equalTo(clientFinded.getName()));
		assertThat("mj@notfound.com", equalTo(clientFinded.getEmail()));
		assertThat(client.getRegistrationDate(), equalTo(clientFinded.getRegistrationDate()));
		assertFalse(clientFinded.isAccessAllowed());
	}

	@Test
	public final void testRemove() throws NotFoundException {
		Client client = dao.save(new ClientDataBuilder().build());
		dao.remove(client.getId());
	}

	@Test
	public final void testRemoveNotFound() {
		dao.save(new ClientDataBuilder().build());

		try {
			dao.remove(888888l);
		} catch (NotFoundException e) {
			entityManager.getTransaction().rollback();
			assertThat(e.getMessage(), equalTo("Client not found."));
		}
	}
}
