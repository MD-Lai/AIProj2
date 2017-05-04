package agents.mldz;

import aiproj.slider.SliderPlayer;
import board.Board;
import utils.MOPS;

import java.util.ArrayList;
import java.util.Random;

import aiproj.slider.Move;

/** base class with some foundation functions to help with generating moves
 * e.g. listing all available moves and counting number of available moves etc.
 */
public class Interplay implements SliderPlayer{
	
	protected Board board;
	protected char me;
	protected char op;
	
	@Override
	public void init(int dimension, String board, char player) {
		// TODO get board representation
		this.board = new Board(Board.convertBoard(dimension, board));
		
		if(player == 'H'){
			this.me = 'H';
			this.op = 'V';
		}
		else{
			this.me = 'V';
			this.op = 'H';
		}
		
	}

	@Override
	public void update(Move move) {
		// update based on opponent's move;
		if(move != null){
			this.board.movePiece(move.i, move.j, move.d);
		}
	}

	@Override
	public Move move() {
		Random r = new Random();
		// base movement, random
		Move[] m = this.movesAvailable(this.me);
		
		if(m.length == 0){
			return null;
		}
		
		Move next = m.length > 0 ? m[r.nextInt(m.length)] : null;
		
		this.board.movePiece(next.i, next.j, next.d);
		
		return next;
	}
	
	// TODO method to log move
	public void logMove(Move m){
		
	}
	
	public int countMoves(char player){
		byte[][] b = this.board.getTiles();
		
		int free = 0;
		for(int y = 0; y < b.length; y++){
			for(int x = 0; x < b.length; x++){
				if(player == Board.BLOCKS[b[x][y]]){
					if(this.board.validMove(x, y, MOPS.forward(player))){
						free++;
					}
					if(this.board.validMove(x, y, MOPS.right(player))){
						free++;
					}
					if(this.board.validMove(x, y, MOPS.left(player))){
						free++;
					}
				}
			}
		}
		return free;
	}
	
	// takes a player char as it could be used to check opponent's moves as well
	protected Move[] movesAvailable(char player){
		byte[][] b = this.board.getTiles();
		
		ArrayList<Move> moves = new ArrayList<Move>();
		
		for(int y = 0; y < b.length; y++){
			for(int x = 0; x < b.length; x++){
				if(player == Board.BLOCKS[b[x][y]]){
					if(this.board.validMove(x, y, MOPS.forward(player))){
						moves.add(new Move(x,y,MOPS.forward(player)));
					}
					if(this.board.validMove(x, y, MOPS.right(player))){
						moves.add(new Move(x,y,MOPS.right(player)));
					}
					if(this.board.validMove(x, y, MOPS.left(player))){
						moves.add(new Move(x,y,MOPS.left(player)));
					}
				}
			}
		}
		
		return moves.toArray(new Move[moves.size()]);
	}
}
