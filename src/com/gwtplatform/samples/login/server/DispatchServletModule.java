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

import com.google.inject.servlet.ServletModule;
import com.gwtplatform.dispatch.server.DispatchServiceImpl;
import com.gwtplatform.dispatch.shared.ActionImpl;

public class DispatchServletModule extends ServletModule {

  @Override
  public void configureServlets() {
    serve("/gwtpsample/" + ActionImpl.DEFAULT_SERVICE_NAME).with(DispatchServiceImpl.class);
  }
  
}
