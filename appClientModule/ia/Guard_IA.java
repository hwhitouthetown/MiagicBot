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
		}else if(readyToAttack && this.hero.getCurrentMana() >= 2){
			coup = "ATTACK";
			readyToAttack = false;
		}else if(this.hero.getCurrentMana() < 1){
			coup = "REST";
		}else{
			coup = "ATTACK";
		}
		if (this.hero.isYelled()){
			coup = "DEFEND";
		}
		this.coup = coup;
	}

}
