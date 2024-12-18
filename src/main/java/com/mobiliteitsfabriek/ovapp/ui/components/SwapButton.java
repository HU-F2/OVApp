package com.mobiliteitsfabriek.ovapp.ui.components;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Rotate;

public class SwapButton extends Button {

    private final ImageView imageView;

    public SwapButton(Runnable onClickHandler) {
        Image swapIcon = new Image(SwapButton.class.getResource("/Swap_icoon.png").toExternalForm());
        this.imageView = new ImageView(swapIcon);
        this.imageView.setFitWidth(32);
        this.imageView.setFitHeight(32);

        this.setGraphic(imageView);
        
        this.getStyleClass().add("swap-btn");
        
        this.setMinWidth(imageView.getFitWidth());
        this.setMinHeight(imageView.getFitHeight());
        this.setMaxWidth(imageView.getFitWidth());
        this.setMaxHeight(imageView.getFitHeight());

        this.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> onClickHandler.run());
    }
}
