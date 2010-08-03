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
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.Place;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;
import com.gwtplatform.samples.login.client.NameTokens;
import com.gwtplatform.samples.login.shared.CurrentUser;
import com.gwtplatform.samples.login.shared.LoginAction;
import com.gwtplatform.samples.login.shared.LoginResult;

public class ResponsePresenter extends
		PresenterImpl<ResponsePresenter.MyView, ResponsePresenter.MyProxy> {

	public static final String loginParam = "loginParam";
	public static final String passwordParam = "passwordParam";

	public interface MyView extends View {
		public Button getCloseButton();

		public void setTextToServer(String textToServer);

		public void setServerResponse(String serverResponse);
	}

	@ProxyCodeSplit
	@NameToken(NameTokens.responsePage)
	public interface MyProxy extends Proxy<ResponsePresenter>, Place {
	}

	private final PlaceManager placeManager;
	private final DispatchAsync dispatcher;
	private CurrentUser user;

	private String loginText = null;
	private String passwordText = null;

	@Inject
	public ResponsePresenter(EventBus eventBus, MyView view, MyProxy proxy,
			PlaceManager placeManager, DispatchAsync dispatcher,
			CurrentUser user) {
		super(eventBus, view, proxy);
		this.placeManager = placeManager;
		this.dispatcher = dispatcher;
		this.user = user;
	}

	@Override
	protected void revealInParent() {
		RevealRootContentEvent.fire(eventBus, this);
	}

	@Override
	protected void onBind() {
		super.onBind();
		registerHandler(getView().getCloseButton().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						placeManager.revealPlace(new PlaceRequest(
								NameTokens.loginPage));
					}
				}));
	}

	@Override
	protected void onReset() {
		super.onReset();
		getView().setTextToServer(loginText);
		getView().setServerResponse("Waiting for response...");
		dispatcher.execute(new LoginAction(loginText, passwordText),
				new AsyncCallback<LoginResult>() {
					@Override
					public void onFailure(Throwable caught) {
						getView().setServerResponse(
								"An error occured: " + caught.getMessage());
					}

					@Override
					public void onSuccess(LoginResult result) {
						user = result.getResponse();
						redirect();
					}
				});
	}

	@Override
	public void prepareFromRequest(PlaceRequest request) {
		super.prepareFromRequest(request);
		loginText = request.getParameter(loginParam, null);
		passwordText = request.getParameter(passwordParam, null);
	}

	@Override
	public PlaceRequest prepareRequest(PlaceRequest request) {
		request = super.prepareRequest(request);

		if (loginText != null && passwordText != null)
			return request.with(loginParam, loginText).with(passwordParam,
					passwordText);

		return request;
	}

	private void redirect() {
		placeManager.revealPlace(new PlaceRequest(NameTokens.mainPage));
	}
}
