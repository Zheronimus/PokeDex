package com.example.pokedex2;

public class Type {

    public String type;
    public String[] vulnerabilities;
    public String[] immunities;
    public String[] resistances;

    public Type() {

    }


    public String[] getImmunities() {
        return immunities;
    }


    public String[] getResistances() { return resistances; }


    public String getType() { return type; }


    public String[] getVulnerabilities() {
        return vulnerabilities;
    }
}