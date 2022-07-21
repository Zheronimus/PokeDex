package com.example.pokedex2;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;

public class DexController {

    @FXML private Label nameLabel;
    @FXML private Label entryLabel;
    @FXML private Label prevEntryLabel;
    @FXML private Label nextEntryLabel;
    @FXML private ImageView sprite;
    @FXML private ImageView typeOneImg;
    @FXML private ImageView typeTwoImg;
    @FXML private ScrollPane body;
    @FXML private VBox bodyContent;
    @FXML private VBox topContent;
    @FXML private HBox typingContent;
    @FXML private HBox infoLabels;
    @FXML private Button prevButton;
    @FXML private Button nextButton;
    @FXML private Button filterButton;
    @FXML private TextField searchField;
    private ComboBox<String> formSelector;
    private VBox bottomContent;


    public void setNameLabel() {
        nameLabel.setText(Dex.getInstance().getCurrPokemon().getName());
    }


    public void setEntryLabel() { entryLabel.setText(String.format("#%03d", Dex.getInstance().getCurrPokemon().getEntryNum())); }


    public void setSprite() { sprite.setImage(new Sprite().getSprite()); }


    public void clearSearch() {
        searchField.clear();
    }


    @FXML private void handleNavigation(ActionEvent event) {

        Button sourceButton = (Button) event.getSource();
        ArrayList<Pokemon> dex = Dex.getInstance().getDex();

        Dex.getInstance().getCurrPokemon().setName(Dex.getInstance().getCurrPokemon().getName().replaceFirst("Gigantamax ", ""));

        int index = BinarySearch.searchByEntry(dex, Dex.getInstance().getCurrPokemon().getEntryNum());

        if ((sourceButton).getId().equals("prevButton")) {
            if (Dex.getInstance().getCurrPokemon().getEntryNum() > dex.get(0).getEntryNum()) {
                index--;
            } else {
                index = dex.size() - 1;
            }
        }

        else if ((sourceButton).getId().equals("nextButton")) {
            if (Dex.getInstance().getCurrPokemon().getEntryNum() < dex.get(dex.size() - 1).getEntryNum()) {
                index++;
            } else {
                index = 0;
            }
        }

        Dex.getInstance().setCurrPokemon(dex.get(index));

        setNameLabel();
        setEntryLabel();
        setSprite();
        setBitSprite();
        setTyping();
        setEntryNumLabel();
        clearSearch();
        addFormSelector();
        hideInfo();
    }


    @FXML private void handleFilter() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FilterWindow.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setTitle("Filter Window");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    @FXML private void handleSearch(ActionEvent event) {

        String input = searchField.getText();
        Object sourceButton = event.getSource();
        ArrayList<Pokemon> nationalDex = Dex.getInstance().getDex();

        try {
            if (!input.isEmpty() && sourceButton instanceof Button) {
                if (BinarySearch.searchByName(nationalDex, input) != null || BinarySearch.searchByEntry(nationalDex, Integer.parseInt(input)) != -1) {
                    if (isNumeric(input)) {
                        Dex.getInstance().setCurrPokemon(nationalDex.get(BinarySearch.searchByEntry(nationalDex, Integer.parseInt(input))));
                    } else if (!isNumeric(input)) {
                        Dex.getInstance().setCurrPokemon(BinarySearch.searchByName(nationalDex, input));
                    }
                }

                setNameLabel();
                setEntryLabel();
                setSprite();
                setBitSprite();
                setTyping();
                setEntryNumLabel();
                clearSearch();
                addFormSelector();
                hideInfo();
            }
        } catch (NumberFormatException ex) {
            searchField.clear();
        }
    }


    @FXML private void showStats() {

        bottomContent = new VBox();
        Label hideInfoLabel;
        VBox stats = new Stats().getStats();

        bottomContent.setMinHeight(500);
        hideInfoLabel = new Label("\u2014" + " Hide Stats " + "\u2014");

        VBox.setMargin(bottomContent, (new Insets(100, 0, 0, 0)));
        VBox.setMargin(infoLabels, new Insets(65, 0, 0, 0));

        hideInfoLabel.setOnMouseClicked(event -> hideInfo());
        hideInfoLabel.setStyle("-fx-font-size: 15px; -fx-font-family: Gill Sans; -fx-cursor: hand;");

        infoLabels.getChildren().clear();
        infoLabels.getChildren().add(hideInfoLabel);

        scrollAnimation();

        bottomContent.getChildren().add(stats);
        bottomContent.getChildren().add(infoLabels);
        bodyContent.getChildren().add(bottomContent);

        body.setPannable(true);
        body.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        body.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }


    @FXML private void showEffectiveness() {

        bottomContent = new VBox();
        Label hideInfoLabel;
        VBox effectiveness = new TypeEffectiveness().getTypeEffectiveness();

        bottomContent.setMinHeight(500);
        hideInfoLabel = new Label("\u2014" + " Hide Effectiveness " + "\u2014");

        VBox.setMargin(bottomContent, (new Insets(100, 0, 0, 0)));
        VBox.setMargin(infoLabels, new Insets(65, 0, 0, 0));

        hideInfoLabel.setOnMouseClicked(event -> hideInfo());
        hideInfoLabel.setStyle("-fx-font-size: 15px; -fx-font-family: Gill Sans; -fx-cursor: hand;");

        infoLabels.getChildren().clear();
        infoLabels.getChildren().add(hideInfoLabel);

        scrollAnimation();

        bottomContent.getChildren().add(effectiveness);
        bottomContent.getChildren().add(infoLabels);
        bodyContent.getChildren().add(bottomContent);

        body.setPannable(true);
        body.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        body.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }


    public void addFormSelector() {

        if (topContent.getChildren().contains(formSelector)) {
            topContent.getChildren().remove(formSelector);
            VBox.setMargin(infoLabels, new Insets(65, 0, 0, 0));
        }

        if (Dex.getInstance().getCurrPokemon().getForms().size() > 1) {
            formSelector = new ComboBox<>();

            formSelector.getItems().addAll(Dex.getInstance().getCurrPokemon().getForms());
            formSelector.setPromptText("   Select Pok" + "\u00E9" + "mon Form");
            formSelector.setCellFactory(new FormCellFactory());
            formSelector.setButtonCell(new FormCell());
            formSelector.setStyle("-fx-cursor: hand;");
            formSelector.setOnAction(new ChangeFormHandler());

            topContent.getChildren().add(5, formSelector);

            VBox.setMargin(formSelector, new Insets(15, 0, 0, 0));
            VBox.setMargin(infoLabels, new Insets(25, 0, 0, 0));
        }
    }


    public void hideInfo() {

        if (bottomContent != null) {
            Label showStatsLabel = new Label("\u2014" + " Show Stats " + "\u2014");
            Label showEffectivenessLabel = new Label("\u2014" + " Show Effectiveness" + "\u2014");

            bottomContent.getChildren().clear();
            infoLabels.getChildren().clear();

            showStatsLabel.setOnMouseClicked(clickEvent -> showStats());
            showEffectivenessLabel.setOnMouseClicked(clickEvent -> showEffectiveness());

            showStatsLabel.setStyle("-fx-font-size: 15px; -fx-font-family: Gill Sans; -fx-cursor: hand;");
            showEffectivenessLabel.setStyle("-fx-font-size: 15px; -fx-font-family: Gill Sans; -fx-cursor: hand;");

            if (topContent.getChildren().contains(formSelector)) {
                VBox.setMargin(infoLabels, new Insets(25, 0, 0, 0));
            }

            infoLabels.getChildren().addAll(showStatsLabel, showEffectivenessLabel);

            bodyContent.getChildren().remove(bottomContent);

            topContent.getChildren().add(infoLabels);

            body.setPannable(false);

            bottomContent = null;
        }
    }


    private boolean isNumeric(String text) {

        try {
            Integer.parseInt(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    private void scrollAnimation() {

        final int MILLISECS = 400;
        final Timeline TIME_LINE = new Timeline();
        final KeyValue KEY_VALUE = new KeyValue(body.vvalueProperty(), 1.0);
        final KeyFrame KEY_FRAME = new KeyFrame(javafx.util.Duration.millis(MILLISECS), KEY_VALUE);

        TIME_LINE.getKeyFrames().add(KEY_FRAME);
        TIME_LINE.play();
    }


    public void setTyping() {

        final float WIDTH = 125;
        final float HEIGHT = 25;

        typingContent.getChildren().clear();

        typeOneImg = new Sprite().getTyping(Dex.getInstance().getCurrPokemon().getTypeOne());

        typeOneImg.setFitWidth(WIDTH);
        typeOneImg.setFitHeight(HEIGHT);
        typeOneImg.setPreserveRatio(true);

        typingContent.getChildren().add(typeOneImg);

        if (Dex.getInstance().getCurrPokemon().getTypeTwo() != null) {
            typeTwoImg = new Sprite().getTyping(Dex.getInstance().getCurrPokemon().getTypeTwo());

            typeTwoImg.setFitWidth(WIDTH);
            typeTwoImg.setFitHeight(HEIGHT);
            typeTwoImg.setPreserveRatio(true);

            typingContent.getChildren().add(typeTwoImg);
        }

        VBox.setMargin(typingContent, new Insets(20, 0, 0, 0));
    }


    public void setBitSprite() {

        ArrayList<Pokemon> dex = Dex.getInstance().getDex();

        if (Dex.getInstance().getCurrPokemon().getEntryNum() == dex.get(0).getEntryNum()) {
            prevButton.setGraphic(new Sprite().getBitSprite(dex.get(Dex.getInstance().getDex().size() - 1).getEntryNum()));
            nextButton.setGraphic(new Sprite().getBitSprite(Dex.getInstance().getNext().getEntryNum()));
        }

        else if (Dex.getInstance().getCurrPokemon().getEntryNum() == dex.get(dex.size() - 1).getEntryNum()) {
            prevButton.setGraphic(new Sprite().getBitSprite(Dex.getInstance().getPrevious().getEntryNum()));
            nextButton.setGraphic(new Sprite().getBitSprite(dex.get(0).getEntryNum()));
        } else {
            prevButton.setGraphic(new Sprite().getBitSprite(Dex.getInstance().getPrevious().getEntryNum()));
            nextButton.setGraphic(new Sprite().getBitSprite(Dex.getInstance().getNext().getEntryNum()));
        }
    }


    public void setEntryNumLabel() {

        ArrayList<Pokemon> dex = Dex.getInstance().getDex();

        if (Dex.getInstance().getCurrPokemon().getEntryNum() > dex.get(0).getEntryNum() && Dex.getInstance().getCurrPokemon().getEntryNum() < dex.get(dex.size() - 1).getEntryNum()) {
            prevEntryLabel.setText(String.format("#%03d", dex.get(BinarySearch.searchByEntry(dex, Dex.getInstance().getCurrPokemon().getEntryNum()) - 1).getEntryNum()));
            nextEntryLabel.setText(String.format("#%03d", dex.get(BinarySearch.searchByEntry(dex, Dex.getInstance().getCurrPokemon().getEntryNum()) + 1).getEntryNum()));
        } else {
            if (Dex.getInstance().getCurrPokemon().getEntryNum() == dex.get(0).getEntryNum()) {
                prevEntryLabel.setText(String.format("#%03d", dex.get(Dex.getInstance().getDex().size() - 1).getEntryNum()));
                nextEntryLabel.setText(String.format("#%03d", dex.get(BinarySearch.searchByEntry(dex, Dex.getInstance().getCurrPokemon().getEntryNum()) + 1).getEntryNum()));
            }

            else if (Dex.getInstance().getCurrPokemon().getEntryNum() == dex.get(dex.size() - 1).getEntryNum()) {
                prevEntryLabel.setText(String.format("#%03d", dex.get(BinarySearch.searchByEntry(dex, Dex.getInstance().getCurrPokemon().getEntryNum()) - 1).getEntryNum()));
                nextEntryLabel.setText(String.format("#%03d", dex.get(0).getEntryNum()));
            }
        }
    }


    public void addRemoveFilterButton() {

        filterButton = new Button();
        filterButton.setGraphic(new ImageView(new Image(String.valueOf(getClass().getResource("/Images/Symbols/RemoveFilter.png")))));

        filterButton.setOnAction(event -> {
            event.consume();
            PokeData.setPokeData(Dex.getInstance().getDex(), "NationalDex");
            Dex.getInstance().setCurrPokemon(Dex.getInstance().getDex().get(0));
            filterButton.setGraphic(new ImageView(new Image(String.valueOf(getClass().getResource("/Images/Symbols/AddFilter.png")))));
            filterButton.setOnAction(e -> handleFilter());
            setNameLabel();
            setEntryLabel();
            setSprite();
            setBitSprite();
            setTyping();
            setEntryNumLabel();
            clearSearch();
            addFormSelector();
            hideInfo();
        });

        topContent.getChildren().set(0, filterButton);

        filterButton.setStyle("-fx-background-color: Transparent; -fx-cursor: hand;");

        VBox.setMargin(filterButton, new Insets(0, 0, 0, 455));
    }


    private class ChangeFormHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {

            event.consume();

            Dex.getInstance().getCurrPokemon().setName(Dex.getInstance().getCurrPokemon().getName().replaceFirst("Gigantamax ", ""));
            Dex.getInstance().setCurrPokemon(Dex.getInstance().getDex().get(BinarySearch.searchByEntry(Dex.getInstance().getDex(), Dex.getInstance().getCurrPokemon().getEntryNum())));

            switch (((ComboBox) event.getSource()).getSelectionModel().getSelectedItem().toString()) {
                case "Mega Form" ->
                        Dex.getInstance().setCurrPokemon(Dex.getInstance().getCurrPokemon().getMegaForm());
                case "Mega X Form" ->
                        Dex.getInstance().setCurrPokemon(Dex.getInstance().getCurrPokemon().getMegaForm('x'));
                case "Mega Y Form" ->
                        Dex.getInstance().setCurrPokemon(Dex.getInstance().getCurrPokemon().getMegaForm('y'));
                case "Gigantamax Form" ->
                        Dex.getInstance().getCurrPokemon().setName("Gigantamax " + Dex.getInstance().getCurrPokemon().getName());
                case "Alolan Form" ->
                        Dex.getInstance().setCurrPokemon(Dex.getInstance().getCurrPokemon().getAlolanForm());
                case "Galarian Form" ->
                        Dex.getInstance().setCurrPokemon(Dex.getInstance().getCurrPokemon().getGalarianForm());
            }

            setNameLabel();
            setSprite();
            setTyping();
            clearSearch();
            addFormSelector();
            hideInfo();
        }
    }
}
