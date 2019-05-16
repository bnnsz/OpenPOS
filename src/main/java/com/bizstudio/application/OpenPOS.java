/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.application;

import com.bizstudio.security.services.BasicIniEnvironment;
import com.bizstudio.ui.pages.application.ApplicationContainer;
import javafx.application.Application;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.env.Environment;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;

/**
 *
 * @author ObinnaAsuzu
 */
public class OpenPOS extends Application {


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("/fxml/application/Application.fxml"));
ApplicationContainer root = new ApplicationContainer();

Scene scene = new Scene(root);
scene.getStylesheets().add("/styles/application/application.css");

stage.setTitle("Open POS");
stage.setScene(scene);

Environment env = new BasicIniEnvironment("classpath:shiro.ini");
SecurityManager securityManager = env.getSecurityManager();
// Make the SecurityManager instance available to the entire application
// via static memory:
SecurityUtils.setSecurityManager(securityManager);

scene.addEventFilter(EventType.ROOT, event -> {
    if (event.getEventType() != MouseEvent.MOUSE_MOVED
            && event.getEventType() != MouseEvent.MOUSE_ENTERED_TARGET
            && event.getEventType() != MouseEvent.MOUSE_EXITED_TARGET) {
        Session session = SecurityUtils.getSubject().getSession(false);
        if (session != null) {
            session.touch();
        }
    }
    
});
stage.show();

    }

}
