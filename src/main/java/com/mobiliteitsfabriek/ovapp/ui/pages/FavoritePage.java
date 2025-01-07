package com.mobiliteitsfabriek.ovapp.ui.pages;

import java.util.List;

import com.mobiliteitsfabriek.ovapp.config.GlobalConfig;
import com.mobiliteitsfabriek.ovapp.model.Favorite;
import com.mobiliteitsfabriek.ovapp.service.FavoriteService;
import com.mobiliteitsfabriek.ovapp.translation.TranslationHelper;
import com.mobiliteitsfabriek.ovapp.ui.OVAppUI;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

public class FavoritePage {

    public static Scene getScene() {
        VBox root = new VBox();
        Label title = new Label(TranslationHelper.get("favorites.header"));
        title.getStyleClass().add("favorite-page-header");

        ListView<String> favoriteListView = new ListView<>();
        List<Favorite> favorites = FavoriteService.loadFavorites();
        for (Favorite favorite : favorites) {
            favoriteListView.getItems().add(TranslationHelper.get("favorites.route",favorite.getStartStation(),favorite.getEndStation()));
        }

        Button backBtn = new Button(TranslationHelper.get("app.common.back"));
        backBtn.getStyleClass().add("submit-btn");
        backBtn.setOnAction(event -> {
            Scene homePageScene = HomePage.getScene();
            OVAppUI.switchToScene(homePageScene);
        });

        root.getChildren().addAll(title,favoriteListView,backBtn);
        Scene scene = new Scene(root, GlobalConfig.SCENE_WIDTH, GlobalConfig.SCENE_HEIGHT);
        scene.getStylesheets().add(HomePage.class.getResource("/styles/styles.css").toExternalForm());
        return scene;
    }
}
