package partie;

import com.google.gson.annotations.Expose;

import heroes.EpicHeroesLeague;


public class Board {
	@Expose
	private int nbTurnsLeft; 
	private EpicHeroesLeague miagicBot; 
	private EpicHeroesLeague adversaire; 
	
	public Board(EpicHeroesLeague miagicBot,EpicHeroesLeague adversaire, int nbTurnsLeft){
		this.miagicBot = miagicBot;
		this.adversaire = adversaire; 
		this.nbTurnsLeft = nbTurnsLeft;
	}

	public Board() {
		this.nbTurnsLeft = 0;
	}

	public int getNbTurnsLeft() {
		return nbTurnsLeft;
	}

	public void setNbTurnsLeft(int nbTurnsLeft) {
		this.nbTurnsLeft = nbTurnsLeft;
	}

	public EpicHeroesLeague getMiagicBot() {
		return miagicBot;
	}

	public void setMiagicBot(EpicHeroesLeague miagicBot) {
		this.miagicBot = miagicBot;
	}

	public EpicHeroesLeague getAdversaire() {
		return adversaire;
	}

	public void setAdversaire(EpicHeroesLeague adversaire) {
		this.adversaire = adversaire;
	}
	
	public void afficherCoupAdversaires() throws InterruptedException{
		
		for(int i=0;i<adversaire.getFighters().size();i++){
			
			System.out.println("\n\n" + this.adversaire.getFighters().get(i).getFighterClass());
			
			for(String s : this.adversaire.getFighters().get(i).getCoups()){
				
				System.out.println("\n-"+s);

			}	
		}
		
		Thread.sleep(3000);
	}

	@Override
	public String toString() {
		return "Board [nbTurnsLeft=" + nbTurnsLeft + "\n\n MiagicBot=" + miagicBot + "\n\nAdversaire=" + adversaire + "]";
	}
	
}
