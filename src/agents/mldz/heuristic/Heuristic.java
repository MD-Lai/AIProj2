package agents.mldz.heuristic;

import agents.mldz.Interplay;
import aiproj.slider.Move;
import board.Board;

public class Heuristic extends Interplay{
	
	public Move move(){
		Move best = null;
		int score = -9999999;
		int tempScore = -999999999;
		
		System.out.println(this.me + " Boards after possible moves");
		for(Move m : this.movesAvailable(this.me)){
			tempScore = this.heuristic(m);
			if(tempScore > score){
				score = tempScore;
				best = m;
			}
			Board nb = new Board(this.board.getTiles(), m);
			System.out.println(nb.toString());
			
		}
		update(best);
		System.out.println("move chosen\n");
		return best;
	}
}
