package br.eti.ricardocosta.product.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import br.eti.ricardocosta.exception.BusinessException;
import br.eti.ricardocosta.product.domain.Product;

public class ProductTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testProduct() throws Exception {
		Product product = new ProductDataBuilder().build();
		assertThat(ProductDataBuilder.ID, equalTo(product.getId()));
		assertThat(ProductDataBuilder.NAME, equalTo(product.getName()));
		assertThat(ProductDataBuilder.PRICE, equalTo(product.getPrice()));
	}
	
	@Test
	public void testEqualsAndHashCode() throws Exception {
		Product product = new ProductDataBuilder().build();
		Product product2 = new ProductDataBuilder().build();
		assertTrue(product.equals(product2));
		assertFalse(product.equals(null));
		assertFalse(product.equals(String.class));
		assertThat(product.hashCode(), equalTo(product2.hashCode()));
	}

	@Test
	public void testIsAvailable() throws Exception {
		Product product = new ProductDataBuilder().withStock(1l).build();
		assertTrue(product.isAvailable());
	}


	@Test
	public void testIsNotAvailableNull() throws Exception {
		Product product = new ProductDataBuilder().withStock(null).build();
		assertFalse(product.isAvailable());
	}
	
	@Test
	public void testIsNotAvailableZero() throws Exception {
		Product product = new ProductDataBuilder().withStock(0l).build();
		assertFalse(product.isAvailable());
	}

	@Test
	public void testIsNotAvailable() throws Exception {
		Product product = new ProductDataBuilder().withStock(0l).build();
		assertFalse(product.isAvailable());
	}

	@Test
	public void testCalculateTotalAmount() throws Exception {
		Product product = new ProductDataBuilder().withStock(10l).build();
		BigDecimal totalAmount = ProductDataBuilder.PRICE.multiply(new BigDecimal(5));
		assertThat(totalAmount, equalTo(product.calculateTotalAmount(5l)));
	}
	
	@Test
	public void testCalculateTotalAmountNoStock() throws Exception {
		Product product = new ProductDataBuilder().withStock(1l).build();

		exception.expect(BusinessException.class);
		exception.expectMessage("Quantity not available.");

		product.calculateTotalAmount(5l);
	}

	@Test
	public void testSell() throws Exception {
		Product product = new ProductDataBuilder().withStock(10l).build();
		product.sell(8l);
		assertThat(2l, equalTo(product.getStock()));
	}

	@Test
	public void testSellWithoutStock() throws Exception {
		Product product = new ProductDataBuilder().withStock(19l).build();

		exception.expect(BusinessException.class);
		exception.expectMessage("Quantity not available.");

		product.sell(20l);
	}

	@Test
	public void testUpdateStock() throws Exception {
		Product product = new ProductDataBuilder().build();
		assertThat(null, equalTo(product.getStock()));
		product.updateStock(10l);
		assertThat(10l, equalTo(product.getStock()));
		product.updateStock(5l);
		assertThat(15l, equalTo(product.getStock()));
	}
}
