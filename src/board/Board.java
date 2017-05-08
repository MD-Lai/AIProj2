package board;

import aiproj.slider.Move;

public class Board {
	
	public static final int FREE = 0;
	public static final int BLCK = 1;
	public static final int VERT = 2;
	public static final int HORI = 3;
	public static final char[] BLOCKS = {'+', 'B', 'V', 'H'};
	
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
	
	// newly realised method to find collision 
	// returns (x,y) of first collision starting at (x,y), casting along directions specified
	// intended use is cast(x,y,dv,dv) for straight casts, cast(x,y,dv,dh) for diagonal casts
	// note 2nd direction will override first direction if along same axis
	public byte[] cast(int x, int y, Move.Direction v, Move.Direction h){
		int yDir = 0;
		int xDir = 0;
		byte[] coll = new byte[2];
		
		switch(v){
		case UP:
			yDir = 1;
			break;
		case DOWN:
			yDir = -1;
			break;
		case RIGHT:
			xDir = 1;
			break;
		case LEFT:
			xDir = -1;
			break;
		}
		
		switch(h){
		case UP:
			yDir = 1;
			break;
		case DOWN:
			yDir = -1;
			break;
		case RIGHT:
			xDir = 1;
			break;
		case LEFT:
			xDir = -1;
			break;
		}
		
		while(withinBounds(x + xDir,y + yDir) && board[x + xDir][y + yDir] == FREE){
			y += yDir;
			x += xDir;
		}
		if(!withinBounds(x + xDir,y + yDir)){
			coll[0] = (byte)x;
			coll[1] = (byte)y;
		} 
		
		coll[0] = (byte)(x + xDir);
		coll[1] = (byte)(y + yDir);
		
		return coll;
		
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
	
	public Board flipped(){
		byte[][] flipped = new byte[this.board.length][this.board.length];
		
		for(int y = 0; y < this.board.length; y++){
			for(int x = 0; x < this.board.length; x++){
				flipped[x][y] = this.board[y][x];
			}
		}
		
		return new Board(flipped);
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
	
	public String toString(){
		String str = "";
		
		for(int y = this.board.length - 1; y >= 0; --y){
			
			for(int x = 0; x < this.board.length; ++x){
				str += (BLOCKS[this.board[x][y]]);
				if(x != this.board.length-1){
					str += (" ");
				}
			}
			str += "\n";
		}
		
		return str;
	}

	public int manhattan(int direction) {
		int dim = board.length;
		int minDist = dim;
		if (direction == VERT) {
			for(int x = 0; x < dim; x++) {
				for(int y = 0; y < dim; y++) {
					if (board[x][y] == VERT) {
						minDist = Math.min(minDist, pManhattan(x, y));
					}
				}
			}
			return minDist;
		} else if (direction == HORI) {
			for(int x = 0; x < dim; x++) {
				for(int y = 0; y < dim; y++) {
					if (board[x][y] == HORI) {
						minDist = Math.min(minDist, pManhattan(x, y));
					}
				}
			}
			return minDist;
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
					return 1;
				}
			}
			return 0;
		} else if(board[x][y] == HORI) {
			for(int i = x + 1; i < dim; i++) {
				if(board[i][y] == BLCK) {
					return 1;
				}
			}
			return 0;
		} else {
			System.out.println("Block is not occupied by a piece.");
			return dim;
		}
	}
}
