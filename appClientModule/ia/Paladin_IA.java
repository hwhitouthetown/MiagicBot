package ia;

import heroes.EpicHero;
import partie.Board;

import java.util.ArrayList;

public class Paladin_IA extends Ia {
    public boolean readyToAttack = false;
    public void coup(Board b) {
        String coup = "ATTACK";
        this.choisirCible(b);
        if(this.hero.getCurrentMana() >= 2){
            coup = "CHARGE";
        }
        if (this.hero.isYelled() && b.canDefend()){
            coup = "DEFEND";
            this.hero.addState("DEFENDING");

        }
        if(this.hero.getCurrentMana() < 1){
            coup = "REST";
        }
        if(this.hero.isCritical() && !this.hero.isProtected() && !this.hTarget.isYelled() && b.canDefend()){
        	coup = "DEFEND";
            this.hero.addState("DEFENDING");

        }
        if(hTarget.getFighterClass().equals("GUARD") ){
        	coup = "ATTACK";
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
