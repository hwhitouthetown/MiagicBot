package ia;

import partie.Board;

public class Priest_IA extends Ia {

	@Override
	public void coup(Board b) {
		choisirCible( b);
		this.coup = "ATTACK";
	}
}
