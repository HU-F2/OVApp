package com.mobiliteitsfabriek.ovapp.ui.pages;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

import com.mobiliteitsfabriek.ovapp.model.Route;
import com.mobiliteitsfabriek.ovapp.ui.OVAppUI;

import javafx.geometry.Pos;
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

        HBox dateContainer = new HBox();
        Text dateText = new Text(firstRoute.getStartDateTime().format(dateFormatter));
        dateText.setStyle("-fx-font-size: 36px; -fx-font-weight:bold;");
        dateContainer.getChildren().add(dateText);
        dateContainer.setAlignment(Pos.TOP_CENTER);
        dateContainer.setStyle("-fx-padding:4px 0px 16px 0px");
        
 
        root.getChildren().addAll(dateContainer);

        for (Route route : routes) {
            VBox routeContainer = new VBox();

            // Time
            HBox timeContainer = new HBox();
            Text startTimeText = new Text(route.getStartDateTime().format(timeFormatter));
            Text arrowSign = new Text(" --> ");
            Text endTimeText = new Text(route.getEndDateTime().format(timeFormatter));
            timeContainer.getChildren().addAll(startTimeText, arrowSign, endTimeText);
            timeContainer.setStyle("-fx-font-size: 24px; -fx-padding:0px 0px 0px 8px;");


            // Duration
            HBox infoContainer = new HBox();
            Text durationText = new Text(route.getDuration() + " minuten | ");
            Text transfersText = new Text(route.getTransfers() + " overstappen | ");
            Text platformNumberText = new Text("platform " + route.getPlatformNumber());
            infoContainer.getChildren().addAll(durationText,transfersText,platformNumberText);
            infoContainer.setStyle("-fx-font-size: 18px; -fx-padding:0px 0px 0px 8px;");

            routeContainer.getChildren().addAll(timeContainer,infoContainer);
            routeContainer.setStyle("-fx-padding: 8px; -fx-border-color: black; -fx-border-bottom-width:1px; -fx-border-style:solid;");

            routeContainer.setOnMouseClicked((e)->{
                handleGoToDetailedRoute(route);
            });
            

            root.getChildren().addAll(routeContainer);
        }

        Scene scene = new Scene(root, 1200, 800);
        
        return scene;
    }
//TODO: implementeer de detailedRoute pagina
    public static void handleGoToDetailedRoute(Route route){
        System.out.println(route);
        // throw new UnsupportedOperationException();
    }
}
