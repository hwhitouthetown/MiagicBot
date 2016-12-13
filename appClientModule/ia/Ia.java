package ia;

import java.util.ArrayList;

import heroes.EpicHero;
import partie.Board;

public class Ia {

	EpicHero hero;
	protected int pdvCritique;

	public String coup(Board b) {
		return "ATTACK";
	}

	public String choisirCible(Board board) {
		String response = "";

		ArrayList<EpicHero> equipe_adv = board.getAdversaire().getFighters();

		for (EpicHero ep : equipe_adv) {

			if (!ep.isDead()) {
				int index = equipe_adv.indexOf(ep) +1; 
				response = "E" + index ;
			}

		}
		return response;
	}

	// Procedure pour mettre à jour les données du hero de l'IA //
	public void updateData(EpicHero e) {
		this.hero = e;
	}

	// Choix des joueurs lors du début de la partie //
	public String choisirJoueurEquipe(ArrayList<EpicHero> joueurAdv) {

		String typeJoueur = "";

		if (joueurAdv == null) {
			typeJoueur = "ORC";
		} else if (joueurAdv.size() == 1) {
			typeJoueur = "PRIEST";
		} else {
			typeJoueur = "GUARD";
		}
		
		System.out.println("\n Choix du personnage :" + typeJoueur );

		return typeJoueur;
	}

	/*
	 * stratégie : défensive ou offensive ?
	 * return string with coup + "," + target
	 */
	public String Jouer(Board b) {
		return coup(b) + "," + choisirCible(b);
	}
}
