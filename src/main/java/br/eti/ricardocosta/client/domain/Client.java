package br.eti.ricardocosta.client.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import br.eti.ricardocosta.exception.ErrorCode;
import br.eti.ricardocosta.exception.RequiredException;
import br.eti.ricardocosta.exception.ValidationException;
import br.eti.ricardocosta.util.DateUtil;

@Entity
@Table(name = "CLIENT")
@Cacheable
@Cache(region = "domainCache", usage = CacheConcurrencyStrategy.READ_WRITE)
public class Client implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CLI_ID")
	private Long id;

	@Column(name = "CLI_NAME", nullable = false, columnDefinition = "VARCHAR(60)")
	private String name;

	@Column(name = "CLI_EMAIL", nullable = false, unique = true, columnDefinition = "VARCHAR(120)")
	private String email;

	@Column(name = "CLI_PASSWORD", columnDefinition = "VARCHAR(12)")
	private String password;

	@Column(name = "CLI_REGISTRATION_DATE", nullable = false)
	private LocalDate registrationDate;

	@Column(name = "CLI_LAST_ACCESS_DATE", nullable = true)
	private LocalDateTime lastAccessDate;

	@Column(name = "CLI_ACCESS_ALLOWED", nullable = false, columnDefinition = "NUMBER(1)")
	private ClientAccess accessAllowed;

	protected Client() {

	}

	public Client(String name, String email, LocalDate registrationDate) {
		super();
		this.name = name;
		this.email = email;
		this.registrationDate = registrationDate;
		this.accessAllowed = ClientAccess.BLOCKED;
	}

	public void allowAccess(String password) {
		this.password = password;
		this.accessAllowed = ClientAccess.ALLOWED;
	}

	public void blockAccess() {
		this.accessAllowed = ClientAccess.BLOCKED;
		this.password = null;
	}

	public void validate() throws RequiredException, ValidationException {
		if (name == null || name.isEmpty()) {
			throw new RequiredException("Name is required.");
		}

		if (email == null || email.isEmpty()) {
			throw new RequiredException("E-mail is required.");
		}

		if (registrationDate == null) {
			throw new RequiredException("Registration date is required.");
		}

		if (registrationDate.isBefore(DateUtil.today())) {
			throw new ValidationException("Invalid registration date.", ErrorCode.INVALID_REGISTRATION_DATE);
		}
	}

	public boolean verifyPassword(String password) {
		return this.password.equals(password);
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getRegistrationDate() {
		return registrationDate;
	}

	public LocalDateTime getLastAccessDate() {
		return lastAccessDate;
	}

	public void updateLastAccessDate() {
		this.lastAccessDate = DateUtil.now();
	}

	public boolean isAccessAllowed() {
		return this.accessAllowed.equals(ClientAccess.ALLOWED);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accessAllowed == null) ? 0 : accessAllowed.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastAccessDate == null) ? 0 : lastAccessDate.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((registrationDate == null) ? 0 : registrationDate.hashCode());
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
		Client other = (Client) obj;
		if (accessAllowed != other.accessAllowed)
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastAccessDate == null) {
			if (other.lastAccessDate != null)
				return false;
		} else if (!lastAccessDate.equals(other.lastAccessDate))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (registrationDate == null) {
			if (other.registrationDate != null)
				return false;
		} else if (!registrationDate.equals(other.registrationDate))
			return false;
		return true;
	}
}
