package ia;

import java.util.ArrayList;

import heroes.EpicHero;
import partie.Board;

public class Orc_IA extends Ia {
	@Override
	public void coup(Board b) {
        String coup = "REST";
        this.choisirCible(b);
        if(this.hero.getCurrentMana() >= 2 && !readyToAttack){
            coup = "YELL";
        }else if(readyToAttack && this.hero.getCurrentMana() >= 1){
            coup = "ATTACK";
            readyToAttack = false;
        }
        if (this.hero.isYelled()){
            coup = "DEFEND";
        }
        this.coup = coup;
	}
	
	@Override
    public void choisirCible(Board board) {
        String response = "";
        int indexFocus = 0;
        ArrayList<EpicHero> equipe_adv = board.getAdversaire().getFighters();

        for (EpicHero ep : equipe_adv) {
            for (String focus: focus){
                if((focus.equals(ep.getFighterClass()) || ep.isCritical() || ep.isYelled())&& !ep.isDead()){
                    if (ep.isYelled()){
                        readyToAttack = true;
                    }
                    int index = equipe_adv.indexOf(ep) +1;
                    response = "E" + index ;
                }

            }
        }
        this.target = response;
    }
}
