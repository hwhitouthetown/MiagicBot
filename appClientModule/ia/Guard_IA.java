package ia;

import heroes.EpicHero;
import partie.Board;

public class Guard_IA extends Ia {

	@Override
	public String coup(Board b) {
		
		
		for(EpicHero ep:b.getMiagicBot().getFighters()){
			
			if(ep.getCurrentLife()>this.pdvCritique){
				return "ATTACK";
			}
			
		}
		return "ATTACK";
	}

}
