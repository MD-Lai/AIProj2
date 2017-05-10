package agents.mldz.heuristic;

import agents.mldz.Interplay;
import aiproj.slider.Move;
import board.Board;

public class Heuristic extends Interplay{
	
	@Override
	public Move nextMove(){
		Move best = null;
		int score = Integer.MIN_VALUE;
		int tempScore = Integer.MIN_VALUE;
		Board eval;
		
		//System.out.println(this.me + " Boards after possible moves");
		for(Move m : this.movesAvailable(this.me)){
			eval = new Board(this.board.getTiles(), m);
			tempScore = eval.evaluate(this.me);
			if(tempScore > score){
				score = tempScore;
				best = m;
			}
			//Board nb = new Board(this.board.getTiles(), m);
			//System.out.println(nb.toString());
			
		}
		//System.out.println("move chosen\n");
		return best;
	}
}
