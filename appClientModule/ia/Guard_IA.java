package ia;

import heroes.EpicHero;
import partie.Board;

public class Guard_IA extends Ia {

	@Override
	public void coup(Board b) {
		String coup = "REST";
		this.choisirCible(b);
		if(this.hero.getCurrentMana() >= 2){
			coup = "ATTACK";
		}else if(readyToAttack){
			coup = "ATTACK";
		}else{
			coup = "REST";
		}
		if (this.hero.isYelled()){
			coup = "DEFEND";
		}
		this.coup = coup;
	}

}
