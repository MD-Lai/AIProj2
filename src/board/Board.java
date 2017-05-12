package board;

import java.util.ArrayList;

import aiproj.slider.Move;
import utils.MOPS;

public class Board {
	
	public static final int FREE = 0; // Free block
	public static final int BLCK = 1; // Blocked block
	public static final int VERT = 2; // Vertical player piece
	public static final int HORI = 3; // Horizontal player piece
	public static final char[] BLOCKS = {'+', 'B', 'V', 'H'}; // character representation of tile use as BLOCKS[board[x][y]]
	
	private byte[][] board;
	
	public static byte[][] convertBoard(int dimension, String board){
		
		byte[][] a = new byte[dimension][dimension];
		int s = 0;
		
		String[] rows = board.replaceAll(" ", "").split("\n");
		
		
		for(int y = dimension - 1; y >= 0; --y){
			for(int x = 0; x < dimension; ++x){
				switch(rows[s].charAt(x)){
				case '+':
					a[x][y] = FREE;
					break;
				case 'B':
					a[x][y] = BLCK;
					break;
				case 'V':
					a[x][y] = VERT;
					break;
				case 'H':
					a[x][y] = HORI;
					break;
				}
			}
			++s;
		}
		return a;
	}
	
	public Board(byte[][] bBoard){
		int dim = bBoard.length;
		
		this.board = new byte[dim][dim];
		
		for(int y = 0; y < dim; ++y){
			for(int x = 0; x < dim; ++x){
				this.board[x][y] = bBoard[x][y];
			}
		}
	}
	
	public Board(byte[][] bBoard, Move m){
		// create a new board updated with a new move
		
		int dim = bBoard.length;
		
		this.board = new byte[dim][dim];
		
		for(int y = 0; y < dim; ++y){
			for(int x = 0; x < dim; ++x){
				this.board[x][y] = bBoard[x][y];
			}
		}
		this.movePiece(m.i, m.j, m.d);
		
	}
	
	// blindly moves a piece, up to user to give a valid move
	public void movePiece(int x, int y, Move.Direction d){
		switch(d){
		// just account for moving up and right off the board, assume player gives correct moves
		case UP:
			if(y+1 > this.board.length - 1){
				// replace with free if moving off board
				this.board[x][y] = FREE;
			}
			else{
				this.board[x][y+1] = this.board[x][y];
			}
			break;
		case RIGHT:
			if(x+1 > this.board.length - 1){
				// replace with free if moving off board
				this.board[x][y] = FREE;
			}
			else{
				this.board[x+1][y] = this.board[x][y];
			}
			break;
		case DOWN:
			this.board[x][y-1] = this.board[x][y];
			break;
		case LEFT:
			this.board[x-1][y] = this.board[x][y];
			break;
		}
		this.board[x][y] = FREE;
	}
	
	// checks if a given x and y are within bounds
	public boolean withinBounds(int x, int y){
		//System.out.println("b"+x+y);
		if(x > this.board.length - 1 || x < 0 || y > this.board.length - 1 || y < 0){
			return false;
		}
		return true;
	}
	
	// publically useable version of withinBounds
	// pass it dimension of board and an x,y coord
	public static boolean withinBounds(int d, int x, int y){
		if(x > d - 1 || x < 0 || y > d - 1 || y < 0){
			return false;
		}
		return true;
	}
	
	public boolean validMove(int x, int y, Move.Direction dir){
		
		int curr = this.board[x][y];
		
		// in case you check a piece that isn't a player piece
		switch(curr){
		case FREE:
			return false;
		case BLCK:
			return false;
		case VERT:
			switch(dir){
			case UP:
				if(y == board.length - 1 || withinBounds(x, y + 1) && board[x][y+1] == Board.FREE){
					return true;
				}
				break;
			case RIGHT:
				if(withinBounds(x + 1, y) && board[x+1][y] == Board.FREE){
					return true;
				}
				break;
			case LEFT:
				if(withinBounds(x - 1, y) && board[x-1][y] == Board.FREE){
					return true;
				}
				break;
			default:
				return false;
			}
			break;
		case HORI:
			switch(dir){
			case RIGHT:
				if(x == board.length - 1 || withinBounds(x + 1, y) && board[x+1][y] == Board.FREE){
					return true;
				}
				break;
			case DOWN:
				if(withinBounds(x, y - 1) && board[x][y-1] == Board.FREE){
					return true;
				}
				break;
			case UP:
				if(withinBounds(x, y + 1) && board[x][y+1] == Board.FREE){
					return true;
				}
				break;
			default:
				return false;
			}
			break;
		default:
			return false;
		}
		
		return false;
	}

	
	
	/**
	 * Evaluation function ideally should look at the relative difference between you and your opponent
	 * All eval functions should return difference between opponent's state and your state
	 * So that minimax will know which move is "better"
	 * since an opponent should always move to put current player at a disadvantage
	 */
	
	/**
	 * Gives a rough estimation of the "goodness" of a board in a given state for a given player
	 * Attempts to sum up the value of current board state
	 * @param player player to evaluate for
	 * @return approximate value of a current board state
	 */
	// TODO a better evaluation function, one that weighs your state vs opponent's state
	public int evaluate2(byte player){
		// heuristic evaluation of board
		int w1 = 4;
		int w2 = 2; 
		int w3 = 3;
		int w4 = 90;
		//     move forward             number of enemies blocked     pieces relative to opponent
		return w1 * manhattan(player) + w2 * enemiesBlocked(player) + w3 * relativePieces(player) + w4 * hasWon(player);
	}
	
	/**
	 * evaluates the comparative utility of given player vs the other player
	 * @param player player to evaluate for
	 * @return relative utility of board for given player
	 */
	public int evaluate(byte player){
		// few things to look out for
		// piece moves forward -> is able to win the game
		// pieces don't become blocked by opponent or environment -> doesn't put itself at a disadvantage
		// pieces are able to put enemy at a disadvantage -> is able to hinder opponent from winning the game to some extent
		// no two or combination of two evaluations should result in the same OR contradicting information
		
		int w1 = 4;
		int w2 = 1;
		int w3 = 2;
		int w4 = this.board.length;
		// all of these evaluations should be positive aspects for player
		return (w1 * relForward(player))
				+ (w2 * relBehind(player))
				+ (w3 * relFree(player)) 
				+ (w4 * relOff(player)); 
	}
	
	// relative pieces forward
	private int relForward(byte player){
		
		int nVOff = this.board.length - 1; // how many pieces V should have if none are off
		int nHOff = this.board.length - 1; // how many pieces H should have if none are off
		int hForward = 0;
		int vForward = 0;
		for(int y = 0; y < this.board.length; y++){
			for(int x = 0; x < this.board.length; x++){
				// total forward for V player
				switch(this.tileAt(x, y)){
				case VERT:
					--nVOff;
					vForward += y;
					break;
				// total forward for H player
				case HORI:
					--nHOff;
					hForward += x;
					break;
				}
			}
		}
		// account for fact that having less pieces SHOULD be a positive factor
		// applies for entire evaluation
		hForward += nHOff * this.board.length;
		vForward += nVOff * this.board.length;
		// + 1 accounts for the fact that horizontal always has the first move advantage
		return player == HORI ? hForward + 1 - vForward : vForward - hForward;
	}
	
	// relative number of pieces behind enemies
	// TODO implement
	private int relBehind(byte player){
		// relative pieces with forward and left free for V, forward and right for H
		// don't want to get out of a bind just to get blocked again immediately after
		//
		// min just moved
		//     + + +
		//     + H +
		//     + V +
		//
		// max's turn to move
		// available moves
		//   1       2
		// + + +   + + +
		// + H + > + H +
		// V + +   + + V
		// we want to +1 to get to state 1, -1 for state 2
		// if both occur simultaneously, evaluates to 0
		// if(board[x+1][y+1] == H && board[x][y+1] == +)
		
		int nH = 0; // sum of case 1 and case 2 for H
		int nV = 0; // sum of case 1 and case 2 for V
		
		for(int y = 0; y < this.board.length; y++){
			for(int x = 0; x < this.board.length; x++){
				switch(this.tileAt(x, y)){
				case HORI:
					
					break;
				case VERT:
					
					break;
				}
			}
		}
		return 0;
	}
	
	// relative pieces with forward direction free
	// i.e. count number of free available forward moves
	private int relFree(byte player){
		int vFree = 0; // vertical pieces free
		int hFree = 0; // horizontal pieces free
		for(int y = 0; y < this.board.length; y++){
			for(int x = 0; x < this.board.length; x++){
				switch(this.tileAt(x, y)){
				case HORI:
					// H piece is on edge of board   H piece is in normal regions of board
					if(x + 1 == this.board.length || x + 1 < this.board.length && this.tileAt(x + 1, y) == FREE){
						hFree++;
					}
					break;
				case VERT:
					if(y + 1 == this.board.length || y + 1 < this.board.length && this.tileAt(x, y+1) == FREE){
						vFree++;
					}
					break;
				}
			}
		}
		
		return player == HORI ? hFree - vFree : vFree - hFree;
	}
	
	private int relOff(byte player){
		int nVOff = this.board.length - 1; // how many pieces V should have if none are off
		int nHOff = this.board.length - 1; // how many pieces H should have if none are off
		for(int y = 0; y < this.board.length; y++){
			for(int x = 0; x < this.board.length; x++){
				// subtract one if a piece is present
				switch(this.tileAt(x, y)){
				case VERT:
					--nVOff;
					break;
				// total forward for H player
				case HORI:
					--nHOff;
					break;
				}
			}
		}
		// counts number of pieces that a player has moved off
		// adjust weight in evaluate func
		return player == HORI ? nHOff - nVOff : nVOff - nHOff;
	}
	
	
	private int hasWon(byte player){
		int hp = 0;
		int vp = 0;
		for(int y = 0; y < this.board.length; y++){
			for(int x = 0; x < this.board.length; x++){
				if(this.tileAt(x, y) == HORI){
					++hp;
				}
				else if(this.tileAt(x, y) == VERT){
					++vp;
				}
			}
		}
		if(hp == 0){
			// horizontal won
			return player == HORI ? 1 : -1;
		}
		else if(vp == 0){
			// vertical won
			return player == VERT ? 1 : -1;
		}
		else{
			// nobody won
			return 0;
		}
	}
	
	/**
	 * How many pieces the opponent has relative to the player
	 * Higher is better, more opponent pieces relative to yours
	 * @param player player to evaluate for
	 * @return n opponent pieces - n player's pieces
	 */
	private int relativePieces(byte player){
		int hp = 0;
		int vp = 0;
		// count for each H, dist = dim(board) - curr(x)
		// count for each V, dist = dim(board) - curr(y)
		for(int y = 0; y < this.board.length; y++){
			for(int x = 0; x < this.board.length; x++){
				if(this.tileAt(x, y) == HORI){
					++hp;
				}
				else if(this.tileAt(x, y) == VERT){
					vp++;
				}
				
			}
		}
		// opponent pieces - my pieces 
		return player == VERT ? hp - vp : vp - hp;
	}
	
	/**
	 * How many pieces of the opponent are blocked, either by player or blocked tiles
	 * @param player player to evaluate for
	 * @return n opponent pieces in a forward blocked state
	 */
	private int enemiesBlocked(byte player){
		int nblocked = 0;
		// if H: look right
		switch(player){
		case HORI:
			for(int y = 0; y < this.board.length; y++){
				for(int x = 0; x < this.board.length; x++){
					if(this.tileAt(x, y) == HORI){
						if(this.withinBounds(x, y-1) && (this.tileAt(x, y-1) == VERT || this.tileAt(x, y-1) == BLCK)){
							nblocked++;
						}
					}
				}
			}
		// if V look left
		case VERT:
			for(int y = 0; y < this.board.length; y++){
				for(int x = 0; x < this.board.length; x++){
					if(this.tileAt(x, y) == VERT){
						if(this.withinBounds(x-1, y) && (this.tileAt(x-1, y) == HORI || this.tileAt(x-1, y) == BLCK)){
							nblocked++;
						}
					}
				}
			}
		}
		return nblocked;
	}
	
	/** 
	 * Distance a piece has to the edge of the board + any blocks it encounters
	 * Value is negative as a larger distance is a bigger DISadvantage
	 * @param player which player to check
	 * @return negative value of distance to edge of board +1 for any blocks a piece encounters
	 */
	private int manhattan(byte player) {
		int dim = board.length;
		int mandist = 0;
		
		if (player == HORI) {
			for(int x = 0; x < dim; x++) {
				for(int y = 0; y < dim; y++) {
					if (this.tileAt(x, y) == VERT) {
						mandist += pManhattan(x, y);
					}
				}
			}
		return -mandist;
		} else if (player == VERT) {
			for(int x = 0; x < dim; x++) {
				for(int y = 0; y < dim; y++) {
					if (this.tileAt(x, y) == HORI) {
						mandist += pManhattan(x, y);
					}
				}
			}
		return -mandist;
		} else {
			return dim;
		}
	}

	private int pManhattan(int x, int y) {
		int dim = board.length;
		if(board[x][y] == VERT) {
			return dim - y + forwardBlocked(x, y);
		} else if(board[x][y] == HORI) {
			return dim - x + forwardBlocked(x, y);
		} else {
			return dim;
		}
	}

	private int forwardBlocked(int x, int y) {
		int dim = board.length;
		if(board[x][y] == VERT) {
			for(int j = y + 1; j < dim; j ++) {
				if(board[x][j] == BLCK) {
					return 2;
				}
			}
			return 0;
		} else if(board[x][y] == HORI) {
			for(int i = x + 1; i < dim; i++) {
				if(board[i][y] == BLCK) {
					return 2;
				}
			}
			return 0;
		} else {
			System.out.println("Block is not occupied by a piece.");
			return dim;
		}
	}
	
	/**
	 * Entire list of available moves to a particular player
	 * Scans board twice, once for vertical moves, once for right and left moves, in that order
	 * @param player player to evaluate for
	 * @return array of moves available for that player
	 */
	public Move[] movesAvailable(byte player){
		
		ArrayList<Move> moves = new ArrayList<Move>();
		// scans array 2 times, once for forward, once for both sideways
		// when picking a move later, ties are broken by pieces which came in first
		// ideally would move forward first then sideways
		
		for(int y = 0; y < this.board.length; y++){
			for(int x = 0; x < this.board.length; x++){
				if(player == this.tileAt(x, y)){
					if(this.validMove(x, y, MOPS.forward(player))){
						moves.add(new Move(x,y,MOPS.forward(player)));
					}
				}
			}
		}
		for(int y = 0; y < this.board.length; y++){
			for(int x = 0; x < this.board.length; x++){
				if(player == this.tileAt(x, y)){
					if(this.validMove(x, y, MOPS.right(player))){
						moves.add(new Move(x,y,MOPS.right(player)));
					}
					if(this.validMove(x, y, MOPS.left(player))){
						moves.add(new Move(x,y,MOPS.left(player)));
					}
				}
			}
		}
		return moves.toArray(new Move[moves.size()]);
	}	

	/**
	 * Checks if a particular state of the game is finished or not
	 * @return true if a game is finished
	 */
	public boolean hasFinished(){
		int hp = 0;
		int vp = 0;
		for(int y = 0; y < this.board.length; y++){
			for(int x = 0; x < this.board.length; x++){
				if(this.tileAt(x, y) == HORI){
					++hp;
				}
				else if(this.tileAt(x, y) == VERT){
					++vp;
				}
			}
		}
		return hp == 0 || vp == 0;
	}
	
	public byte[][] getTiles(){
		return this.board;
	}
	
	public int getLen(){
		return this.board.length;
	}
	
	public int tileAt(int x, int y){
		return this.board[x][y];
	}
	
	public char charAt(int x, int y){
		return BLOCKS[this.board[x][y]];
	}
	
	public String toString(){
		String str = "";
		
		for(int y = this.board.length - 1; y >= 0; --y){
			
			for(int x = 0; x < this.board.length; ++x){
				str += this.charAt(x, y);
				if(x != this.board.length-1){
					str += (" ");
				}
			}
			str += "\n";
		}
		
		return str;
	}
}
