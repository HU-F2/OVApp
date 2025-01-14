package com.mobiliteitsfabriek.ovapp.ui.pages;

import java.util.ArrayList;

import com.mobiliteitsfabriek.ovapp.config.GlobalConfig;
import com.mobiliteitsfabriek.ovapp.model.Favorite;
import com.mobiliteitsfabriek.ovapp.model.FavoritesManagement;
import com.mobiliteitsfabriek.ovapp.model.Route;
import com.mobiliteitsfabriek.ovapp.service.FavoriteService;
import com.mobiliteitsfabriek.ovapp.service.RouteService;
import com.mobiliteitsfabriek.ovapp.translation.TranslationHelper;
import com.mobiliteitsfabriek.ovapp.ui.OVAppUI;
import com.mobiliteitsfabriek.ovapp.ui.components.RouteElement;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class FavoritePage {

    public static Scene getScene() {
        VBox root = new VBox();
        Label title = new Label(TranslationHelper.get("favorites.header"));
        title.getStyleClass().add("favorite-page-header");

        ListView<Favorite> favoriteListView = new ListView<>();
        ArrayList<Favorite> favorites = FavoriteService.loadFavoriteByUser();
        favoriteListView.getItems().addAll(favorites);

        Button backBtn = new Button(TranslationHelper.get("app.common.back"));
        backBtn.getStyleClass().add("submit-btn");
        backBtn.setOnAction(event -> {
            Scene homePageScene = HomePage.getScene();
            OVAppUI.switchToScene(homePageScene);
        });

        Button showRouteBtn = new Button(TranslationHelper.get("favorites.showRoute"));
        showRouteBtn.getStyleClass().add("submit-btn");
        showRouteBtn.setDisable(true);

        showRouteBtn.setOnAction((e)->{
            Route route = RouteService.getRoute(favoriteListView.getSelectionModel().getSelectedItem().getRouteId());
            RouteElement.handleGoToDetailedRoute(route, null);
        });
        
        Button deleteRouteBtn = new Button(TranslationHelper.get("favorites.delete"));
        deleteRouteBtn.getStyleClass().add("submit-btn");
        deleteRouteBtn.setDisable(true);

        deleteRouteBtn.setOnAction((e)->{
            Favorite favorite = favoriteListView.getSelectionModel().getSelectedItem();
            favoriteListView.getItems().remove(favorite);
            FavoritesManagement.deleteFavorite(favorite.getRouteId());
        });

        favoriteListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showRouteBtn.setDisable(false);
                deleteRouteBtn.setDisable(false);
            }
        });
        
        HBox actionContainer = new HBox(showRouteBtn, deleteRouteBtn, backBtn);
        actionContainer.setSpacing(10);
        root.getChildren().addAll(title, favoriteListView, actionContainer);
        Scene scene = new Scene(root, GlobalConfig.SCENE_WIDTH, GlobalConfig.SCENE_HEIGHT);
        scene.getStylesheets().add(HomePage.class.getResource("/styles/styles.css").toExternalForm());
        return scene;
    }
}
