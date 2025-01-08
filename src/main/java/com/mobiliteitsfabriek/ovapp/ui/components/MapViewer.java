package com.mobiliteitsfabriek.ovapp.ui.components;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.mobiliteitsfabriek.ovapp.model.Route;
import com.mobiliteitsfabriek.ovapp.model.RouteTransfers;
import com.mobiliteitsfabriek.ovapp.model.Station;
import com.mobiliteitsfabriek.ovapp.service.StationService;
import com.mobiliteitsfabriek.ovapp.translation.TranslationHelper;
import com.mobiliteitsfabriek.ovapp.ui.pages.RouteDetailPage;

import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class MapViewer {
    private static final double minLat = 50.5800; // Southernmost latitude
    private static final double maxLat = 53.67; // Northernmost latitude
    private static final double minLon = 3.11;  // Westernmost longitude
    private static final double maxLon = 7.6024;  // Easternmost longitude

    private final Route route;

    public MapViewer(Route route){
        this.route = route;
    }

    public void showMap() {
        Stage mapStage = new Stage();
        mapStage.setTitle("Map Viewer");
        VBox mapView = createNetherlandsMapWithStations();
        Scene mapScene = new Scene(mapView, 800, 600);
        mapStage.setScene(mapScene);
        mapStage.show();
    }

    private VBox createNetherlandsMapWithStations() {
        Image netherlandsMap = new Image(MapViewer.class.getResource("/NL_removed.jpg").toExternalForm());
        ImageView mapView = new ImageView(netherlandsMap);
    
        double mapWidth = 2775;
        double mapHeight = 3128;
    
        double initialWidth = 800;
        double initialHeight = 600; 
        mapView.setFitWidth(initialWidth);
        mapView.setFitHeight(initialHeight);
    
        Pane overlayPane = new Pane();
    
        ArrayList<Station> routeStations = getStationsFromRoute();
        Set<Station> processedStations = new HashSet<>();

        int stationNumber = 1;

        for (Station station : routeStations) {
            if (isStationInNetherlands(station) && !processedStations.contains(station)) {
                processedStations.add(station);
                Circle marker = new Circle(5, Color.RED);
                marker.setStroke(Color.BLACK);
                
                Label stationLabel = new Label(String.valueOf(stationNumber));
                stationLabel.setTextFill(Color.BLACK);

                Label stationNameLabel = new Label(station.getName());

                double normalizedX = getNormalizedLongitude(station.getLongitude());
                double normalizedY = getNormalizedLatitude(station.getLatitude());
    
                double originalX = normalizedX * mapWidth;
                double originalY = normalizedY * mapHeight;

                stationLabel.setStyle("-fx-font-weight: bold;");
                stationNameLabel.setStyle("-fx-font-weight: bold;");

                marker.layoutXProperty().bind(mapView.fitWidthProperty().multiply(originalX / mapWidth).add(5));
                marker.layoutYProperty().bind(mapView.fitHeightProperty().multiply(originalY / mapHeight).add(5));
                stationLabel.layoutXProperty().bind(marker.layoutXProperty().subtract(15));
                stationLabel.layoutYProperty().bind(marker.layoutYProperty().subtract(5));
                stationNameLabel.layoutXProperty().bind(marker.layoutXProperty().add(5));
                stationNameLabel.layoutYProperty().bind(marker.layoutYProperty().add(0));

                overlayPane.getChildren().addAll(marker, stationLabel, stationNameLabel);
                stationNumber++;
            }
        }
    
        StackPane mapContainer = new StackPane(mapView, overlayPane);
    
        return new VBox(mapContainer);
    }
    

    private ArrayList<Station> getStationsFromRoute() {
        ArrayList<Station> stations = new ArrayList<>();
        for (RouteTransfers transfer : route.getRouteTransfers()) {
            Station departureStation = StationService.getStation(transfer.getDepartureLocation());
            Station arrivalStation = StationService.getStation(transfer.getArrivalLocation());

            if (departureStation != null) {
                stations.add(departureStation);
            }
            if (arrivalStation != null) {
                stations.add(arrivalStation);
            }
        }
        return stations;
    }
    
    private boolean isStationInNetherlands(Station station) {
        return "NL".equalsIgnoreCase(station.getCountry());
    }
    
    private double getNormalizedLongitude(double lon) {
        return (lon - minLon) / (maxLon - minLon);
    }
    
    private double getNormalizedLatitude(double lat) {
        return (maxLat - lat) / (maxLat - minLat);
    }
}
