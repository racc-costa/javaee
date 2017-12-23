package br.eti.ricardocosta.client.domain;

import java.time.LocalDate;

import br.eti.ricardocosta.util.DateUtil;

public class ClientDataBuilder {
	public static String NAME = "Josh Silva";
	public static String EMAIL = "josh.silva@zentech.com";
	public static String PASSWORD = "12345";
	public static LocalDate REGISTRATION_DATE = DateUtil.today();

	private String name = NAME;
	private String email = EMAIL;
	private String password = PASSWORD;
	private LocalDate registrationDate = REGISTRATION_DATE;
	private ClientAccess status;

	public Client build() {
		Client client = new Client(name, email, registrationDate);
		if (status != null && status == ClientAccess.ALLOWED) {
			client.allowAccess(password);
		}
		if (status != null && status == ClientAccess.BLOCKED) {
			client.blockAccess();
		}

		return client;
	}

	public ClientDataBuilder withName(String name) {
		this.name = name;
		return this;
	}

	public ClientDataBuilder withEmail(String email) {
		this.email = email;
		return this;
	}

	public ClientDataBuilder withRegistrationDate(LocalDate registrationDate) {
		this.registrationDate = registrationDate;
		return this;
	}

	public ClientDataBuilder allowed() {
		this.status = ClientAccess.ALLOWED;
		return this;
	}
}
