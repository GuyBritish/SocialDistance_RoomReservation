package Index;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Optional;

public class Controller {

    private static ArrayList<Person> guests = new ArrayList<Person>();
    private static ArrayList<Room> rooms = new ArrayList<Room>();

    @FXML
    private TextArea roomList;

    @FXML
    private TextArea guestList;

    @FXML
    private TextField guestNum;

    @FXML
    private TextField roomNum;

    private void displayRooms() {
        String str = "";
        for (int i = 0; i<=rooms.size() - 1; ++i) {
            str += Integer.toString(i + 1) + ") " + rooms.get(i).getName();
            str += " | Capacity: " + Integer.toString(rooms.get(i).getOccupancy()) + "/";
            str += Integer.toString(rooms.get(i).getCapacity()) + "\n";
        }
        roomList.setText(str);
    }

    private void displayGuests() {
        String str = "";
        for (int i = 0; i<=guests.size() - 1; ++i) {
            Person guest = guests.get(i);
            str += Integer.toString(i + 1) + ") " + guest.getName();
            if (guest.isWaiting()) {
                str += " - waiting for a room";
            } else {
                str += " - assigned a room";
            }
            str += "\n";
        }
        guestList.setText(str);
    }

    @FXML
    private void addRoom(ActionEvent event) {
        // Create the custom dialog.
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Create New Room");
        dialog.setHeaderText("Enter Room Information");

        // Set the icon (must be included in the project).
        dialog.setGraphic(new ImageView(this.getClass().getResource("../public/images/newRoom" +
         ".png").toString()));

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField roomName = new TextField();
        roomName.setPromptText("Room name");
        TextField capacity = new TextField();
        capacity.setPromptText("Capacity");

        grid.add(new Label("Room name:"), 0, 0);
        grid.add(roomName, 1, 0);
        grid.add(new Label("Capacity:"), 0, 1);
        grid.add(capacity, 1, 1);

        // Enable/Disable login button depending on whether a username was entered.
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        // Do some validation (using the Java 8 lambda syntax).
        roomName.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        // Request focus on the username field by default.
        Platform.runLater(() -> roomName.requestFocus());

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(roomName.getText(), capacity.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(newRoom -> {
            String name = newRoom.getKey();
            int cap = Integer.parseInt(newRoom.getValue());
            System.out.println("Room name=" + newRoom.getKey() + ", Capacity=" + newRoom.getValue());
            rooms.add(new Room(name, cap));
            displayRooms();
        });
    }

    @FXML
    private void addPerson(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog("Anonymous");
        dialog.setTitle("Add A Person");
        dialog.setHeaderText(" Enter New Person ");
        dialog.setContentText("Name:");

        // Set the icon (must be included in the project).
        dialog.setGraphic(new ImageView(this.getClass().getResource("../public/images/info" +
                ".png").toString()));

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            System.out.println("Name: " + result.get());
        }

        // The Java 8 way to get the response value (with lambda expression).
        result.ifPresent(name -> {
            System.out.println("Person name: " + name);
            guests.add(new Person(name));
            displayGuests();
        });
    }

    @FXML
    private void checkIn(ActionEvent event) {
        int guest = Integer.parseInt(guestNum.getText()) - 1;
        int room = Integer.parseInt(roomNum.getText()) - 1;

        rooms.get(room).checkIn(guests.get(guest));

        displayRooms();
        displayGuests();
    }

    @FXML
    private void checkOut(ActionEvent event) {
        int guest = Integer.parseInt(guestNum.getText()) - 1;
        int room = Integer.parseInt(roomNum.getText()) - 1;

        rooms.get(room).checkOut(guests.get(guest));

        displayRooms();
        displayGuests();
    }

    public void initialize() {
        // initialization here, if needed...
    }
}
