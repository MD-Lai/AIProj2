package agents.mldz.minimax;

import agents.mldz.Interplay;
import aiproj.slider.Move;
import board.Board;

public class Minimax extends Interplay{
	public Move nextMove(){
		return minimaxDecision();
	}
	
	private Move minimaxDecision(){
		
		Move best = null;
		int highScore = Integer.MIN_VALUE;
		int tempScore = Integer.MIN_VALUE;
		Board nb;
		for(Move m : this.board.movesAvailable(this.me)){
			
			nb = new Board(this.board.getTiles(), m);
			tempScore = minimaxVal(nb, this.me, 4);
			
			if(tempScore > highScore){
				highScore = tempScore;
				best = m;
			}
			
			
		}
		System.out.println(Board.BLOCKS[this.me] + " " + highScore);
		return best;
	}
	
	private int minimaxVal(Board b, byte player, int folds){
		if(b.hasFinished() || folds == 0){
			return b.evaluate(player);
		}
		// my turn to move, max
		else if(player == this.op){
			Board nb;
			int score = Integer.MIN_VALUE;
			int tempScore = Integer.MIN_VALUE;
			for(Move m : b.movesAvailable(this.me)){
				nb = new Board(b.getTiles(), m);
				
				tempScore = minimaxVal(nb, this.me, folds - 1);
				
				if(tempScore > score){
					score = tempScore;
				}
			}
			return score;
		}
		// opponent's turn to move, min
		else{
			Board nb;
			int score = Integer.MAX_VALUE;
			int tempScore = Integer.MAX_VALUE;

			for(Move m : b.movesAvailable(this.op)){
				nb = new Board(b.getTiles(), m);
				
				// evaluate board score for ME given opponent's moves
				tempScore = minimaxVal(nb, this.me, folds - 1);
				
				if(tempScore < score){
					score = tempScore;
				}
			}
			return score;
		}
	}
	
}
