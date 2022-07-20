package com.example.pokedex2;

import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;

public class FormCell extends ListCell<String> {

    @Override
    public void updateItem(String form, boolean empty) {

        super.updateItem(form, empty);

        if (!empty) {
            setText(form);

            ImageView formImg;

            if (form.equals("Normal Form")) {
                formImg = new Sprite().getFormSymbol("Pokeball");
            }

            else if (form.contains("Mega")) {
                formImg = new Sprite().getFormSymbol("Mega Form");
            } else {
                formImg = new Sprite().getFormSymbol(form);
            }

            formImg.setFitWidth(17);
            formImg.setFitHeight(17);
            formImg.setSmooth(true);
            setGraphic(formImg);
        }
    }
}
