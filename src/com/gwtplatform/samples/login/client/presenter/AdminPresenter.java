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

import com.google.inject.Inject;
import com.gwtplatform.mvp.client.EventBus;
import com.gwtplatform.mvp.client.PresenterImpl;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.TabInfo;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.gwtplatform.mvp.client.proxy.TabContentProxyPlace;
import com.gwtplatform.samples.login.client.NameTokens;
import com.gwtplatform.samples.login.client.gatekeeper.AdminGatekeeper;

/**
 * @author Christian Goudreau
 */
public class AdminPresenter extends
		PresenterImpl<AdminPresenter.MyView, AdminPresenter.MyProxy> {
	public interface MyView extends View {
	}

	@ProxyCodeSplit
	@NameToken(NameTokens.adminPage)
	@TabInfo(container = MainPagePresenter.class, priority = 1, label = "Admin Tab")
	@UseGatekeeper(AdminGatekeeper.class)
	public interface MyProxy extends TabContentProxyPlace<AdminPresenter> {
	}

	@Inject
	public AdminPresenter(final EventBus eventBus, final MyView view,
			final MyProxy proxy) {
		super(eventBus, view, proxy);
	}

	@Override
	protected void revealInParent() {
		RevealContentEvent.fire(eventBus, MainPagePresenter.TYPE_SetTabContent,
				this);
	}
}