package com.gwtplatform.samples.login.client.gin;

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

import com.google.inject.Singleton;
import com.gwtplatform.mvp.client.DefaultEventBus;
import com.gwtplatform.mvp.client.DefaultProxyFailureHandler;
import com.gwtplatform.mvp.client.EventBus;
import com.gwtplatform.mvp.client.RootPresenter;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.proxy.ParameterTokenFormatter;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyFailureHandler;
import com.gwtplatform.mvp.client.proxy.TokenFormatter;
import com.gwtplatform.samples.login.client.MyPlaceManager;
import com.gwtplatform.samples.login.client.gatekeeper.AdminGatekeeper;
import com.gwtplatform.samples.login.client.gatekeeper.UserGatekeeper;
import com.gwtplatform.samples.login.client.presenter.AdminPresenter;
import com.gwtplatform.samples.login.client.presenter.LoginPresenter;
import com.gwtplatform.samples.login.client.presenter.MainPagePresenter;
import com.gwtplatform.samples.login.client.presenter.UserPresenter;
import com.gwtplatform.samples.login.client.view.AdminView;
import com.gwtplatform.samples.login.client.view.LoginView;
import com.gwtplatform.samples.login.client.view.MainPageView;
import com.gwtplatform.samples.login.client.view.UserView;
import com.gwtplatform.samples.login.shared.CurrentUser;

public class MyModule extends AbstractPresenterModule {

	@Override
	protected void configure() {
		bind(EventBus.class).to(DefaultEventBus.class).in(Singleton.class);
		bind(PlaceManager.class).to(MyPlaceManager.class).in(Singleton.class);
		bind(CurrentUser.class).in(Singleton.class);
		bind(AdminGatekeeper.class).in(Singleton.class);
		bind(UserGatekeeper.class).in(Singleton.class);
		bind(TokenFormatter.class).to(ParameterTokenFormatter.class).in(
				Singleton.class);
		bind(RootPresenter.class).asEagerSingleton();
		bind(ProxyFailureHandler.class).to(DefaultProxyFailureHandler.class)
				.in(Singleton.class);

		// Presenters
		bindPresenter(LoginPresenter.class, LoginPresenter.MyView.class,
				LoginView.class, LoginPresenter.MyProxy.class);

		bindPresenter(MainPagePresenter.class, MainPagePresenter.MyView.class,
				MainPageView.class, MainPagePresenter.MyProxy.class);

		bindPresenter(UserPresenter.class, UserPresenter.MyView.class,
				UserView.class, UserPresenter.MyProxy.class);

		bindPresenter(AdminPresenter.class, AdminPresenter.MyView.class,
				AdminView.class, AdminPresenter.MyProxy.class);
	}
}