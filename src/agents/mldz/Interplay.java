package agents.mldz;

import aiproj.slider.SliderPlayer;
import board.Board;
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
		
		switch(player){
		case 'H':
			for(int y = 0; y < b.length; y++){
				for(int x = 0; x < b.length; x++){
					if(player == Board.BLOCKS[b[x][y]]){
						// get available horizontal moves
						
						// check up 
						if(Board.withinBounds(b.length, x, y + 1) && b[x][y+1] == Board.FREE){
							free++;
						}
						
						// check right
						if(x + 1 == b.length || Board.withinBounds(b.length, x + 1, y) && b[x+1][y] == Board.FREE){
							free++;
						}
						
						// check down
						if(Board.withinBounds(b.length, x, y - 1) && b[x][y-1] == Board.FREE){
							free++;
						}
					}
				}
			}
			break;
		case 'V':
			for(int y = 0; y < b.length; y++){
				for(int x = 0; x < b.length; x++){
					if(player == Board.BLOCKS[b[x][y]]){
						// get available horizontal moves
						
						// check up 
						if(y == b.length || Board.withinBounds(b.length, x, y + 1) && b[x][y+1] == Board.FREE){
							free++;
						}
						
						// check right
						if(Board.withinBounds(b.length, x + 1, y) && b[x+1][y] == Board.FREE){
							free++;
						}
						
						// check left
						if(Board.withinBounds(b.length, x - 1, y) && b[x-1][y] == Board.FREE){
							free++;
						}
					}
				}
			}
			break;
		default:
			break;
		}
		
		return free;
	}
	
	protected Move[] movesAvailable(char player){
		byte[][] b = this.board.getTiles();
		
		ArrayList<Move> moves = new ArrayList<Move>();
		
		switch(player){
		case 'H':
			for(int y = 0; y < b.length; y++){
				for(int x = 0; x < b.length; x++){
					if(player == Board.BLOCKS[b[x][y]]){
						// get available horizontal moves
						
						// check up 
						if(Board.withinBounds(b.length, x, y + 1) && b[x][y+1] == Board.FREE){
							moves.add(new Move(x,y,Move.Direction.UP));
						}
						
						// check right
						if(x == b.length - 1 || Board.withinBounds(b.length, x + 1, y) && b[x+1][y] == Board.FREE){
							moves.add(new Move(x,y,Move.Direction.RIGHT));
						}
						
						// check down
						if(Board.withinBounds(b.length, x, y - 1) && b[x][y-1] == Board.FREE){
							moves.add(new Move(x,y,Move.Direction.DOWN));
						}
					}
				}
			}
			break;
		case 'V':
			for(int y = 0; y < b.length; y++){
				for(int x = 0; x < b.length; x++){
					if(player == Board.BLOCKS[b[x][y]]){
						// get available horizontal moves
						
						// check up 
						if(y == b.length - 1 || Board.withinBounds(b.length, x, y + 1) && b[x][y+1] == Board.FREE){
							moves.add(new Move(x,y,Move.Direction.UP));
						}
						
						// check right
						if(Board.withinBounds(b.length, x + 1, y) && b[x+1][y] == Board.FREE){
							moves.add(new Move(x,y,Move.Direction.RIGHT));
						}
						
						// check left
						if(Board.withinBounds(b.length, x - 1, y) && b[x-1][y] == Board.FREE){
							moves.add(new Move(x,y,Move.Direction.LEFT));
						}
					}
				}
			}
			break;
			
		default:
			break;
		}
		
		return moves.toArray(new Move[moves.size()]);
	}
}
