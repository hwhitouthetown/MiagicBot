package ia;

import heroes.EpicHero;
import partie.Board;

public class Guard_IA extends Ia {

	@Override
	public void coup(Board b) {
		choisirCible(b);
		for(EpicHero ep:b.getMiagicBot().getFighters()){
			if(ep.getCurrentLife()>this.pdvCritique){
				this.coup = "ATTACK";
			}
		}
		this.coup = "ATTACK";
	}

}
