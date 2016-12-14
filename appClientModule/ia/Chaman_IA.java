package ia;

import heroes.EpicHero;
import partie.Board;

import java.util.ArrayList;

public class Chaman_IA extends Ia{
	public boolean isDenfending = false;
	@Override
	public void coup(Board b) {
		String coup = "REST";
		this.choisirCible(b);
		if(this.isDenfending && this.hero.getCurrentMana()>=2){
			coup = "CLEANSE";
		}else if(this.hero.isCritical() && !this.hero.isProtected()){
			coup = "DEFEND";
		}else if(!this.isDenfending && this.hero.getCurrentMana() == this.hero.getMaxAvailableMana()){
			coup = "ATTACK";
		}else{
			coup = "REST";
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
			if(!ep.isDead() && ep.getStates().size() > 0 && this.hero.getCurrentMana()>=2){
					int index = equipe.indexOf(ep) +1;
					hTarget = ep;
					this.isDenfending = true;
					found = true;
					response = "A" + index ;
			}
		}
		if(!found){
			for (EpicHero ep : equipe) {
				if(!ep.isDead() && ep.getCurrentMana() == 1 && this.hero.getCurrentMana()>=2){
						int index = equipe.indexOf(ep) +1;
						hTarget = ep;
						this.isDenfending = true;
						found = true;
						response = "A" + index ;
				}
			}
		}
		hTarget.addState("PURIFIED");
		this.target = response;
	}
}
