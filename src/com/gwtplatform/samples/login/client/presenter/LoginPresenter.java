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

package com.gwtplatform.samples.login.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.client.DispatchAsync;
import com.gwtplatform.mvp.client.EventBus;
import com.gwtplatform.mvp.client.PresenterImpl;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.Place;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;
import com.gwtplatform.samples.login.client.NameTokens;
import com.gwtplatform.samples.login.shared.CurrentUser;
import com.gwtplatform.samples.login.shared.FieldVerifier;
import com.gwtplatform.samples.login.shared.LoginAction;
import com.gwtplatform.samples.login.shared.LoginResult;

public class LoginPresenter extends
		PresenterImpl<LoginPresenter.MyView, LoginPresenter.MyProxy> {

	public interface MyView extends View {
		public Button getSendButton();

		public void setError(String errorText);

		public String getName();

		public String getPassword();

		public void resetAndFocus();
	}

	private final DispatchAsync dispatcher;
	private CurrentUser user;

	@ProxyStandard
	@NameToken(NameTokens.loginPage)
	public interface MyProxy extends Proxy<LoginPresenter>, Place {
	}

	private final PlaceManager placeManager;

	@Inject
	public LoginPresenter(EventBus eventBus, MyView view, MyProxy proxy,
			PlaceManager placeManager, DispatchAsync dispatcher,
			CurrentUser user) {
		super(eventBus, view, proxy);
		this.placeManager = placeManager;
		this.dispatcher = dispatcher;
		this.user = user;
	}

	@Override
	protected void onBind() {
		super.onBind();
		registerHandler(getView().getSendButton().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						login();
					}
				}));
	}

	@Override
	protected void onReset() {
		super.onReset();
		getView().resetAndFocus();
	}

	@Override
	protected void revealInParent() {
		RevealRootContentEvent.fire(eventBus, this);
	}

	/**
	 * Send the name from the nameField to the server and wait for a response.
	 */
	private void login() {
		// First, we validate the input.
		getView().setError("");
		String loginText = getView().getName();
		String passwordText = getView().getPassword();

		if (!FieldVerifier.isValidName(loginText)) {
			getView().setError("Please enter at least four characters");
			return;
		}

		dispatcher.execute(new LoginAction(loginText, passwordText),
				new AsyncCallback<LoginResult>() {
					@Override
					public void onFailure(Throwable caught) {
						getView().setError(
								"An error occured: " + caught.getMessage());
					}

					@Override
					public void onSuccess(LoginResult result) {
						user.setRoles(result.getResponse().getRoles());
						user.setUsername(result.getResponse().getUsername());

						redirect();
					}
				});
	}

	private void redirect() {
		placeManager.revealPlace(new PlaceRequest(NameTokens.userPage));
	}

}
