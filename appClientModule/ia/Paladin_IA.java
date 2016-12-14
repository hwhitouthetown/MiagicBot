package ia;

import heroes.EpicHero;
import partie.Board;

import java.util.ArrayList;

public class Paladin_IA extends Ia {
    public boolean readyToAttack = false;
    public void coup(Board b) {
        String coup = "ATTACK";
        this.choisirCible(b);
        if(this.hero.getCurrentMana() >= 2 && !readyToAttack){
            coup = "YELL";
        }else{
            coup = "ATTACK";
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
            if((this.focus[indexFocus].equals(ep.getFighterClass()) || ep.isCritical() || ep.isYelled())&& !ep.isDead()){
                if (ep.isYelled()){
                    readyToAttack = true;
                }
                int index = equipe_adv.indexOf(ep) +1;
                response = "E" + index ;
            }else{
                indexFocus++;
            }
        }
        this.target = response;
    }
}
