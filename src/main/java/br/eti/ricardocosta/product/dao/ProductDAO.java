package br.eti.ricardocosta.product.dao;

import java.util.List;

import br.eti.ricardocosta.product.domain.Product;

public interface ProductDAO {

	Product save(Product product);

	Product findById(Long productID);

	List<Product> getAll();
}
