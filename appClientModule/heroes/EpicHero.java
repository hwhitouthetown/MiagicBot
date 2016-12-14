package heroes;

import java.util.ArrayList;

public class EpicHero {
	private String fighterClass; 
	private int orderNumberInTeam; 
	private boolean isDead; 
	private int maxAvailableMana; 
	private int maxAvailableLife; 
	private int currentMana; 
	private int currentLife; 
	private ArrayList<String> coups;
	private ArrayList<State> states;
	private String fighterID;
	private static int criticalLife = 6;
	
	public EpicHero(String fighterClass, int orderNumberInTeam, boolean isDead, int maxAvailableMana,
			int maxAvailableLife, int currentMana, int currentLife, ArrayList<State> states, String fighterId) {
		this.fighterClass = fighterClass;
		this.orderNumberInTeam = orderNumberInTeam;
		this.isDead = isDead;
		this.maxAvailableMana = maxAvailableMana;
		this.maxAvailableLife = maxAvailableLife;
		this.currentMana = currentMana;
		this.currentLife = currentLife;
		this.states = states;
		this.fighterID = fighterId;
		this.coups = new ArrayList<String>();
	}
	
	

	public String getFighterClass() {
		return fighterClass;
	}
	public void setFighterClass(String fighterClass) {
		this.fighterClass = fighterClass;
	}
	public int getOrderNumberInTeam() {
		return orderNumberInTeam;
	}
	public void setOrderNumberInTeam(int orderNumberInTeam) {
		this.orderNumberInTeam = orderNumberInTeam;
	}
	public boolean isDead() {
		return isDead;
	}
	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}
	public int getMaxAvailableMana() {
		return maxAvailableMana;
	}
	public void setMaxAvailableMana(int maxAvailableMana) {
		this.maxAvailableMana = maxAvailableMana;
	}
	public int getMaxAvailableLife() {
		return maxAvailableLife;
	}
	public void setMaxAvailableLife(int maxAvailableLife) {
		this.maxAvailableLife = maxAvailableLife;
	}
	public int getCurrentMana() {
		return currentMana;
	}
	public void setCurrentMana(int currentMana) {
		this.currentMana = currentMana;
	}
	public int getCurrentLife() {
		return currentLife;
	}
	public void setCurrentLife(int currentLife) {
		this.currentLife = currentLife;
	}
	public ArrayList<State> getStates() {
		return states;
	}
	public void setStates(ArrayList<State> states) {
		this.states = states;
	}
	public String getFighterId() {
		return fighterID;
	}
	public void setFighterId(String fighterId) {
		this.fighterID = fighterId;
	}
	
	public boolean isCritical(){
		return this.currentLife <= criticalLife;
	}
	
	public boolean isYelled(){
		boolean res = false;
		for(State state : states){
			if(state.getType().equals("SCARED")){
				res = true;
			}
		}
		return res;
	}
	
	public void addCoups(String coup){
		
		if(this.coups==null){				
			this.coups = new ArrayList<String>();
		}
		
		System.out.println("ajouter coup");
		
		this.coups.add(coup);
	}
	
	public ArrayList<String> getCoups() {
		
		if(this.coups==null){				
			this.coups = new ArrayList<String>();
		}
		
		return coups;
	}
	
	public void setCoups(ArrayList<String> coups) {
		this.coups = coups;
	}
	
	@Override
	public String toString() {
		return "EpicHero [fighterClass=" + fighterClass + "\n orderNumberInTeam=" + orderNumberInTeam + "\n isDead="
				+ isDead + "\n maxAvailableMana=" + maxAvailableMana + "\n maxAvailableLife=" + maxAvailableLife
				+ "\n currentMana=" + currentMana + "\n currentLife=" + currentLife + "\n states=" + states
				+ "\n fighterId=" + fighterID + "]";
	} 
	
}
