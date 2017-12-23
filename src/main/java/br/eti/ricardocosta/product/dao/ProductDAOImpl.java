package br.eti.ricardocosta.product.dao;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.eti.ricardocosta.product.domain.Product;

@Dependent
public class ProductDAOImpl implements ProductDAO {

	@PersistenceContext(unitName = "H2PersistenceUnit")
	EntityManager entityManager;

	@Override
	public Product save(Product product) {
		entityManager.persist(product);
		entityManager.flush();
		entityManager.refresh(product);
		return product;
	}

	@Override
	public Product findById(Long productID) {
		return entityManager.find(Product.class, productID);
	}
	
	@SuppressWarnings("unchecked")
	public List<Product> getAll() {
		Query query = entityManager.createQuery("SELECT p FROM " + Product.class.getCanonicalName() + " p");
		query.setHint("org.hibernate.cacheable", true);
		query.setHint("org.hibernate.cacheMode", "NORMAL");
		return query.getResultList();
	}
}
