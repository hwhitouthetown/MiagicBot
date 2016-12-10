package heroes;

import java.util.ArrayList;

public class EpicHeroesLeague {

	private String playerId;
	private String playerName;

	ArrayList<EpicHero> fighters;

	public EpicHeroesLeague(String playerId, String playerName) {

		this.playerId = playerId;
		this.playerName = playerName;

		fighters = new ArrayList<EpicHero>();
	}

	public void AjouterHeroe(EpicHero ep) {
		fighters.add(ep);

		System.out.println(fightersToString());
	}

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public ArrayList<EpicHero> getFighters() {
		return fighters;
	}

	public void setFighters(ArrayList<EpicHero> fighters) {
		this.fighters = fighters;
	}

	@Override
	public String toString() {
		return "EpicHeroesLeague [playerId=" + playerId + ", playerName=" + playerName + fightersToString() + "]";
	}

	public String fightersToString(){
		
		String s = "-------- Equipe --------";
		
		
		if(fighters!=null){
			s += "\n Nombre de joueur(s) : " + fighters.size(); 
		
			for(EpicHero ep : fighters){
				s += ep.toString();
			}
		} else {
			s += "Aucun personnage";
		}
		return s; 
	}

}
