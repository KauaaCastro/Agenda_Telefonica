package com.example.JavaStyle;

import javafx.scene.control.Button;

public class ManualStyle {

    // Estilização manual de botões
    public static void ButtonDateStyle(Button bnt_Days) {
        bnt_Days.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
        bnt_Days.setStyle(
                "-fx-background-color: white;" +
                        "-fx-text-fill: #333333;" +
                        "-fx-border-color: #ccc;" +
                        "-fx-border-width: 1.5;" +
                        "-fx-padding: 3 10 3 10;" +
                        "-fx-font-weight: 600;" +
                        "-fx-cursor: hand;");
        bnt_Days.setOnMouseEntered(e -> {
            bnt_Days.setStyle(
                    "-fx-background-color: #cce6fc;" +
                            "-fx-text-fill:#0a7acc;" +
                            "-fx-border-color: #ccc;" +
                            "-fx-border-width: 1.5;" +
                            "-fx-padding: 3 10 3 10;" +
                            "-fx-font-weight: 600;" +
                            "-fx-cursor: hand;");
        });
        bnt_Days.setOnMouseExited(e -> {
            bnt_Days.setStyle(
                    "-fx-background-color: white;" +
                            "-fx-text-fill: #333333;" +
                            "-fx-border-color: #ccc;" +
                            "-fx-border-width: 1.5;" +
                            "-fx-padding: 3 10 3 10;" +
                            "-fx-font-weight: 600;" +
                            "-fx-cursor: hand;");
        });
        bnt_Days.setOnMousePressed(e -> {
            bnt_Days.setScaleX(0.95);
            bnt_Days.setScaleY(0.95);
        });

        bnt_Days.setOnMouseReleased(e -> {
            bnt_Days.setScaleX(1);
            bnt_Days.setScaleY(1);
        });
    }
}
