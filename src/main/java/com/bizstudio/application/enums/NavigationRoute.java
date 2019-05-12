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

import com.bizstudio.application.configs.MenuItemMap;
import static com.bizstudio.application.configs.MenuItemMap.INVENTORY;
import com.bizstudio.ui.pages.application.ApplicationPage;
import com.bizstudio.ui.pages.application.HomePage;
import com.bizstudio.ui.pages.application.LoginPage;
import com.bizstudio.ui.pages.application.MenuPage;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 *
 * @author ObinnaAsuzu
 */
public enum NavigationRoute {
    LOGIN("Login",LoginPage.class),
    HOME("Home",HomePage.class),
    SETTINGS(LoginPage.class),
    INVENTORY("Inventory", MenuPage.class, NavigationRoute::inventoryMenuDefaultParams),
    TRANSACTIONS(LoginPage.class),
    MENU("Main menu",MenuPage.class,NavigationRoute::mainMenuDefaultParams);
    
    
    String name;
    Class<? extends ApplicationPage> page;
    Supplier<Map<String, Object>> defaultParameters;

    private NavigationRoute(String name, Class<? extends ApplicationPage> page, Supplier<Map<String, Object>> defaultParameters) {
        this.name = name;
        this.page = page;
        this.defaultParameters = defaultParameters;
    }
    
    

    private NavigationRoute(String name, Class<? extends ApplicationPage> page) {
        this.name = name;
        this.page = page;
    }
    
    public Map<String, Object> getDefaultParameters(){
        if(defaultParameters == null){
            return new HashMap<>();
        }
        return defaultParameters.get();
    }
    
    private NavigationRoute(Class<? extends ApplicationPage> page) {
        this.page = page;
    }

    public Class<? extends ApplicationPage> page() {
        return page;
    }
    
    private static Map<String, Object> inventoryMenuDefaultParams(){
        Map<String, Object> params = new HashMap<>();
        params.put("menu", MenuItemMap.INVENTORY);
        params.put("title", "Main menu");
        return params;
    }
    
    private static Map<String, Object> mainMenuDefaultParams(){
        Map<String, Object> params = new HashMap<>();
        params.put("menu", MenuItemMap.MAIN);
        params.put("title", "Inventory");
        return params;
    }
}
