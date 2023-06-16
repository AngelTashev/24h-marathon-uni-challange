package util;

import javafx.scene.control.Alert;

public class AlertUtil {

    public static Alert createAlert(String title, String message, Alert.AlertType alertType) {
        Alert a = new Alert(alertType);
        a.setTitle(title);
        a.setContentText(message);
        a.show();
        return a;
    }
}
