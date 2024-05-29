package com.cms.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import javafx.stage.Stage;
import javafx.util.Duration;

public class MenuController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button evaluationButton;

    @FXML
    private Button homeButton;

    @FXML
    private Button classesButton;

    @FXML
    private Button studentButton;

    @FXML
    private Button mailButton;

    @FXML
    private Button settingsButton;

    @FXML
    private Button profileButton;

    @FXML
    private ImageView starIcon;

    @FXML
    private ImageView homeIcon;

    @FXML
    private ImageView classesIcon;

    @FXML
    private ImageView studentIcon;

    @FXML
    private ImageView mailIcon;

    @FXML
    private ImageView SettingsIcon;

    @FXML
    private ImageView ProfileIcon;

    private Image lightStarIcon;
    private Image darkStarIcon;

    private Image lightHomeIcon;
    private Image darkHomeIcon;

    private Image lightStudentIcon;
    private Image darkStudentIcon;

    private Image lightClassesIcon;
    private Image darkClassesIcon;

    private Image lightMailIcon;
    private Image darkMailIcon;

    private Image lightSettingsIcon;
    private Image darkSettingsIcon;

    private Image lightProfileIcon;
    private Image darkProfileIcon;

    // Map to store the original X positions of button texts
    private Map<Button, Double> originalPositions = new HashMap<>();

    @FXML
    public void initialize() {

        // Star Icon

        lightStarIcon = new Image(getClass().getResourceAsStream("/com/cms/assets/icon/star_light.png"));
        darkStarIcon = new Image(getClass().getResourceAsStream("/com/cms/assets/icon/star_dark.png"));

        starIcon.setImage(lightStarIcon);

        evaluationButton.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> starIcon.setImage(darkStarIcon));
        evaluationButton.addEventHandler(MouseEvent.MOUSE_EXITED, event -> starIcon.setImage(lightStarIcon));

        // Home Icon

        lightHomeIcon = new Image(getClass().getResourceAsStream("/com/cms/assets/icon/home_light.png"));
        darkHomeIcon = new Image(getClass().getResourceAsStream("/com/cms/assets/icon/home.png"));

        homeIcon.setImage(lightHomeIcon);

        homeButton.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> homeIcon.setImage(darkHomeIcon));
        homeButton.addEventHandler(MouseEvent.MOUSE_EXITED, event -> homeIcon.setImage(lightHomeIcon));

        // Classes Icon

        lightClassesIcon = new Image(getClass().getResourceAsStream("/com/cms/assets/icon/book_light.png"));
        darkClassesIcon = new Image(getClass().getResourceAsStream("/com/cms/assets/icon/book_dark.png"));

        classesIcon.setImage(lightClassesIcon);

        classesButton.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> classesIcon.setImage(darkClassesIcon));
        classesButton.addEventHandler(MouseEvent.MOUSE_EXITED, event -> classesIcon.setImage(lightClassesIcon));

        // Students Icon

        lightStudentIcon = new Image(getClass().getResourceAsStream("/com/cms/assets/icon/graduation_light.png"));
        darkStudentIcon = new Image(getClass().getResourceAsStream("/com/cms/assets/icon/graduation_dark.png"));

        studentIcon.setImage(lightStudentIcon);

        studentButton.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> studentIcon.setImage(darkStudentIcon));
        studentButton.addEventHandler(MouseEvent.MOUSE_EXITED, event -> studentIcon.setImage(lightStudentIcon));

        // Mail Icon

        lightMailIcon = new Image(getClass().getResourceAsStream("/com/cms/assets/icon/envelope_light.png"));
        darkMailIcon = new Image(getClass().getResourceAsStream("/com/cms/assets/icon/envelope _dark.png"));

        mailIcon.setImage(lightMailIcon);

        mailButton.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> mailIcon.setImage(darkMailIcon));
        mailButton.addEventHandler(MouseEvent.MOUSE_EXITED, event -> mailIcon.setImage(lightMailIcon));

        // Settings Icon

        lightSettingsIcon = new Image(getClass().getResourceAsStream("/com/cms/assets/icon/settings_light.png"));
        darkSettingsIcon = new Image(getClass().getResourceAsStream("/com/cms/assets/icon/settings_dark.png"));

        mailIcon.setImage(lightSettingsIcon);

        mailButton.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> mailIcon.setImage(darkSettingsIcon));
        mailButton.addEventHandler(MouseEvent.MOUSE_EXITED, event -> mailIcon.setImage(lightSettingsIcon));

        // Profile Icon

        lightProfileIcon = new Image(getClass().getResourceAsStream("/com/cms/assets/icon/user_light.png"));
        darkProfileIcon = new Image(getClass().getResourceAsStream("/com/cms/assets/icon/user_dark.png"));

        mailIcon.setImage(lightProfileIcon);

        mailButton.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> mailIcon.setImage(darkProfileIcon));
        mailButton.addEventHandler(MouseEvent.MOUSE_EXITED, event -> mailIcon.setImage(lightProfileIcon));

        setupButtonAnimation(evaluationButton);
        setupButtonAnimation(homeButton);
        setupButtonAnimation(classesButton);
        setupButtonAnimation(studentButton);
        setupButtonAnimation(mailButton);
        setupButtonAnimation(settingsButton);
        setupButtonAnimation(profileButton);
    }

    private void setupButtonAnimation(Button button) {
        originalPositions.put(button, button.getTranslateX());

        button.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            TranslateTransition tt = new TranslateTransition(Duration.millis(200), button.lookup(".text"));
            tt.setByX(20);
            tt.play();
        });

        button.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            TranslateTransition tt = new TranslateTransition(Duration.millis(200), button.lookup(".text"));
            tt.setToX(originalPositions.get(button));
            tt.play();
        });
    }

    public void switchToSchedule(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("/com/cms/schedule.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
