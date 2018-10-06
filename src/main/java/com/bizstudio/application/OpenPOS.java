/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.application;

import com.bizstudio.xmpp.dtos.XMPPMessage;
import com.bizstudio.xmpp.dtos.XMPPPayload;
import com.bizstudio.xmpp.enums.XmppPayloadAction;
import com.bizstudio.xmpp.events.XmppMessageEventListener;
import com.bizstudio.application.managers.NetworkCommunicationManager;
import com.bizstudio.ui.pages.application.ApplicationContainer;
import com.google.gson.Gson;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author ObinnaAsuzu
 */
public class OpenPOS extends Application {

    @Override
    public void start(Stage stage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("/fxml/application/Application.fxml"));
        ApplicationContainer root = new ApplicationContainer();

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/application/application.css");

        stage.setTitle("JavaFX and Maven");
        stage.setScene(scene);
        
        
        NetworkCommunicationManager.getInstance().addIncomingMessageListener(XmppPayloadAction.INENTORY_TRANSFER, (String from, String message, byte[] payload) -> {
            Test obj = new Gson().fromJson(new String(payload), Test.class);
            
            System.out.println("recived me=======>"+obj);
        });

        byte[] data = new Gson().toJson(new Test()).getBytes();
       
        NetworkCommunicationManager.getInstance().sendData(new XMPPMessage("Hi! World", new XMPPPayload(XmppPayloadAction.INENTORY_TRANSFER, data)), "bnnsz@asuzuobinna.com");
        stage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public class Test implements Serializable{

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * @param name the name to set
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * @return the id
         */
        public long getId() {
            return id;
        }

        /**
         * @param id the id to set
         */
        public void setId(long id) {
            this.id = id;
        }
        private String name = "Test Data";
        private long id = 200;

        public Test() {
        }

        @Override
        public String toString() {
            return new Gson().toJson(Test.this);
        }

    }

}
