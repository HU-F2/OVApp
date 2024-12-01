package com.mobiliteitsfabriek.ovapp.ui.pages;

import java.time.format.DateTimeFormatter;
import java.util.List;

import com.mobiliteitsfabriek.ovapp.config.GlobalConfig;
import com.mobiliteitsfabriek.ovapp.model.Route;

import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class RoutesPage {
    public static Scene getScene(List<Route> routes) {
        VBox root = new VBox();

        Route firstRoute = routes.get(0);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        Text dateText = new Text(firstRoute.getStartDateTime().format(dateFormatter));
        root.getChildren().addAll(dateText);

        for (Route route : routes) {
            HBox routeContainer = new HBox();

            // Time
            HBox timeContainer = new HBox();
            Text startTimeText = new Text(route.getStartDateTime().format(timeFormatter));
            Text arrowSign = new Text(" --> ");
            Text endTimeText = new Text(route.getEndDateTime().format(timeFormatter));
            timeContainer.getChildren().addAll(startTimeText, arrowSign, endTimeText);

            // Duration
            Text durationText = new Text(route.getDuration() + " minuten | ");
            Text transfersText = new Text(route.getTransfers() + " overstappen | ");
            Text platformNumberText = new Text(route.getPlatformNumber());

            routeContainer.getChildren().addAll(durationText, transfersText, platformNumberText);

            root.getChildren().addAll(timeContainer, routeContainer);
        }

        Scene scene = new Scene(root, GlobalConfig.SCENE_WIDTH, GlobalConfig.SCENE_HEIGHT);
        return scene;
    }
}
