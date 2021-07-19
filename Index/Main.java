package Index;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.image.Image;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("app.fxml"));

        Scene scene = new Scene(root, Color.LIGHTSKYBLUE);
        scene.getStylesheets().add(getClass().getResource("app.css").toExternalForm());

        Image icon = new Image("./public/images/room_booking.png");

        primaryStage.setTitle("Social Distance Room Reservation App");
        primaryStage.getIcons().add(icon);
        primaryStage.setWidth(600);
        primaryStage.setHeight(600);
        primaryStage.setResizable(false);

        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
