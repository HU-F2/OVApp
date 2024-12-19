package com.mobiliteitsfabriek.ovapp.ui.pages;

import com.mobiliteitsfabriek.ovapp.service.FavoriteService;
import com.mobiliteitsfabriek.ovapp.model.Favorite;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class FavoritePage extends VBox {

    public FavoritePage() {
        Label title = new Label("Your Favorite Routes");
        this.getChildren().add(title);

        ListView<String> favoriteListView = new ListView<>();

        List<Favorite> favorites = FavoriteService.loadFavorites();

        for (Favorite favorite : favorites) {
            String favoriteText = "From " + favorite.getStartStation() + " to " + favorite.getEndStation();
            favoriteListView.getItems().add(favoriteText);
        }

        this.getChildren().add(favoriteListView);

        Button backBtn = new Button("Back");
        backBtn.setOnAction(event -> {
            System.out.println("Navigating back to Home Page");

            Scene homePageScene = HomePage.getScene(); 

            Stage primaryStage = (Stage) this.getScene().getWindow();
            primaryStage.setScene(homePageScene);
        });

        this.getChildren().add(backBtn);
    }
}
