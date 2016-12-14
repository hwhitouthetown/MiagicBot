package ia;

import java.util.ArrayList;

import heroes.EpicHero;
import partie.Board;

public class Orc_IA extends Ia {

	@Override
	public void coup(Board b) {
		String coup = "ATTACK";
		this.choisirCible(b);
		if(this.hero.getMaxAvailableMana() < 2){
			
		}
		this.coup = coup;
	}
	
	@Override
	public void choisirCible(Board board) {
		String response = "";
		int indexFocus = 0;
		ArrayList<EpicHero> equipe_adv = board.getAdversaire().getFighters();

		for (EpicHero ep : equipe_adv) {
			if((this.focus[indexFocus].equals(ep.getFighterClass()) || ep.isCritical() || ep.isYelled())&& !ep.isDead()){
					int index = equipe_adv.indexOf(ep) +1; 
					response = "E" + index ;
			}else{
				indexFocus++;
			}
		}
		this.target = response;
	}
}
