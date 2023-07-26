module cz.gyarab.dino {
    requires javafx.controls;
    requires javafx.fxml;


    opens cz.gyarab.dino to javafx.fxml;
    exports cz.gyarab.dino;
}