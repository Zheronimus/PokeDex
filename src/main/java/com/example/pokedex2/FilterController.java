package com.example.pokedex2;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FilterController {

    @FXML private VBox body;
    @FXML private ComboBox<String> filterSelector;
    @FXML private Button setFilterButton;
    private ToggleGroup buttonGroup;
    private Node container;


    @FXML public void handleFilterSelection() {

        String selection = filterSelector.getSelectionModel().getSelectedItem();

        switch (selection) {
            case "Filter By Type" -> addFilterByTypeSect();
            case "Filter By Generation" -> addFilterByGenerationSect();
            case "Filter By Forms" -> addFilterByFormsSect();
        }

        VBox.setMargin(setFilterButton, new Insets(25, 0, 0, 0));

        body.getChildren().add(setFilterButton);
    }


    private void addFilterByFormsSect() {

        final String[] ALL_FORMS = {"Mega Form", "Gigantamax Form", "Alolan Form", "Galarian Form"};

        container = new VBox(30);
        buttonGroup = new ToggleGroup();

        for (String form : ALL_FORMS) {
            RadioButton radioButton = new RadioButton(form);

            ((VBox) container).getChildren().add(radioButton);

            radioButton.setToggleGroup(buttonGroup);
        }

        ((VBox) container).setAlignment(Pos.CENTER);
        VBox.setMargin(container, new Insets(25, 0, 0, 0));

        addContainer();
    }


    private void addFilterByGenerationSect() {

        final int NUM_GENERATIONS = 8;
        container = new VBox(15);
        buttonGroup = new ToggleGroup();

        for (int i = 0; i < NUM_GENERATIONS; i++) {
            RadioButton radioButton = new RadioButton("Generation " + (i + 1));

            ((VBox) container).getChildren().add(radioButton);

            radioButton.setToggleGroup(buttonGroup);
        }

        ((VBox) container).setAlignment(Pos.CENTER);
        VBox.setMargin(container, new Insets(25, 0, 0, 0));

        addContainer();
    }


    private void addFilterByTypeSect() {

        container = new HBox(50);
        buttonGroup = new ToggleGroup();
        VBox leftContainer = new VBox(10);
        VBox rightContainer = new VBox(10);

        final String[] ALL_TYPES = {"Normal", "Fire", "Water", "Fighting", "Grass", "Bug", "Ice", "Ground", "Dark", "Ghost", "Electric",
                "Poison", "Steel", "Fairy", "Dragon", "Flying", "Rock", "Psychic"};

        for (int i = 0; i < ALL_TYPES.length; i++) {
            RadioButton radioButton = new RadioButton(ALL_TYPES[i]);

            if (i < ALL_TYPES.length / 2) {
                leftContainer.getChildren().add(radioButton);
            } else {
                rightContainer.getChildren().add(radioButton);
            }

            radioButton.setToggleGroup(buttonGroup);
        }

        ((HBox) container).getChildren().addAll(leftContainer, rightContainer);
        ((HBox) container).setAlignment(Pos.CENTER);
        VBox.setMargin(container, new Insets(25, 0, 0, 0));

        addContainer();
    }


    private void addContainer() {

        if (body.getChildren().size() > 2) {
            body.getChildren().set(2, container);
            body.getChildren().remove(setFilterButton);
        } else {
            body.getChildren().add(container);
        }
    }


    @FXML public void setFilter() {

        switch (filterSelector.getSelectionModel().getSelectedItem()) {
            case "Filter By Type" ->
                    Dex.getInstance().filterByType(((RadioButton) buttonGroup.getSelectedToggle()).getText());
            case "Filter By Generation" ->
                    Dex.getInstance().filterByGeneration(Integer.parseInt(((RadioButton) buttonGroup.getSelectedToggle()).getText().substring(((RadioButton) buttonGroup.getSelectedToggle()).getText().length() - 1)));
            case "Filter By Forms" ->
                    Dex.getInstance().filterByForms(((RadioButton) buttonGroup.getSelectedToggle()).getText());
        }

        PokedexDriver.dexController.addRemoveFilterButton();
        PokedexDriver.dexController.addFormSelector();
        PokedexDriver.dexController.setNameLabel();
        PokedexDriver.dexController.setEntryLabel();
        PokedexDriver.dexController.setSprite();
        PokedexDriver.dexController.setBitSprite();
        PokedexDriver.dexController.setTyping();
        PokedexDriver.dexController.setEntryNumLabel();
        PokedexDriver.dexController.clearSearch();

        Stage stage = (Stage) body.getScene().getWindow();
        stage.close();
    }
}
