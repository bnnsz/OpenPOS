package com.bizstudio;

import com.bizstudio.core.utils.AutowireHelper;
import com.bizstudio.security.services.SecurityService;
import com.bizstudio.view.pages.application.ApplicationContainer;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class OpenPOS extends Application {

    private ConfigurableApplicationContext springContext;
    
    ApplicationContainer root;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void init() throws Exception {
        springContext = SpringApplication.run(OpenPOS.class);
        SecurityService securityService = springContext.getBean(SecurityService.class);
        DefaultSecurityManager securityManager = new DefaultSecurityManager(securityService);
        DefaultSessionManager sessionManager = (DefaultSessionManager) securityManager.getSessionManager();
        sessionManager.setSessionDAO(securityService);
        sessionManager.setSessionListeners(Arrays.asList(securityService));
        SecurityUtils.setSecurityManager(securityManager);
        AutowireHelper.getInstance().setApplicationContext(springContext);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/application/Application.fxml"));
        fxmlLoader.setControllerFactory(springContext::getBean);
        fxmlLoader.setResources(ResourceBundle.getBundle("bundles.application.lang", new Locale("en")));
        fxmlLoader.setRoot(springContext.getBean(ApplicationContainer.class));
        root = fxmlLoader.load();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/application/colors.css");
        scene.getStylesheets().add("/styles/themes/default.css");
        scene.getStylesheets().add("/styles/application/application.css");
        stage.setTitle("Open POS");
        stage.setScene(scene);
        
        scene.addEventFilter(EventType.ROOT, event -> {
            if (event.getEventType() != MouseEvent.MOUSE_MOVED
                    && event.getEventType() != MouseEvent.MOUSE_ENTERED_TARGET
                    && event.getEventType() != MouseEvent.MOUSE_EXITED_TARGET) {

                try {
                    Session session = SecurityUtils.getSubject().getSession(false);
                    if (session != null) {
                        session.touch();
                    }
                } catch (Exception e) {

                }

            }

        });
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        springContext.close();
    }
}
