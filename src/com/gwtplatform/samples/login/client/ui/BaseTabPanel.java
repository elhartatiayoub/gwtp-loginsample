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

package com.gwtplatform.samples.login.client.ui;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.Tab;
import com.gwtplatform.mvp.client.TabPanel;

public abstract class BaseTabPanel extends Composite implements TabPanel {

  @UiField
  FlowPanel tabPanel;

  @UiField
  FlowPanel tabContentContainer;

  private final List<Tab> tabList = new ArrayList<Tab>();
  Tab currentActiveTab = null;
  
  public BaseTabPanel() {
    super();
  }

  @Override
  public Tab addTab(String text, String historyToken, float priority) {
    Tab newTab = CreateNewTab( priority );
    int beforeIndex;
    for( beforeIndex = 0; beforeIndex < tabList.size(); ++beforeIndex )
      if(newTab.getPriority() < tabList.get(beforeIndex).getPriority())
        break;
    tabPanel.insert(newTab.asWidget(), beforeIndex);
    tabList.add( beforeIndex, newTab );
    newTab.setText( text );
    newTab.setTargetHistoryToken( historyToken );
    return newTab;
  }


  @Override
  public void removeTab(Tab tab) {
    tabPanel.getElement().removeChild( tab.asWidget().getElement() );
    tabList.remove( tab );
  }

  @Override
  public void removeTabs() {
    for( Tab tab : tabList )
      tabPanel.getElement().removeChild( tab.asWidget().getElement() );
    tabList.clear();
  }


  @Override
  public void setActiveTab(Tab tab) {
    if( currentActiveTab != null )
      currentActiveTab.deactivate();
    if( tab != null )
      tab.activate();
    currentActiveTab = tab;
  }

  /**
   * Sets the content displayed in the main panel.
   * 
   * @param panelContent The {@link Widget} to set in the main panel, or {@code null} to clear the panel.
   */
  public void setPanelContent(Widget panelContent) {
    tabContentContainer.clear();
    if( panelContent != null )
      tabContentContainer.add( panelContent );
  }

  /**
   * Returns a new tab of the type specific for this tab panel.
   * 
   * @param priority The desired priority of the new tab.
   * @return The new tab.
   */
  protected abstract Tab CreateNewTab(float priority);  

}