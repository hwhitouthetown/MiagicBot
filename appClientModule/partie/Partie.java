package partie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import application.Api;
import application.Convertor;
import exceptions.InvalidChoiceException;
import exceptions.InvalidStatusException;
import heroes.EpicHero;
import ia.Archer_IA;
import ia.Chaman_IA;
import ia.Guard_IA;
import ia.Ia;
import ia.Orc_IA;
import ia.Paladin_IA;
import ia.Priest_IA;

public class Partie {
	private int choix;
	
	private String idPartie;
	private String idEquipe;
	private Status statut;
	private int nbTours; 
	private Api apiCaller; 
	
	private Board board;
	
	private Convertor convert;

	private Ia bot;

	private ArrayList<Ia> lescerveaux; 
	/*
	 * Si le niveau est -1 c'est qu'on joue c'est que l'on va jouer en mode VS
	 */
	public Partie(String url, String idEquipe, String format, int choix)
			throws IOException, InvalidChoiceException, InterruptedException {


		this.idEquipe = idEquipe;

		this.choix = choix;
		this.idPartie = "NA";
		this.nbTours = 1;
		
		apiCaller = new Api(url,format);
		
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

					this.idPartie = apiCaller.nextGame(idEquipe);

					while (idPartie.equals("NA")) {
						Thread.sleep(500);
						this.idPartie = apiCaller.nextGame(idEquipe);
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

					this.idPartie = apiCaller.newGame(Integer.toString(choix), idEquipe);

					while (idPartie.equals("NA")) {
						Thread.sleep(500);
						this.idPartie = apiCaller.newGame(Integer.toString(choix), idEquipe);
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


	/*
	 *  ----------- FIN PARTIE INITIALISATION -------
	 */

	
	/*
	 * ------------ METHODES WORKFLOW ------------
	 */
	
	public void UpdateBoard() throws InvalidStatusException, IOException{
		
		String response = apiCaller.GetBoard(idPartie); 
	
		this.board = convert.convert(response);
		
	}
	
	
	public void UpdateCoups() throws IOException, InterruptedException{
		
		String response = apiCaller.getLastMove(idPartie, idEquipe);
		
		System.out.println("response: "+response);
		
		if(!response.equals("NA")){
			int i=0; 
			String[] dol = response.split("\\$");
			for(int j=0;j<dol.length;j++){
			 
				System.out.println("\nj ->" + dol[j]);	
				
				 String tab[] = dol[j].split(",");
				 
				 if(tab.length>2){
					this.board.getAdversaire().getFighters().get(i).addCoups(tab[1]+","+tab[2]);
				 }
				
			i++;
			}	
		}
		
		board.afficherCoupAdversaires();
	}
	
	


	
	/*
	 * ------------ FIN METHODES WORKFLOW ------------
	 */

	
	
	/*
	 * ------------ METHODES POUR JOUER ------ 
	 */
	
	public void Jouer() throws InterruptedException, IOException, InvalidStatusException {

	
		this.statut = apiCaller.GetStatus(idPartie, idEquipe);
	
		String response = "";
		
		while(!finPartie()){
			// On met à jour le status de la partie // 
			this.statut = apiCaller.GetStatus(idPartie, idEquipe);
		
			while(statut.equals(Status.CANTPLAY)){
				System.out.println("Status CANT PLAY, statut :" + statut + "" );
				this.statut = apiCaller.GetStatus(idPartie, idEquipe);
				Thread.sleep(500);
			}

			if(statut.equals(Status.CANPLAY)){

				System.out.println("Status CANPLAY, statut :" + statut + "" );
				// On récupère le board // 
				UpdateBoard();
			
				if(nbTours<4){

					String perso = bot.choisirJoueurEquipe(board.getAdversaire().getFighters());
					
					Ia ia; 
					
					switch(perso){
					
					case("GUARD"): 
						ia = new Guard_IA();
						break;
					case("PRIEST"): 
						ia = new Priest_IA();
						break;
					case("ORC"): 	
						ia = new Orc_IA();
						break;
					case("PALADIN"): 	
						ia = new Paladin_IA();
						break;
					case("ARCHER"): 	
						ia = new Archer_IA();
						break;
					case("CHAMAN"):
						ia = new Chaman_IA();
						break;
					default : 
						ia = new Guard_IA(); 
						break;
					}
						
					response = apiCaller.Jouer(this.idPartie, this.idEquipe,perso);
					System.out.println(response);
					this.lescerveaux.add(ia);
					
					Thread.sleep(500);
				
				} else {
					
					String input = "";
					String joueur = "";  
					String coup = ""; 
				
					updateIaPlayer();

					
					for(Ia ia: lescerveaux){
						
						
						int index = lescerveaux.indexOf(ia);
						
						int numeroJoueur = index+1;
						
						joueur = "A" + numeroJoueur; 
						
						coup = ia.Jouer(board);
						
						ia.choisirCible(board);
						
						input += joueur +","+ coup; 
						
						if(index!=lescerveaux.size()-1){
							input += "$";
						}
					}
					
					
					System.out.println("Coup :" + input);
					Thread.sleep(500);
					
					
					response = apiCaller.Jouer(this.idPartie, this.idEquipe, input);
					
					System.out.println("reponse après joué :" + response);
					UpdateCoups();
					UpdateBoard();

				}			
					this.nbTours++;
			}
			
			
			
			
		} 
		
		System.out.println(statut);
	} // Fin jouer //

	
	
	
	
	
	/*
	 * ------------ METHODES POUR JOUER ------ 
	 */
	
	
	private void updateIaPlayer(){
		
		
			for(int i=0;i<this.board.getMiagicBot().getFighters().size();i++){				
				lescerveaux.get(i).updateData(this.board.getMiagicBot().getFighters().get(i));
			}	
	}
	




	/*
	 * ------------ METHODES GENERIQUES  ------------
	 */
	
	
	/*
	 * Permet de savoir si une partie est terminée 
	 */
	public boolean finPartie(){
		return (statut.equals(Status.DRAW)||statut.equals(Status.CANCELLED) || statut.equals(Status.DEFEAT) ||statut.equals(Status.VICTORY));
	}
	
	/*
	 * ------------ FIN METHODES GENERIQUES  ------------
	 */
	
	
	

	/* Getters and Setters */

}
