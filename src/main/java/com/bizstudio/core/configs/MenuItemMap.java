/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.core.configs;

import com.bizstudio.core.enums.NavigationRoute;
import com.bizstudio.view.components.util.MenuItem;
import static com.bizstudio.view.components.util.MenuItem.menu;
import static java.util.Arrays.asList;
import java.util.List;
import java.util.function.Supplier;

/**
 *
 * @author obinna.asuzu
 */
public enum MenuItemMap {
    MAIN(MenuItemMap::mainMenuItems),
    INVENTORY(MenuItemMap::inventoryMenuItems),
    ADMIN(MenuItemMap::adminMenuItems);

    Supplier<List<MenuItem>> menuItems;

    private MenuItemMap(Supplier<List<MenuItem>> menuItems) {
        this.menuItems = menuItems;
    }

    public List<MenuItem> getMenuItems() {
        return menuItems.get();
    }

    private static List<MenuItem> inventoryMenuItems() {
        return asList(menu("Items", NavigationRoute.HOME),
                menu("Categories", NavigationRoute.HOME),
                menu("Locations", NavigationRoute.HOME),
                menu("Settings", NavigationRoute.SETTINGS),
                menu("Home", NavigationRoute.MENU));
    }

    private static List<MenuItem> mainMenuItems() {
        return asList(menu("Home", NavigationRoute.HOME),
                menu("Transactions", NavigationRoute.TRANSACTIONS),
                menu("Inventory", NavigationRoute.INVENTORY),
                menu("Admin", NavigationRoute.ADMIN),
                menu("Settings", NavigationRoute.SETTINGS));
    }

    private static List<MenuItem> adminMenuItems() {
        return asList(menu("Users", NavigationRoute.USERS),
                menu("Roles", NavigationRoute.ROLES),
                menu("Settings", NavigationRoute.SETTINGS),
                menu("Home", NavigationRoute.MENU));
    }

}
