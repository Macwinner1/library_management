package com.library.frontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LibraryManagementApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load FXML (if using Scene Builder) or use programmatic UI
            Parent root = new LibraryView();

            Scene scene = new Scene(root, 1000, 600);

            primaryStage.setTitle("Library Management System");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    System.out.print(start);
    public static void main(String[] args) {
        launch(args);
    }
}