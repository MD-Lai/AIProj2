package agents.mldz.ab;

import agents.mldz.Interplay;
import aiproj.slider.Move;
import board.Board;

public class AB extends Interplay{
	
	public Move nextMove(){
		
		return abDecision();
	}
	
	private Move abDecision(){
		Move best = null;
		int highScore = Integer.MIN_VALUE;
		int tempScore = Integer.MIN_VALUE;
		Board nb;
		for(Move m : this.board.movesAvailable(this.me)){
			nb = new Board(this.board.getTiles(), m);
			tempScore = abMax(nb, Integer.MIN_VALUE, Integer.MAX_VALUE, this.me, 8);
			
			if(tempScore > highScore){
				highScore = tempScore;
				best = m;
			}
		}
		return best;
	}
	
	private int abMax(Board b, int alpha, int beta, byte player, int folds){
		if(folds == 0 || b.hasFinished()){
			return b.evaluate(player);
		}
		Board nb;
		// should be player
		for(Move m : b.movesAvailable(player)){
			nb = new Board(b.getTiles(), m);
			byte np;
			if(player == this.me){
				np = this.op;
			}
			else{
				np = this.me;
			}
			alpha = Integer.max(alpha, abMin(b, alpha, beta, np, folds - 1));
			if(alpha >= beta){
				return beta;
			}
		}
		return alpha;
	}
	
	private int abMin(Board b, int alpha, int beta, byte player, int folds){
		if(folds == 0 || b.hasFinished()){
			return b.evaluate(player);
		}
		Board nb;
		// should be opponent
		for(Move m : b.movesAvailable(player)){
			nb = new Board(b.getTiles(), m);
			byte np;
			if(player == this.me){
				np = this.op;
			}
			else{
				np = this.me;
			}
			beta = Integer.min(beta, abMax(b, alpha, beta, np, folds - 1));
			if(beta <= alpha){
				return alpha;
			}
		}
		return beta;
	}
}
