package com.example.pokedex2;

import java.util.ArrayList;

public class Pokemon implements Cloneable {
	
    private String name;
	private final String typeOne;
	private final String typeTwo;
    	private final int entryNum;
	private final int baseHp;
	private final int baseAtt;
	private final int baseDef;
	private final int baseSpA;
	private final int baseSpD;
	private final int baseSpeed;
	private final int generation;
    

    public Pokemon(int entryNum, String name, String typeOne, String typeTwo, int baseHp, int baseAtt, int baseDef, int baseSpA, int baseSpD, int baseSpeed, int generation) {

    	this.entryNum = entryNum;
    	this.typeOne = typeOne;
    	this.typeTwo = typeTwo;
    	this.name = name;
    	this.baseHp = baseHp;
    	this. baseAtt = baseAtt;
    	this.baseDef = baseDef;
    	this.baseSpA = baseSpA;
    	this.baseSpD = baseSpD;
    	this.baseSpeed = baseSpeed;
    	this.generation = generation;
    }


    @Override protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
    

	public int getEntryNum() {
		return entryNum;
	}


	public String getName() {
		return name;
	}
	

	public void setName(String name) {
		this.name = name;
	}
	

	public String getTypeOne() {
		return typeOne;
	}


	public String getTypeTwo() {
		return typeTwo;
	}


	public int getBaseHp() {
		return baseHp;
	}


	public int getBaseAtt() {
		return baseAtt;
	}


	public int getBaseDef() {
		return baseDef;
	}


	public int getBaseSpA() {
		return baseSpA;
	}


	public int getBaseSpD() {
		return baseSpD;
	}

	
	public int getBaseSpeed() {
		return baseSpeed;
	}


	public int getGeneration() {
    	return generation;
	}


	public int getBaseTotal() {
    	return baseHp + baseAtt + baseDef + baseSpA + baseSpD + baseSpeed;
    }


	public boolean hasMultipleForms(String dexName) {

    	ArrayList<Pokemon> dex = new ArrayList<>();

    	PokeData.setPokeData(dex, dexName);

		return BinarySearch.searchByEntry(dex, this.entryNum) != -1;
	}


    public boolean hasGigantamax() {

    	final int[] GIGANTAMAX_ENTRY_NUMS = {3, 6, 9, 12, 25, 52, 68, 94, 99, 131, 133, 143, 569, 809, 812, 815, 818,
                823, 826, 834, 839, 841, 844, 849, 851, 858, 861, 869, 879, 884, 892};

		for (int entry : GIGANTAMAX_ENTRY_NUMS) {
			if (entry <= entryNum) {
				if (entryNum == entry) {
					return true;
				}
			} else {
				break;
			}
		}

    	return false;
    }


    public ArrayList<String> getForms() {

    	ArrayList<String> forms = new ArrayList<>();

    	forms.add("Normal Form");

		if (this.hasMultipleForms("MegaDex")) {
			if (this.name.contains("Charizard") || this.name.contains("Mewtwo")) {
				forms.add("Mega X Form");
				forms.add("Mega Y Form");
			} else {
				forms.add("Mega Form");
			}
		}

		if (this.hasGigantamax()) {
			forms.add("Gigantamax Form");
		}

		if (this.hasMultipleForms("AlolanDex")) {
			forms.add("Alolan Form");
		}

		if (this.hasMultipleForms("GalarianDex")) {
			forms.add("Galarian Form");
		}

		return forms;
	}


	public Pokemon getMegaForm() {

        ArrayList<Pokemon> megaDex = new ArrayList<>();

        PokeData.setPokeData(megaDex, "MegaDex");

        return megaDex.get(BinarySearch.searchByEntry(megaDex, this.entryNum));
    }


	public Pokemon getMegaForm(char megaType) {

		ArrayList<Pokemon> megaDex = new ArrayList<>();
		Pokemon mega = null;

		PokeData.setPokeData(megaDex, "MegaDex");

		if (megaType == 'x') {
            mega = megaDex.get(BinarySearch.searchByEntry(megaDex, entryNum) - 1);
        }

		else if (megaType == 'y') {
            mega =  megaDex.get(BinarySearch.searchByEntry(megaDex, entryNum));
        }

		return mega;
	}


	public Pokemon getAlolanForm() {

		ArrayList<Pokemon> alolanDex = new ArrayList<>();

		PokeData.setPokeData(alolanDex, "AlolanDex");

		return alolanDex.get(BinarySearch.searchByEntry(alolanDex, this.entryNum));
	}


	public Pokemon getGalarianForm() {

		ArrayList<Pokemon> galarianDex = new ArrayList<>();

		PokeData.setPokeData(galarianDex, "GalarianDex");

		return galarianDex.get(BinarySearch.searchByEntry(galarianDex, this.entryNum));
	}
}
