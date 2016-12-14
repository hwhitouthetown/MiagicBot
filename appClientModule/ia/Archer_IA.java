package ia;

import java.util.ArrayList;

import heroes.EpicHero;
import partie.Board;

public class Archer_IA extends Ia{
	@Override
	public void coup(Board b) {
        String coup = "ATTACK";
        this.choisirCible(b);
        if(this.hero.getCurrentMana() >= 2){
            coup = "FIREBOLT";
        }else if(this.hero.getCurrentMana() == 1 ){
            coup = "REST";
        }
        if (this.hero.isYelled() && !this.hero.isProtected() && b.canDefend()){
            coup = "DEFEND";
            this.hero.addState("DEFENDING");
        }
        if(this.hero.isCritical() && !this.hero.isProtected() &&  b.canDefend()){
        	coup = "DEFEND";
            this.hero.addState("DEFENDING");
        }
        if(this.hero.getCurrentMana()< 1){
        	coup = "REST";
        }
        this.coup = coup;
	}
	
	@Override
    public void choisirCible(Board board) {
        String response = "";
        int indexFocus = 0;
        ArrayList<EpicHero> equipe_adv = board.getAdversaire().getFighters();
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
        this.target = response;
    }
}
