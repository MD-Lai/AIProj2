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
