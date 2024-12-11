package com.mobiliteitsfabriek.ovapp.ui.components;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.mobiliteitsfabriek.ovapp.general.UtilityFunctions;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class DateTimePicker extends VBox {
    private final DatePicker datePicker;
    private final Spinner<String> timeSpinner;
    private static ArrayList<String> timeOptions = new ArrayList<>();

    public DateTimePicker(boolean isHorizontal) {
        HBox horizontalContainer = new HBox();
        datePicker = new DatePicker();
        datePicker.setPromptText("Kies een datum.");
        datePicker.setValue(LocalDate.now());

        datePicker.setPrefWidth(100);

        timeSpinner = new Spinner<>();
        timeSpinner.setEditable(true);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        if (timeOptions.isEmpty()) {
            for (int hour = 0; hour < 24; hour++) {
                for (int minute = 0; minute < 60; minute++) {
                    timeOptions.add(String.format("%02d:%02d", hour, minute));
                }
            }
        }

        timeSpinner.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(
                FXCollections.observableArrayList(timeOptions)));

        LocalTime currentTime = LocalTime.now();
        String formattedCurrentTime = currentTime.format(timeFormatter);
        timeSpinner.getValueFactory().setValue(formattedCurrentTime);
        timeSpinner.setPrefWidth(100);
        this.setAlignment(Pos.CENTER);
        this.setSpacing(0);
        if (isHorizontal) {
            horizontalContainer.setAlignment(Pos.CENTER);
            horizontalContainer.getChildren().addAll(datePicker, timeSpinner);
            this.getChildren().add(horizontalContainer);
        } else {
            this.getChildren().addAll(datePicker, timeSpinner);
        }
    }

    public DateTimePicker() {
        this(false);
    }

    public String getDateTimeRFC3339Format() {
        LocalDate selectedDate = datePicker.getValue();
        String selectedTime = timeSpinner.getValue();

        return UtilityFunctions.getDateTimeRFC3339Format(selectedDate, selectedTime);
    }

    public DatePicker getDatePicker() {
        return datePicker;
    }

    public Spinner<String> getTimeSpinner() {
        return timeSpinner;
    }

}
