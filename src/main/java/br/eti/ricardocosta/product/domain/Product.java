package br.eti.ricardocosta.product.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import br.eti.ricardocosta.exception.BusinessException;

@Entity
@Table(name = "PRODUCT")
@Cacheable
@Cache(region = "domainCache", usage = CacheConcurrencyStrategy.READ_WRITE)
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "PRD_ID", columnDefinition = "NUMBER(6)")
	private Long id;

	@Column(name = "PRD_NAME", nullable = false, columnDefinition = "VARCHAR(60)")
	private String name;

	@Column(name = "PRD_PRICE", nullable = false, columnDefinition = "NUMBER(6,2)")
	private BigDecimal price;

	@Column(name = "PRD_STOCK", columnDefinition = "NUMBER(6)")
	private Long stock;

	protected Product() {

	}

	public Product(Long id, String name, BigDecimal price) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public Long getStock() {
		return stock;
	}

	public void updateStock(Long stock) {
		if (this.stock == null) {
			this.stock = 0l;
		}
		this.stock += stock;
	}

	public boolean isAvailable() {
		return (this.stock != null && this.stock > 0);
	}

	public BigDecimal calculateTotalAmount(Long quantity) throws BusinessException {
		if (this.stock < quantity) {
			throw new BusinessException("Quantity not available.");
		}

		return this.price.multiply(BigDecimal.valueOf(quantity));
	}

	public void sell(Long quantity) throws BusinessException {
		if (this.stock < quantity) {
			throw new BusinessException("Quantity not available.");
		}

		this.stock = this.stock - quantity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((stock == null) ? 0 : stock.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (stock == null) {
			if (other.stock != null)
				return false;
		} else if (!stock.equals(other.stock))
			return false;
		return true;
	}
}
