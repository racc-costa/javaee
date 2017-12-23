package br.eti.ricardocosta.product.dao;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

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
import org.powermock.reflect.Whitebox;

import br.eti.ricardocosta.product.dao.ProductDAO;
import br.eti.ricardocosta.product.dao.ProductDAOImpl;
import br.eti.ricardocosta.product.domain.Product;
import br.eti.ricardocosta.product.domain.ProductDataBuilder;

public class ProductDAOImplTest {

	private static EntityManager entityManager;
	private static ProductDAO dao;

	@Rule
	public ErrorCollector errorCollector = new ErrorCollector();

	@BeforeClass
	public static void setup() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("H2PersistenceUnitTest");
		entityManager = entityManagerFactory.createEntityManager();
		dao = new ProductDAOImpl();
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
		entityManager.createNativeQuery("DELETE FROM PRODUCT").executeUpdate();
		entityManager.getTransaction().commit();
	}

	@Test
	public void testSave() throws Exception {
		Product product = dao.save(new ProductDataBuilder().build());
		assertThat(product.getId(), equalTo(ProductDataBuilder.ID));
		assertThat(product.getName(), equalTo(ProductDataBuilder.NAME));
		assertThat(product.getPrice(), equalTo(ProductDataBuilder.PRICE));
		assertThat(product.getStock(), equalTo(ProductDataBuilder.STOCK));
	}

	@Test
	public void testFindById() throws Exception {
		Product product = dao.save(new ProductDataBuilder().withId(2l).build());
		Product productFinded = dao.findById(product.getId());

		errorCollector.checkThat(productFinded.getId(), equalTo(product.getId()));
		errorCollector.checkThat(productFinded.getName(), equalTo(product.getName()));
		errorCollector.checkThat(productFinded.getPrice(), equalTo(product.getPrice()));
		errorCollector.checkThat(productFinded.getStock(), equalTo(product.getStock()));
	}

	@Test
	public void testGetAll() throws Exception {
		dao.save(new ProductDataBuilder().withId(3l).build());
		dao.save(new ProductDataBuilder().withId(4l).build());
		List<Product> products = dao.getAll();
		
		assertThat(2, equalTo(products.size()));
	}
}
