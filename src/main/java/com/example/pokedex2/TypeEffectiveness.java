package com.example.pokedex2;

import com.google.gson.Gson;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class TypeEffectiveness {

	private final VBox typeEffectiveness;
	private Type[] allTypes;


	public TypeEffectiveness() {

		typeEffectiveness = new VBox(15);
		typeEffectiveness.setAlignment(Pos.CENTER);

		try (Reader reader = new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/TypeEffectiveness.json")))) {
			Gson gson = new Gson();
			allTypes = gson.fromJson(reader, Type[].class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		final Label EFFECTIVENESS_HEADER = new Label("Type Effectiveness");
		EFFECTIVENESS_HEADER.setStyle("-fx-font-family: Gill Sans; -fx-font-size: 25px;");

		typeEffectiveness.getChildren().add(EFFECTIVENESS_HEADER);
	}


	public VBox getTypeEffectiveness() {

		List<HBox> typeContainers = getTypeContainers();
		List<HBox> effectivenessContainers = getEffectivenessContainers();
		HBox row = new HBox(50);

		for (int i = 0; i < typeContainers.size(); i++) {
			row.setAlignment(Pos.CENTER);

			row.getChildren().add(new HBox(typeContainers.get(i), effectivenessContainers.get(i)));

			if ((i + 1) % 2 == 0) {
				typeEffectiveness.getChildren().add(row);

				row = new HBox(50);
			}
		}

		return typeEffectiveness;
	}


	private ArrayList<HBox> getTypeContainers() {

		ArrayList<HBox> typeContainers = new ArrayList<>();

		for (int i = 0; i < allTypes.length; i++) {
			Label typeLabel = new Label();

			typeLabel.setText(allTypes[i].getType());
			typeLabel.setMinWidth(100);
			typeLabel.setMinHeight(25);
			typeLabel.setAlignment(Pos.CENTER);

			typeContainers.add(new HBox(typeLabel));
			typeContainers.get(i).setStyle("-fx-background-color: " + getTypeColor(allTypes[i].getType()) + ";"
					+ "-fx-font-size: 15px;"
					+ "-fx-font-family: Gill Sans;"
					+ "-fx-border-width: 1px;"
					+ "-fx-border-color: Black;"
					+ "-fx-border-radius: 15px 0px 0px 15px;"
					+ "-fx-background-radius: 15px 0px 0px 15px;");
		}

		return typeContainers;
	}


	private ArrayList<HBox> getEffectivenessContainers() {

		String[] types = {Dex.getInstance().getCurrPokemon().getTypeOne(), Dex.getInstance().getCurrPokemon().getTypeTwo()};
		ArrayList<HBox> effectivenessContainers = new ArrayList<>();

		for (int i = 0; i < allTypes.length; i++) {
			double effectivenessNum = 1;

			for (int j = 0; j < types.length; j++) {
				if (types[j] != null) {
					Type type = new Type();

					for (Type currType : allTypes) {
						if (currType.getType().equalsIgnoreCase(types[j])) {
							type = currType;
						}
					}

					for (String resist : type.getResistances()) {
						if (allTypes[i].getType().equalsIgnoreCase(resist)) {
							effectivenessNum *= 0.5;
						}
					}

					for (String immune : type.getImmunities()) {
						if (allTypes[i].getType().equalsIgnoreCase(immune)) {
							effectivenessNum *= 0;
						}
					}

					for (String vulnerable : type.getVulnerabilities()) {
						if (allTypes[i].getType().equalsIgnoreCase(vulnerable)) {
							effectivenessNum *= 2;
						}
					}
				}
			}

			Label effectivenessLabel = new Label();
			effectivenessLabel.setText(convertDecimal(effectivenessNum) + "x");
			effectivenessLabel.setMinWidth(40);
			effectivenessLabel.setMinHeight(25);
			effectivenessLabel.setAlignment(Pos.CENTER);

			effectivenessContainers.add(new HBox(effectivenessLabel));
			effectivenessContainers.get(i).setStyle("-fx-font-size: 15px; -fx-border-width: 1px 1px 1px 0px; -fx-border-color: Black; -fx-border-radius: 0px 15px 15px 0px;");
		}

		return effectivenessContainers;
	}


	private String getTypeColor(String type) {

		HashMap<String, String> colorMap = new HashMap<>();

		colorMap.put("Normal", "#919AA2");
		colorMap.put("Fire", "#FF9D55");
		colorMap.put("Water", "#5090D6");
		colorMap.put("Electric", "#F4D23C");
		colorMap.put("Grass", "#63BC5A");
		colorMap.put("Ice", "#73CEC0");
		colorMap.put("Fighting", "#CE416B");
		colorMap.put("Poison", "#AA6BC8");
		colorMap.put("Ground", "#D97845");
		colorMap.put("Flying", "#8FA9DE");
		colorMap.put("Psychic", "#FA7179");
		colorMap.put("Bug", "#91C12F");
		colorMap.put("Rock", "#C5B78C");
		colorMap.put("Ghost", "#5269AD");
		colorMap.put("Dragon", "#0B6DC3");
		colorMap.put("Dark", "#5A5465");
		colorMap.put("Steel", "#5A8EA2");
		colorMap.put("Fairy", "#EC8FE6");

		if (colorMap.containsKey(type)) {
			return colorMap.get(type);
		}

		return "";
	}


	private String convertDecimal(double effectivenessNum) {

		String convertedDecimal;

		if (effectivenessNum % 1 != 0) {
			int denominator = (int) (1 / effectivenessNum);

			convertedDecimal = "1/" + denominator;
		} else {
			convertedDecimal = Integer.toString((int) effectivenessNum);
		}

		return convertedDecimal;
	}
}
