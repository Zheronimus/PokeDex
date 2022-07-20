package com.example.pokedex2;

import java.util.ArrayList;
import java.util.Comparator;

public class Dex {

    private static Dex dexInstance;
    private ArrayList<Pokemon> dex;
    private Pokemon currPokemon;


    private Dex() {

        dex = new ArrayList<>();

        PokeData.setPokeData(dex, "NationalDex");
    }


    public static Dex getInstance() {

        if (dexInstance == null) {
            dexInstance = new Dex();
        }

        return dexInstance;
    }


    public Pokemon getPrevious() {
        return dex.get(dex.indexOf(currPokemon) - 1);
    }


    public Pokemon getNext() {
        return dex.get(dex.indexOf(currPokemon) + 1);
    }


    public Pokemon getCurrPokemon() {
        return currPokemon;
    }


    public void setCurrPokemon(Pokemon currPokemon) {
        this.currPokemon = currPokemon;
    }


    public ArrayList<Pokemon> getDex() {
        return dex;
    }


    public void filterByType(String type) {

        ArrayList<Pokemon> filteredDex = new ArrayList<>();

        for (Pokemon pokemon : dex) {
            if (pokemon.getTypeOne().equals(type)) {
                filteredDex.add(pokemon);
            }

            if (pokemon.getTypeTwo() != null) {
                if (pokemon.getTypeTwo().equals(type)) {
                    filteredDex.add(pokemon);
                }
            }
        }

        dex = filteredDex;
        dex.sort(Comparator.comparing(Pokemon::getEntryNum));
        setCurrPokemon(dex.get(0));
    }


    public void filterByForms(String form) {

        ArrayList<Pokemon> filteredDex = new ArrayList<>();

        for (Pokemon pokemon : dex) {
            switch (form) {
                case "Mega Form":
                    if (pokemon.hasMultipleForms("MegaDex")) {
                        filteredDex.add(pokemon);
                    }
                    break;

                case "Gigantamax Form":
                    if (pokemon.hasGigantamax()) {
                        filteredDex.add(pokemon);
                    }
                    break;

                case "Alolan Form":
                    if (pokemon.hasMultipleForms("AlolanDex")) {
                        filteredDex.add(pokemon);
                    }
                    break;

                case "Galarian Form":
                    if (pokemon.hasMultipleForms("GalarianDex")) {
                        filteredDex.add(pokemon);
                    }
                    break;
            }
        }

        dex = filteredDex;
        dex.sort(Comparator.comparing(Pokemon::getEntryNum));
        setCurrPokemon(dex.get(0));
    }


    public void filterByGeneration(int genNum) {

        ArrayList<Pokemon> filteredDex = new ArrayList<>();

        for (Pokemon pokemon : dex) {
            if (pokemon.getGeneration() == genNum) {
                filteredDex.add(pokemon);
            }
        }

        dex = filteredDex;
        setCurrPokemon(dex.get(0));
    }
}
