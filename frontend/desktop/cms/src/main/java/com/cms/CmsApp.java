package com.cms;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CmsApp extends Application {

    @Override
    public void start(Stage stage){

        try {
            Parent root = FXMLLoader.load(getClass().getResource("menu.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            stage.setTitle("CMS");
            stage.setResizable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void start(){
        launch();
    }

}
