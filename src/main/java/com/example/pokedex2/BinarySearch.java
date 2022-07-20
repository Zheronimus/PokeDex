package com.example.pokedex2;

import java.util.ArrayList;
import java.util.Comparator;

public class BinarySearch {

    public static int searchByEntry(ArrayList<Pokemon> dex, int entryNum) {

        int left = 0, right = dex.size() - 1, elementPos = -1;

        while (left <= right) {
            int mid = (left + right) / 2;

            if (dex.get(mid).getEntryNum() == entryNum) {
                elementPos = mid;
                break;
            } else if (dex.get(mid).getEntryNum() < entryNum) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return elementPos;
    }


    public static Pokemon searchByName(ArrayList<Pokemon> dex, String name) {

        int left = 0, right = dex.size() - 1;
        Pokemon pokemon = null;

        dex.sort(Comparator.comparing(Pokemon::getName));

        while (left <= right) {
            int mid = left + (right - left) / 2;

            int res = name.toLowerCase().trim().compareTo(dex.get(mid).getName().trim().toLowerCase());

            if (res == 0) {
                pokemon = dex.get(mid);
            }

            if (res > 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        dex.sort(Comparator.comparing(Pokemon::getEntryNum));
        return pokemon;
    }
}
