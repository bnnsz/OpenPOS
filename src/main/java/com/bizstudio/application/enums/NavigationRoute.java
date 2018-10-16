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
package com.bizstudio.application.enums;

import com.bizstudio.ui.pages.application.ApplicationPage;
import com.bizstudio.ui.pages.application.HomePage;
import com.bizstudio.ui.pages.application.LoginPage;
import java.util.Locale;

/**
 *
 * @author ObinnaAsuzu
 */
public enum NavigationRoute {
    LOGIN(LoginPage.class),
    HOME(HomePage.class),
    SETTINGS(LoginPage.class),
    INVENTORY(LoginPage.class),
    TRANSACTIONS(LoginPage.class);
    
    Class<? extends ApplicationPage> page;
    private NavigationRoute(Class<? extends ApplicationPage> page) {
        this.page = page;
    }

    public Class<? extends ApplicationPage> page() {
        return page;
    }
}