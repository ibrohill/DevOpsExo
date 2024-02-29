module sn.dev.parrainageapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires static lombok;
    requires TrayNotification;


    opens sn.dev.parrainageapp to javafx.fxml;
    exports sn.dev.parrainageapp;
     //exposition des  controleurs
    exports sn.dev.parrainageapp.controllers;
    opens sn.dev.parrainageapp.controllers to javafx.fxml;

    //exposition des entities
    exports sn.dev.parrainageapp.entities;
    opens sn.dev.parrainageapp.entities to javafx.fxml;
}

