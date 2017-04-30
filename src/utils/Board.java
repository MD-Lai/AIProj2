package utils;

/** Bunch of Functions useful for creating and printing the board 
 * as well as numbers corresponding to what each digit represents
 * */

public class Board {
	// numbers corresponding to tile type
	public static final int FREE = 1;
	public static final int BLCK = 2;
	public static final int VERT = 3;
	public static final int HORI = 4;
	
	public static int[] convertBoard(int dimension, String board){
		int[] iBoard = new int[dimension];
		
		String[] rows = board.replaceAll(" ", "").split("\n");
		
		int r = 0;
		
		for(int i = dimension - 1; i >= 0; --i){
			iBoard[r++] = convertRow(rows[i]);
		}
		return iBoard;
		
	}
	
	public static int convertRow(String row){
		int iRow = 0;
		int rowLen = row.length();
		for(int i = 0; i < rowLen; i++){
			switch(row.charAt(i)){
			case '+':
				iRow = Row.append(iRow, FREE);
				break;
			case 'B':
				iRow = Row.append(iRow, BLCK);
				break;
			case 'V':
				iRow = Row.append(iRow, VERT);
				break;
			case 'H':
				iRow = Row.append(iRow, HORI);
				break;
			default:
				break;
			}
		}
		
		return iRow;
	}
	
	public static void movePiece(int[] iBoard, int x, int y, char move){
		switch(move){
		// just account for moving up and right off the board, assume player gives correct moves
		case 'u':
			if(y+1 < iBoard.length - 1){
				// only replace with value if not moving off board
				Row.replaceVal(iBoard, Row.getVal(iBoard, x, y), x, y+1);
			}
			break;
		case 'r':
			if(x+1 < iBoard.length - 1){
				Row.replaceVal(iBoard, Row.getVal(iBoard, x, y), x+1, y);
			}
			break;
		case 'd':
			Row.replaceVal(iBoard, Row.getVal(iBoard, x, y), x, y-1);
			break;
		case 'l':
			Row.replaceVal(iBoard, Row.getVal(iBoard, x, y), x-1, y);
			break;
		}
		
		Row.replaceVal(iBoard, Board.FREE, x, y);
	}
	
	public static void printiBoard(int[] iBoard){
		// prints board from top left corner to bottom right
		// bottom left corner is (0,0)
		// to access digit by digit we increment y slowest
		// i.e. for(y){for(x){}}
		for(int r = iBoard.length - 1; r >= 0; --r){
			System.out.println(iBoard[r]);
		}
	}
}
