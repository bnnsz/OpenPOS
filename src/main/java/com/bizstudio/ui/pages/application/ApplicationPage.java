/*
 * Copyright 2018 BizStudioSoft.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bizstudio.ui.pages.application;

import com.bizstudio.enums.application.PageState;
import com.bizstudio.handlers.NavigationHandler;
import java.util.Map;
import javafx.scene.layout.HBox;

/**
 *
 * @author ObinnaAsuzu
 */
public abstract class ApplicationPage extends HBox implements NavigationHandler{

    private long pageID;
    private PageState pageState;
    
    

    /**
     * @return the pageID
     */
    public long getPageID() {
        return pageID;
    }

    /**
     * @param pageID the pageID to set
     */
    public void setPageID(long pageID) {
        this.pageID = pageID;
    }

    /**
     * @return the pageState
     */
    public PageState getPageState() {
        return pageState;
    }

    /**
     * @param pageState the pageState to set
     */
    public void setPageState(PageState pageState) {
        this.pageState = pageState;
    }
    
    
    public abstract void onPageCreate();
    
    public abstract void onPageDestroy();
    
    public abstract void onPageResume();
    
    public abstract void onPagePause();
    
    @Override
    public abstract boolean equals(Object object);

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (int) (this.pageID ^ (this.pageID >>> 32));
        return hash;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
    
    

}
