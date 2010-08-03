package com.gwtplatform.samples.login.client.gatekeeper;

import com.google.inject.Inject;
import com.gwtplatform.mvp.client.proxy.Gatekeeper;
import com.gwtplatform.samples.login.shared.CurrentUser;

public class UserGatekeeper implements Gatekeeper {

	private final CurrentUser currentUser;

	@Inject
	public UserGatekeeper(final CurrentUser currentUser) {
		this.currentUser = currentUser;
	}

	@Override
	public boolean canReveal() {
		return currentUser.getRoles().contains("ROLE_USER");
	}
}
