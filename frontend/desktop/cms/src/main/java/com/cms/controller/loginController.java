package com.cms.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class loginController {
    
    @FXML
    TextField userIdTextField;

    @FXML
    PasswordField passwordTextField;

    @FXML
    Button loginButton;


    public void login(ActionEvent event) {

        int userid = Integer.parseInt(userIdTextField.getText());
        
    }

}
