package com.example.pokedex2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class PokedexDriver extends Application {

    public static DexController dexController;


    @Override
    public void start(Stage primaryStage) throws Exception {

        Dex.getInstance().setCurrPokemon(Dex.getInstance().getDex().get(0));
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/Dex.fxml"));
        Parent root = loader.load();

        dexController = loader.getController();

        primaryStage.setTitle("Pokedex");
        primaryStage.getIcons().add(new Image(String.valueOf(getClass().getResource("/Images/Symbols/Pokeball.png"))));
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
