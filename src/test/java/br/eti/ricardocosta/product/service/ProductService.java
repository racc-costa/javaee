package br.eti.ricardocosta.product.service;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import br.eti.ricardocosta.product.dao.ProductDAO;
import br.eti.ricardocosta.product.domain.Product;

@Stateless
@LocalBean
public class ProductService {

	@Inject
	@Dependent
	private ProductDAO productDAO;

	public Product findById(Long productID) {
		return productDAO.findById(productID);
	}

	public List<Product> getAll() {
		return productDAO.getAll();
	}
}
