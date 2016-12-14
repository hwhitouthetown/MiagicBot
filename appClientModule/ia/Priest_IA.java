package ia;

import java.util.ArrayList;

import heroes.EpicHero;
import partie.Board;

public class Priest_IA extends Ia {

	public boolean isDenfending = false;
	@Override
	public void coup(Board b) {
		String coup = "REST";
		this.choisirCible(b);
		if(isDenfending && this.hero.getCurrentMana() >= 2){
			coup = "HEAL";
		}else if(!isDenfending && this.hero.getCurrentMana() >= 3){
			coup = "ATTACK";
			this.readyToAttack = false;
		}else{
			coup = "REST";
		}
		if (this.hero.isYelled() && b.canDefend()){
			coup = "DEFEND";
            this.hero.addState("DEFENDING");

		}
		
		if(this.hero.isCritical() && !this.hero.isProtected() && b.canDefend() && hTarget == this.hero){
			if(this.hero.getCurrentMana()>= 2){
				coup = "HEAL";
	            this.hero.addState("HEALING");
			}else{
				coup = "DEFEND";
	            this.hero.addState("DEFENDING");
			}
        	

        }
		this.coup = coup;
	}
	@Override
	public void choisirCible(Board board) {
		String response = "";
		int indexFocus = 0;
		ArrayList<EpicHero> equipe_adv = board.getAdversaire().getFighters();
		ArrayList<EpicHero> equipe = board.getMiagicBot().getFighters();
		this.isDenfending = false;
		boolean found = false;
        while(!found){
        	String focus = this.focus[indexFocus];
        	for (EpicHero ep : equipe_adv) {
                if((focus.equals(ep.getFighterClass()) || ep.isCritical() || ep.isYelled())&& !ep.isDead()){
                    int index = equipe_adv.indexOf(ep) +1;
                    hTarget = ep;
                    response = "E" + index ;
                    found = true;
                }
            }
        	indexFocus++;
        }
		for (EpicHero ep : equipe) {
			if( !ep.isDead() && ep.getCurrentLife() <= ep.getMaxAvailableLife() - 8 && this.hero.getCurrentMana() >= 2){
				if(!isDenfending || (hTarget != null && ep.getCurrentLife() < hTarget.getCurrentLife())){
					int index = equipe.indexOf(ep) +1;
					hTarget = ep;
					this.isDenfending = true;
					response = "A" + index ;
				}
			}
		}
		hTarget.addState("PROTECTED");
		this.target = response;
	}
}
