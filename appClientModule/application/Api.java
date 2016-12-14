package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import exceptions.InvalidStatusException;
import partie.Status;

public class Api {

	
	// Toutes les URIS sont là sauf pour avoir l'id d'équipe //
	private final String URL_ID_PARTIE_VS = "/versus/next";
	private final String URL_ID_PARTIE_IA = "/practice/new";
	private final String URL_STATUS = "/game/status";
	private final String URL_BOARD = "/game/board";
	private final String URL_LAST_MOVE = "/game/getlastmove";
	private final String URL_PLAY = "/game/play";
	private final String URL_NAME_ADV = "/game/opponent";
	
	private String url; 
	private String format; 
	
	public Api(String url,String format){
		this.format = format; 
		this.url = url; 
	}
	
	
	// /epic-ws/epic/game/getlastmove/{idPartie}/{idEquipe}
	
	public String getLastMove(String idPartie,String idEquipe) throws IOException{
		
		return get(url+ URL_LAST_MOVE + "/" + idPartie + "/" + idEquipe);
	}
	
	public String newGame(String choix, String idEquipe) throws IOException{
		String res;
		System.out.println("Demande de jouer en mode Practice niveau : '" + choix + "'");
		res = get(url + URL_ID_PARTIE_IA  + "/" + choix + "/" + idEquipe);
		System.out.println("Réponse : '" + res + "'");
		return res;
	}
	
	public String nextGame(String idEquipe) throws IOException{
		String res;

		System.out.println("Demande de jouer en mode VS");
		res = get(url + URL_ID_PARTIE_VS  + "/" + idEquipe);
		System.out.println("Réponse : '" + res + "'");
		return res;
	}
	
	public Status GetStatus(String idPartie,String idEquipe) throws IOException {


			String response = get(url + URL_STATUS + "/" + idPartie + "/" + idEquipe);
			
			System.out.println(response);
			
			return Status.valueOf(response);
	}
	
	public String GetBoard(String idPartie) throws InvalidStatusException, IOException {

		
		String response = "";
		response = get(url + URL_BOARD + "/" + idPartie + "?format=" + this.format);
		
		System.out.println("\n\n getboardResponse :" + response);
		
		return response; 
	}
	
	
	public String Jouer(String idPartie,String idEquipe,String perso) throws IOException{
		
		
		return get(url+URL_PLAY+"/"+idPartie+"/"+idEquipe+"/"+perso);

	}
	

	
	
	
	/*
	 * ------------ METHODE POUR CONTACTER LE SERVEUR ------------
	 */

	public static String get(String url) throws IOException {

		String res = "";

		URL oracle = null;
		try {
			oracle = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));

		String inputLine;
		while ((inputLine = in.readLine()) != null)
			res = inputLine;
		in.close();

		return res;
	}
}
