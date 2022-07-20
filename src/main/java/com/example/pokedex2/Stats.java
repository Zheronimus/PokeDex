package com.example.pokedex2;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;

public class Stats {

    private final VBox stats;


    public Stats() {

        stats = new VBox(10);
        stats.setAlignment(Pos.CENTER);

        final Label STATS_HEADER = new Label("Base Stats");

        STATS_HEADER.setStyle("-fx-font-family: Gill Sans; -fx-font-size: 25px;");

        stats.getChildren().add(STATS_HEADER);
    }


    public VBox getStats() {

        final int MAX_STAT = 255;
        final String[] STAT_DESC = {"Hp: ", "Attack: ", "Defense: ", "SpAttack: ", "SpDefense: ", "Speed: ", "Total: "};
        final int[] STAT_NUMS = {Dex.getInstance().getCurrPokemon().getBaseHp(), Dex.getInstance().getCurrPokemon().getBaseAtt(), Dex.getInstance().getCurrPokemon().getBaseDef(),
                Dex.getInstance().getCurrPokemon().getBaseSpA(), Dex.getInstance().getCurrPokemon().getBaseSpD(), Dex.getInstance().getCurrPokemon().getBaseSpeed(),
                Dex.getInstance().getCurrPokemon().getBaseTotal()};
        Label[] statLabels = new Label[7];

        for (int i = 0; i < statLabels.length; i++) {
            statLabels[i] = new Label();
            statLabels[i].setText(STAT_DESC[i] + STAT_NUMS[i]);
            statLabels[i].setStyle("-fx-font-family: Gill Sans; -fx-font-size: 18px;");

            if (i < (statLabels.length - 1)) {
                HBox statBox = new HBox();
                HBox statLineBox = new HBox();
                StatLine statLine = new StatLine(Color.YELLOWGREEN, STAT_NUMS[i]);
                StatLine fillLine = new StatLine(Color.GRAY, MAX_STAT - STAT_NUMS[i]);

                statBox.setAlignment(Pos.TOP_CENTER);
                statLineBox.setAlignment(Pos.TOP_CENTER);

                statBox.getChildren().add(statLabels[i]);
                statLineBox.getChildren().addAll(statLine, fillLine);

                if (STAT_NUMS[i] == MAX_STAT) {
                    statLineBox.getChildren().remove(fillLine);
                }

                stats.getChildren().addAll(statBox, statLineBox);
            } else {
                stats.getChildren().add(statLabels[i]);
            }
        }

        return stats;
    }


    private class StatLine extends Line {

        public StatLine(Color color, double statValue) {

            this.setStrokeWidth(8);
            this.setStrokeLineCap(StrokeLineCap.ROUND);
            this.setStartX(0);
            this.setStroke(color);

            if (statValue == 255) {
                this.setEndX(statValue * 0.83);
            } else {
                this.setEndX(statValue * 0.8);
            }
        }
    }
}