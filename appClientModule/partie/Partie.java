package partie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import application.Convertor;
import exceptions.InvalidChoiceException;
import exceptions.InvalidStatusException;
import heroes.EpicHero;
import ia.Guard_IA;
import ia.Ia;
import ia.Orc_IA;
import ia.Priest_IA;

public class Partie {

	// Toutes les URIS sont là sauf pour avoir l'id d'équipe //
	private final String URL_ID_PARTIE_VS = "/versus/next";
	private final String URL_ID_PARTIE_IA = "/practice/new";
	private final String URL_STATUS = "/game/status";
	private final String URL_BOARD = "/game/board";
	private final String URL_LAST_MOVE = "/game/getlastmove";
	private final String URL_PLAY = "/game/play";
	private final String URL_NAME_ADV = "/game/opponent";

	private int choix;
	
	private String idPartie;
	private String idEquipe;
	private String url;
	private String format;
	private Status statut;
	private int nbTours; 
	
	private Board board;
	
	private Convertor convert;

	private Ia bot;

	private ArrayList<Ia> lescerveaux; 
	/*
	 * Si le niveau est -1 c'est qu'on joue c'est que l'on va jouer en mode VS
	 */
	public Partie(String url, String idEquipe, String format, int choix)
			throws IOException, InvalidChoiceException, InterruptedException {

		this.url = url;
		this.idEquipe = idEquipe;
		this.format = format;
		this.choix = choix;
		this.idPartie = "NA";
		this.nbTours = 1;
		
		convert = new Convertor();
		bot = new Ia();
		board = new Board();
		lescerveaux = new ArrayList<Ia>();

		// Initialisation de la partie // 
		initPartie();
		

	}

	
	
	
	/*
	 *  ----------- PARTIE INITIALISATION -------
	 */
	
	
	/*
	 * Permet de lancer soit une partie practice soit une partie VS // 
	 */
	public void initPartie() throws IOException, InterruptedException, InvalidChoiceException{
		// MODE VS //
				// Choix == -1 - On va jouer en mode VS //
				if (this.choix == -1) {

					this.idPartie = NextGame();

					while (idPartie.equals("NA")) {
						Thread.sleep(500);
						this.idPartie = NextGame();
					}
					
					// On a un identifiant de la partie, on peut commencer à jouer
					try {
						Jouer();
					} catch (InvalidStatusException e) {
						e.printStackTrace();
					}
					

				} 
				// MODE PRACTICE //
				else if (this.choix > 0) {
					// Choix > 0 - On va jouer contre un bot de niveau choix si le
					// niveau est ok//

					// On vérifie le niveau des bots // si pas ok lever exception //

					// Level ok //

					this.idPartie = NewGame();

					while (idPartie.equals("NA")) {
						Thread.sleep(500);
						this.idPartie = NewGame();
					}
					
					// On a un identifiant de la partie, on peut commencer à jouer
					try {
						Jouer();
					} catch (InvalidStatusException e) {
						e.printStackTrace();
					}
					
				} else {
					throw new InvalidChoiceException("Erreur choix = 0");
				}
	}

	public String NewGame() throws IOException {

		String res;

		System.out.println("Demande de jouer en mode Practice niveau : '" + choix + "'");
		res = get(url + URL_ID_PARTIE_IA  + "/" + choix + "/" + idEquipe);
		System.out.println("Réponse : '" + res + "'");
		return res;
	}

	public String NextGame() throws IOException {

		String res;

		System.out.println("Demande de jouer en mode VS");
		res = get(url + URL_ID_PARTIE_VS  + "/" + idEquipe);
		System.out.println("Réponse : '" + res + "'");
		return res;
	}
	/*
	 *  ----------- FIN PARTIE INITIALISATION -------
	 */

	
	/*
	 * ------------ METHODES WORKFLOW ------------
	 */
	
	
	
	
	
	public void GetStatus() throws InvalidStatusException {

		try {
			String response = get(url + URL_STATUS + "/" + this.idPartie + "/" + this.idEquipe);
			this.statut =Status.valueOf(response);
			System.out.println("\n\nStatut: " + statut);
			
		} catch (Exception e) {
			throw new InvalidStatusException("Erreur lors de la récupération du status récupéré : '" + statut.toString() + "'" );
		}
	}
	
	
	public void GetBoard() throws InvalidStatusException, IOException {

	
			String response = "";
			response = get(url + URL_BOARD + "/" + this.idPartie + "?format=" + this.format);
			
			System.out.println("\n\n getboardResponse :" + response);
			
			board = convert.convert(response);
			
			System.out.println(board);
			
		
	}
	
	public void Play()  {
		
		
		

		
	}
	
	
	/*
	 * ------------ FIN METHODES WORKFLOW ------------
	 */

	
	
	/*
	 * ------------ METHODES POUR JOUER ------ 
	 */
	
	public void Jouer() throws InterruptedException, IOException, InvalidStatusException {

	
		GetStatus();
		
		
		while(!finPartie()){
			// On met à jour le status de la partie // 
			GetStatus();
		
			while(statut.equals(Status.CANTPLAY)){
				System.out.println("Status CANT PLAY, statut :" + statut + "" );
				Thread.sleep(500);
			}
		
			if(statut.equals(Status.CANPLAY)){
				System.out.println("Status CANPLAY, statut :" + statut + "" );
				// On récupère le board // 
				GetBoard();
			
				if(nbTours<4){

					String perso = bot.choisirJoueurEquipe(board.getAdversaire().getFighters());
					
					Ia ia; 
					
					switch(perso){
					
					case("GUARD"): 
						ia = new Guard_IA(); 
					case("PRIEST"): 
						ia = new Priest_IA(); 
					case("ORC"): 	
						ia = new Orc_IA();
					default : 
						ia = new Guard_IA(); 
						break;
					}
					
					get(url+URL_PLAY+"/"+idPartie+"/"+idEquipe+"/"+perso);
					
					this.lescerveaux.add(ia);
					
					Thread.sleep(500);
				
				} else {
					
					String response = ""; 
					String joueur = ""; 
					String cible = ""; 
					String coup = ""; 
				
					updateIaPlayer();
					
					for(Ia ia: lescerveaux){
						
						int index = lescerveaux.indexOf(ia);
						
						int numeroJoueur = index+1;
						
						joueur = "A" + numeroJoueur; 
						
						coup = ia.Jouer();
						
						cible = ia.choisirCible(board);
						
						response += joueur +","+ coup + "," + cible; 
						
						if(index!=lescerveaux.size()-1){
							response += "$";
						}
					}
					
					
					System.out.println("Coup :" + response);
					Thread.sleep(500);
					System.out.println(get(url+URL_PLAY+"/"+idPartie+"/"+idEquipe+"/"+response));
				}			
					this.nbTours++;
			}
			
			
			
			
		} 
	} // Fin jouer //

	
	
	
	
	
	/*
	 * ------------ METHODES POUR JOUER ------ 
	 */
	
	
	private void updateIaPlayer(){
		
		for(EpicHero ep : board.getMiagicBot().getFighters()){
			
			for(Ia ia : lescerveaux){
				ia.updateData(ep);
			}
			
			
		}
		
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
	/*
	 * ------------ FIN METHODE POUR CONTACTER LE SERVEUR ------------
	 */

	

	/*
	 * ------------ METHODES GENERIQUES  ------------
	 */
	
	
	/*
	 * Permet de savoir si une partie est terminée 
	 */
	public boolean finPartie(){
		return (statut.equals(Status.CANCELLED) || statut.equals(Status.DEFEAT) ||statut.equals(Status.VICTORY));
	}
	
	/*
	 * ------------ FIN METHODES GENERIQUES  ------------
	 */
	
	
	

	/* Getters and Setters */

}
