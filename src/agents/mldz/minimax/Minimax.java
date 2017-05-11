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
			tempScore = minimaxVal(nb, this.me, 6);
			
			if(tempScore > highScore){
				highScore = tempScore;
				best = m;
			}
			
			
		}
		System.out.println(Board.BLOCKS[this.me] + " " + highScore);
		return best;
	}
	
	private int minimaxVal(Board b, byte player, int folds){
		int score = Integer.MIN_VALUE;
		int tempScore = Integer.MIN_VALUE;
		Board nb;
		if(b.hasFinished() || folds == 0){
			return b.evaluate(player);
		}
		else if(player == this.op){
			// my turn to move, max
			for(Move m : b.movesAvailable(this.me)){
				nb = new Board(this.board.getTiles(), m);
				
				tempScore = minimaxVal(nb, this.me, folds - 1);
				
				if(tempScore > score){
					score = tempScore;
				}
			}
		}
		else{
			// opponent's turn to move, min
			for(Move m : b.movesAvailable(this.op)){
				nb = new Board(this.board.getTiles(), m);
				
				tempScore = minimaxVal(nb, this.op, folds - 1);
				
				if(tempScore > score){
					score = tempScore;
				}
			}
		}
		return score;
	}
}
