package com.mobiliteitsfabriek.ovapp.general;

import java.text.MessageFormat;

public class UtilityFunctions {

    public static String fxmlUrlFormatter(String fxml) {
        return MessageFormat.format("/fxml/{0}.fxml", fxml);
    }
}
