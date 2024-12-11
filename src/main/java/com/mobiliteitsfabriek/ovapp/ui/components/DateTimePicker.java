package com.mobiliteitsfabriek.ovapp.ui.components;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class DateTimePicker extends VBox{
    private final DatePicker datePicker;
    private final Spinner<String> timeSpinner;

    public DateTimePicker(boolean isHorizontal){
        HBox horizontalContainer = new HBox();
        datePicker = new DatePicker();
        datePicker.setPromptText("Kies een datum.");
        datePicker.setValue(LocalDate.now());

        datePicker.setPrefWidth(100);

        timeSpinner = new Spinner<>();
        timeSpinner.setEditable(true);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        List<String> timeOptions = new ArrayList<>();
        for (int hour = 0; hour < 24; hour++) {
            for (int minute = 0; minute < 60; minute++) {
                timeOptions.add(String.format("%02d:%02d", hour, minute));
            }
        }

        timeSpinner.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(
                FXCollections.observableArrayList(timeOptions)
        ));

        LocalTime currentTime = LocalTime.now();
        String formattedCurrentTime = currentTime.format(timeFormatter);
        timeSpinner.getValueFactory().setValue(formattedCurrentTime);
        timeSpinner.setPrefWidth(100);
        this.setAlignment(Pos.CENTER);
        this.setSpacing(10);
        if(isHorizontal){
            horizontalContainer.getChildren().addAll(datePicker, timeSpinner);
            this.getChildren().add(horizontalContainer);
        }else{
            this.getChildren().addAll(datePicker, timeSpinner);
        }
    }

    public DateTimePicker(){
        this(false);
    }

    public String getDateTimeRFC3339Format() {
        LocalDate selectedDate = datePicker.getValue();

        // Get selected time
        String selectedTime = timeSpinner.getValue();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime selectedLocalTime = LocalTime.parse(selectedTime, timeFormatter);

        // Combine date and time into LocalDateTime
        LocalDateTime localDateTime = LocalDateTime.of(selectedDate, selectedLocalTime);

        // Convert to Amsterdam timezone
        ZonedDateTime amsterdamDateTime = localDateTime.atZone(ZoneId.of("Europe/Amsterdam"));

        // Format to RFC3339
        DateTimeFormatter rfc3339Formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
        return amsterdamDateTime.format(rfc3339Formatter);
    }

    public DatePicker getDatePicker() {
        return datePicker;
    }
    
    public Spinner<String> getTimeSpinner() {
        return timeSpinner;
    }

}
