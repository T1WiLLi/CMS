package com.cms.controller;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class MenuController {

    @FXML
    Pane pane;

    private Button[] buttons;

    private ImageView[] icons;

    @FXML
    private void initialize() {
        ObservableList<Node> children = pane.getChildren();
        List<Button> buttonList = new ArrayList<>();
        List<ImageView> iconList = new ArrayList<>();

        for (Node node : children) {
            if (node instanceof Button) {
                buttonList.add((Button) node);
            } else if (node instanceof ImageView) {
                iconList.add((ImageView) node);
            }
        }

        buttons = buttonList.toArray(new Button[0]);
        icons = iconList.toArray(new ImageView[0]);

        boundEventListenerToButtonElement();
    }

    private void boundEventListenerToButtonElement() {
        for (Button button : buttons) {
            button.setOnMouseEntered(e -> {
                ImageView associatedIcon = findAssociatedIcon(button);
                if (associatedIcon != null) {
                    associatedIcon.setVisible(false);
                }
            });

            button.setOnMouseExited(event -> {
                ImageView associatedIcon = findAssociatedIcon(button);
                if (associatedIcon != null) {
                    associatedIcon.setVisible(true);
                }
            });
        }
    }

    private ImageView findAssociatedIcon(Button button) {
        ImageView closestIcon = null;
        double closestDistance = Double.MAX_VALUE;

        double buttonCenterX = button.getLayoutX() + button.getWidth() / 2;
        double buttonCenterY = button.getLayoutY() + button.getHeight() / 2;

        for (ImageView icon : icons) {
            double iconCenterX = icon.getLayoutX() + icon.getFitWidth() / 2;
            double iconCenterY = icon.getLayoutY() + icon.getFitHeight() / 2;

            if (iconCenterX >= button.getLayoutX() && iconCenterX <= button.getLayoutX() + button.getWidth()
                    && iconCenterY >= button.getLayoutY() && iconCenterY <= button.getLayoutY() + button.getHeight()) {
                double distance = Math
                        .sqrt(Math.pow(iconCenterX - buttonCenterX, 2) + Math.pow(iconCenterY - buttonCenterY, 2));

                if (distance < closestDistance) {
                    closestIcon = icon;
                    closestDistance = distance;
                }
            }
        }
        return closestIcon;
    }
}
