package com.cms.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class MenuController{

    @FXML
    private Button button;

    @FXML
    private ImageView icon1;

    @FXML
    private void initialize() {
        icon1.setVisible(true);
    }

    @FXML
    private void handleMouseEnter() {
      
        icon1.setOpacity(0);
    }

    @FXML
    private void handleMouseExit() {
        icon1.setVisible(true);
    }

    public void test(ActionEvent event){
        System.out.println("pressed");
    }
}
