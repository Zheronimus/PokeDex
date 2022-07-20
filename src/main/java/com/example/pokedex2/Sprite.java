package com.example.pokedex2;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.*;
import java.util.Objects;

public class Sprite {

    private enum Unicode {

        COLON("\u003A"),
        FEMALE_SYMBOL("\u2640"),
        MALE_SYMBOL("\u2642"),
        PERIOD("\u002E"),
        SPACE("\u0020");

        private final String unicode;

        Unicode(String unicode) {
            this.unicode = unicode;
        }

        @Override
        public String toString() {
            return unicode;
        }
    }


    private InputStream getImgFile(String fileName, String folderName) {

        InputStream imgFile;

        fileName = fileName.replaceAll(Unicode.SPACE.toString(), "").replace(Unicode.PERIOD.toString(), "").replace(Unicode.COLON.toString(), "").
                replace(Unicode.MALE_SYMBOL.toString(), "M").replace(Unicode.FEMALE_SYMBOL.toString(), "F");

        imgFile = getClass().getResourceAsStream("/Images/" + folderName + "/" + fileName + ".png");

        return imgFile;
    }


    public Image getSprite() {

        final String[] FORM_PREFIXES = {"Galarian", "Gigantamax", "Mega", "Alolan"};
        String prefix = "";

        for (String formPrefix : FORM_PREFIXES) {
            if (Dex.getInstance().getCurrPokemon().getName().contains(formPrefix + " ")) {
                prefix = formPrefix + " ";
            }
        }

        return new Image(Objects.requireNonNull(
                getImgFile(Dex.getInstance().getCurrPokemon().getName(), prefix + "Sprites")), 225, 225, true, true);
    }


    public ImageView getBitSprite(int index) {

        return new ImageView(new Image(Objects.requireNonNull(getImgFile(
                Dex.getInstance().getDex().get(BinarySearch.searchByEntry(
                        Dex.getInstance().getDex(), index)).getName(), "BitSprites")), 0, 0, true, true));
    }


    public ImageView getTyping(String type) {

        return new ImageView(new Image(Objects.requireNonNull(this.getImgFile(type, "TypeImages"))));
    }


    public ImageView getFormSymbol(String symbol) {

        return new ImageView(new Image(Objects.requireNonNull(this.getImgFile(symbol, "Symbols"))));
    }
}