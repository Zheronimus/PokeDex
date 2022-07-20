package com.example.pokedex2;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class FormCellFactory implements Callback<ListView<String>, ListCell<String>>
{
    @Override
    public ListCell<String> call(ListView<String> listView) {
        return new FormCell();
    }
}