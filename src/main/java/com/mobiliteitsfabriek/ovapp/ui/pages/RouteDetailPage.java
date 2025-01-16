package com.mobiliteitsfabriek.ovapp.ui.pages;

import java.util.ArrayList;

import com.mobiliteitsfabriek.ovapp.config.GlobalConfig;
import com.mobiliteitsfabriek.ovapp.exceptions.ExistingFavoriteException;
import com.mobiliteitsfabriek.ovapp.exceptions.InvalidRouteException;
import com.mobiliteitsfabriek.ovapp.exceptions.MatchingStationsException;
import com.mobiliteitsfabriek.ovapp.exceptions.NotLoggedInFavoritePermissionException;
import com.mobiliteitsfabriek.ovapp.general.UtilityFunctions;
import com.mobiliteitsfabriek.ovapp.model.FavoritesManagement;
import com.mobiliteitsfabriek.ovapp.model.Route;
import com.mobiliteitsfabriek.ovapp.model.RouteTransfers;
import com.mobiliteitsfabriek.ovapp.model.UserManagement;
import com.mobiliteitsfabriek.ovapp.translation.TranslationHelper;
import com.mobiliteitsfabriek.ovapp.ui.OVAppUI;
import com.mobiliteitsfabriek.ovapp.ui.components.MapViewer;
import com.mobiliteitsfabriek.ovapp.ui.controllers.RouteDetailController;

import javafx.geometry.Pos;
import javafx.scene.AccessibleRole;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class RouteDetailPage {

    private final RouteDetailController controller;
    private final ArrayList<Route> previousRoutes;

    public RouteDetailPage(Route route, ArrayList<Route> routes) {
        this.controller = new RouteDetailController(route);
        this.previousRoutes = routes;
    }

    public Scene createRouteDetailScene() {
        Route route = controller.getRoute();

        // Header
        HBox header = createHeader(route);

        // Javafx list group for routeTransfers
        VBox listGroup = createListGroup(route);

        // Back button
        Button backButton = new Button(TranslationHelper.get("app.common.back"));
        backButton.setOnAction((actionEvent) -> {
            controller.handleBackButton(actionEvent, previousRoutes);
        });
        backButton.getStyleClass().add("goTo-login-page-button");

        VBox layoutData = new VBox(0, header, listGroup);

        // Open Map button
        Button openMapButton = new Button(TranslationHelper.get("detail.mapviewer"));
        openMapButton.setOnAction(actionEvent -> {
            MapViewer.getInstance().showMap(route);
        });
        openMapButton.getStyleClass().add("goTo-login-page-button");

        // Layout
        VBox layout = new VBox(10, layoutData, new HBox(10, backButton, openMapButton));
        layout.getStyleClass().add("detailedRoute-container");

        Scene scene = new Scene(layout, GlobalConfig.SCENE_WIDTH, GlobalConfig.SCENE_HEIGHT);
        scene.getStylesheets().add(RoutesPage.class.getResource("/styles/styles.css").toExternalForm());
        return scene;
    }

    private HBox createHeader(Route route) {
        Label title = new Label(TranslationHelper.get("detail.title"));
        title.getStyleClass().add("title");
        title.setFocusTraversable(GlobalConfig.isUsingScreenreader);

        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button favoritesBtn = createFavoriteButton(route.getStartLocation(), route.getEndLocation(), route.getCtxRecon());

        HBox container = new HBox(title, spacer);
        if (favoritesBtn != null) {
            container.getChildren().add(favoritesBtn);
        }
        container.getStyleClass().add("detail-container");
        container.setAlignment(Pos.BOTTOM_LEFT);

        Label travelInfo = new Label(TranslationHelper.get("detail.travelInfo", route.getStartLocation(), route.getEndLocation()));
        travelInfo.setAccessibleText(TranslationHelper.get("detail.travelInfo.accessibleText", route.getStartLocation(), route.getEndLocation()));

        travelInfo.getStyleClass().add("info");
        travelInfo.setFocusTraversable(GlobalConfig.isUsingScreenreader);

        VBox headerContent = new VBox(container, travelInfo);
        if (!UtilityFunctions.checkEmpty(route.getCost())) {
            Label priceLabel = new Label(TranslationHelper.get("detail.price", UtilityFunctions.formatValueInCentsAsCurrency(route.getCost().getFirstClassPriceInCents()), UtilityFunctions.formatValueInCentsAsCurrency(route.getCost().getSecondClassPriceInCents())));
            priceLabel.getStyleClass().add("info");
            headerContent.getChildren().add(priceLabel);
        }

        headerContent.setSpacing(5);

        HBox header = new HBox(headerContent);
        header.getStyleClass().add("header");

        // Note: I couldn't get it to take up the whole space without this
        header.widthProperty().addListener((observable, oldValue, newValue) -> {
            container.setPrefWidth((Double) newValue);
        });
        return header;
    }

    private VBox createListGroup(Route route) {
        VBox listGroup = new VBox();

        for (RouteTransfers routeTransfer : route.getRouteTransfers()) {
            HBox listItem = new HBox();
            listItem.setFocusTraversable(GlobalConfig.isUsingScreenreader);
            listItem.setAccessibleRole(AccessibleRole.TEXT);
            listItem.setAccessibleText(TranslationHelper.get("detail.transfer", routeTransfer.getTransportType()));

            VBox stopDetails = new VBox();
            Label location = new Label(TranslationHelper.get("detail.transfer.location", routeTransfer.getDepartureLocationAndDetails(), routeTransfer.getArrivalLocationAndDetails()));
            location.setFocusTraversable(GlobalConfig.isUsingScreenreader);
            location.setAccessibleText(TranslationHelper.get("detail.transfer.location.accessibleText", routeTransfer.getDepartureLocation(), routeTransfer.getDepartureLocationDetails(), routeTransfer.getArrivalLocation(), routeTransfer.getArrivalLocationDetails()));
            Label time = new Label(TranslationHelper.get("detail.transfer.time", UtilityFunctions.formatTime(routeTransfer.getPlannedDepartureDateTime()), UtilityFunctions.formatTime(routeTransfer.getPlannedArrivalDateTime()), routeTransfer.getPlannedDurationMinutes()));
            time.setFocusTraversable(GlobalConfig.isUsingScreenreader);
            time.setAccessibleText(TranslationHelper.get("detail.transfer.time.accessibleText", UtilityFunctions.formatTime(routeTransfer.getPlannedDepartureDateTime()), UtilityFunctions.formatTime(routeTransfer.getPlannedArrivalDateTime()), routeTransfer.getPlannedDurationMinutes()));
            Label details = new Label(TranslationHelper.get("detail.transfer.details", routeTransfer.getCombinedTransport()));
            details.setFocusTraversable(GlobalConfig.isUsingScreenreader);
            details.setAccessibleText(TranslationHelper.get("detail.transfer.details.accessibleText", routeTransfer.getTransportType()));

            stopDetails.getChildren().addAll(location, time, details);
            stopDetails.setSpacing(5);

            listItem.getChildren().add(stopDetails);
            listItem.getStyleClass().add("routeTransfer");
            listGroup.getChildren().add(listItem);
        }

        return listGroup;
    }

    private Button createFavoriteButton(String startValue, String endValue, String routeId) {
        if (!UserManagement.userLoggedIn()) {
            return null;
        }

        boolean favoriteExists;
        try {
            favoriteExists = FavoritesManagement.favoriteExists(routeId);
        } catch (NotLoggedInFavoritePermissionException e) {
            return null;
        }

        if (favoriteExists) {
            Button removeFavoriteBtn = new Button(TranslationHelper.get("favorites.delete"));
            removeFavoriteBtn.getStyleClass().add("goTo-login-page-button");
            removeFavoriteBtn.setOnAction((e) -> {
                try {
                    FavoritesManagement.deleteFavorite(routeId);
                } catch (NotLoggedInFavoritePermissionException e1) {
                    e1.printStackTrace();
                }
                OVAppUI.switchToScene(createRouteDetailScene());
            });
            return removeFavoriteBtn;
        }

        Button addToFavoritesBtn = new Button(TranslationHelper.get("favorites.add"));
        addToFavoritesBtn.getStyleClass().add("goTo-login-page-button");
        addToFavoritesBtn.setOnAction(event -> onAddFavorite(startValue, endValue, routeId));
        return addToFavoritesBtn;
    }

    public void onAddFavorite(String startValue, String endValue, String routeId) {
        try {
            FavoritesManagement.addFavorite(startValue, endValue, routeId);
            OVAppUI.switchToScene(createRouteDetailScene());
        } catch (InvalidRouteException | MatchingStationsException | ExistingFavoriteException | NotLoggedInFavoritePermissionException e) {
            if (GlobalConfig.DEBUG_FAVORITE) {
                System.out.println(e.getMessage());
            }
        }
    }

}
