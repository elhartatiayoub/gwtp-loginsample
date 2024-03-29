/**
 * Copyright 2010 Philippe Beaudoin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gwtplatform.samples.login.server;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionHandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;
import com.gwtplatform.samples.login.shared.CurrentUser;
import com.gwtplatform.samples.login.shared.FieldVerifier;
import com.gwtplatform.samples.login.shared.LoginAction;
import com.gwtplatform.samples.login.shared.LoginResult;

public class LoginHandler implements ActionHandler<LoginAction, LoginResult> {

	private ServletContext servletContext;
	private Provider<HttpServletRequest> requestProvider;

	@Inject
	LoginHandler(ServletContext servletContext,
			Provider<HttpServletRequest> requestProvider) {
		this.servletContext = servletContext;
		this.requestProvider = requestProvider;
	}

	@Override
	public LoginResult execute(LoginAction action, ExecutionContext context)
			throws ActionException {

		String username = action.getUsername();
		String password = action.getPassword();

		// Verify that the input is valid.
		if (!FieldVerifier.isValidName(username)) {
			// If the input is not valid, throw an IllegalArgumentException back
			// to
			// the client.
			System.out.println("Exception!");
			throw new ActionException("Name must be at least 4 characters long");
		}

		CurrentUser user = isUserValid(username, password);

		return new LoginResult(user);
	}

	/* This method should be using spring-security or something like that */
	private CurrentUser isUserValid(String username, String password) {

		CurrentUser user = null;

		if ("admin".equals(username) && "admin".equals(password)) {
			user = new CurrentUser();
			user.setUsername("admin");
			ArrayList<String> roles = new ArrayList<String>(2);
			roles.add("ROLE_USER");
			roles.add("ROLE_ADMIN");
			user.setRoles(roles);
		} else if ("user".equals(username) && "user".equals(password)) {
			user = new CurrentUser();
			user.setUsername("user");
			ArrayList<String> roles = new ArrayList<String>(1);
			roles.add("ROLE_USER");
			user.setRoles(roles);
		}

		return user;

	}

	@Override
	public void undo(LoginAction action, LoginResult result,
			ExecutionContext context) throws ActionException {
		// Not undoable
	}

	@Override
	public Class<LoginAction> getActionType() {
		return LoginAction.class;
	}

}
