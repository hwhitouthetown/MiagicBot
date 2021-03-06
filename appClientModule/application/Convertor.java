package application;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import heroes.EpicHeroesLeague;
import partie.Board;

public class Convertor {

	private String jsonContent;
	private JsonObject jsonObj;

	public Convertor() {

		jsonContent = "";

	}

	public Board convert(String jsonContent) {

		Board b = new Board();
		Gson gson = new GsonBuilder().create();

		Type listType = new TypeToken<ArrayList<EpicHeroesLeague>>() {
		}.getType();

		JsonObject jsonObject = gson.fromJson(jsonContent, JsonObject.class);

		System.out.println("\n\n\n\nGetBoard JSON :" + jsonObject.get("playerBoards"));
		
		List<EpicHeroesLeague> yourClassList = new Gson().fromJson(jsonObject.get("playerBoards"), listType);

		System.out.println(yourClassList.size());

		for (EpicHeroesLeague e : yourClassList) {
			if (e.getPlayerName().equals("Miagic Bot")) {
				b.setMiagicBot(e);
			} else {
				b.setAdversaire(e);
			}
		}

		b.setNbTurnsLeft(Integer.parseInt(jsonObject.get("nbrTurnsLeft").toString()));

		System.out.println(b);

		return b;

	}
	
	public void retrieveLastCoup(String content){
		
		
		
	}

}
