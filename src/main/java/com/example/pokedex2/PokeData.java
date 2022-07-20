package com.example.pokedex2;

import java.sql.*;
import java.util.ArrayList;

public class PokeData {
	
	public PokeData() {
		
	}
	

	private static Connection connect() {
		
		String connString = "jdbc:sqlite::resource:PokemonDB.db";
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(connString);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return conn;
	}
	

	public static void setPokeData(ArrayList<Pokemon> dex, String tableName) {

		String sql = "SELECT * FROM " + tableName;

		try (Connection conn = connect(); PreparedStatement statement = conn.prepareStatement(sql)) {
			ResultSet data = statement.executeQuery();

			if (!dex.isEmpty()) {
				dex.clear();
			}

			while (data.next()) {
				dex.add(new Pokemon(data.getInt("EntryNum"), data.getString("Name"), data.getString("TypeOne"), data.getString("TypeTwo"),
						data.getInt("Hp"), data.getInt("Attack"), data.getInt("Defense"), data.getInt("SpAttack"), data.getInt("SpDefense"),
						data.getInt("Speed"), data.getInt("Generation")));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}
