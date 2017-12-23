package br.eti.ricardocosta.product.service;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import br.eti.ricardocosta.product.dao.ProductDAO;
import br.eti.ricardocosta.product.domain.Product;
import br.eti.ricardocosta.product.domain.ProductDataBuilder;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {
	
	@Mock
	private ProductDAO productDAO;
	
	@InjectMocks
	private ProductService productService;

	@Test
	public void testFindById() throws Exception {
		Product productMock = Mockito.mock(Product.class);
		Mockito.when(productDAO.findById(ProductDataBuilder.ID)).thenReturn(productMock);
		Product productFinded = productService.findById(ProductDataBuilder.ID);
		
		assertThat(productFinded, equalTo(productMock));
	}

	@Test
	public void testGetAll() throws Exception {
		productService.getAll();
	}
}
