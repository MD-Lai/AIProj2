package utils;
// anything requiring strings is so slow
// try to minimise use of string as much as possible...
public class Row {
	public static int collapse(int... rowVals){
		// much faster, but harder to understand, works for only single digits
		// only allows 0-9
		int collapsed = 0;
		int factor = 1;
		for(int i = rowVals.length - 1; i >= 0; --i){
			collapsed += rowVals[i] * factor;
			factor *= 10;
		}
		return collapsed;
		
	}
	
	// adds number 'add' to end of row, only works for any ints
	public static int append(int row, int add){
		// much faster method
		// java doesn't display leading 0s of an int
		// values have to be > 0
		return row * n10(add) + add;
		/*
		// slow method
		return row < 0 ? add : Integer.parseInt(Integer.toString(row) + Integer.toString(add));
		 */
	}
	
	public static int prepend(int row, int add){
		// fast method
		return row <= 0 ? add : add * n10(row)  + row;
	}
	
	// function to retrieve a single digit of an int at specified index of board
	// (0,0) is bottom left corner, follows cartesian coords
	public static int getVal(int[] board, int x, int y){
		int row = board[y];
		
		int curr = Integer.toString(row).length();
		int d, k = -1;
		
		// lists numbers from right to left,
		// meaning index increments high to low
		while(row > 0 && curr-- != x){
			d = row / 10;
			k = row - d * 10;
			row = d;
		}
		return k;
	}
	
	// faster now
	// only works to replace single digits
	public static int replaceVal(int row, int rep, int ind){
		int newRow = 0;
		// actually index currently at
		int curr = Integer.toString(row).length() - 1;
		while (row > 0) {
			int d = row / 10;
			int k = row - d * 10;
			row = d;
			// k is the right hand most value
			// row is what's left after removing k
			
			// if not at ind, prepend k,
			// otherwise prepend rep
			// break and prepend row;
			if(curr == ind){
				newRow = prepend(newRow, rep);
				return prepend(newRow, row);
			}
			newRow = prepend(newRow, k);
			--curr;
		}
		// -1 signalling failure
		return -1;
		
		/*
		// slow method
		String rowStr;
		String newRow;
		// grab substring of section 0-ind + rep toString + ind+1-end;
		rowStr = Integer.toString(row);
		newRow = rowStr.substring(0, ind) + Integer.toString(rep) + rowStr.substring(ind + 1);
		return Integer.parseInt(newRow);
		*/
	}
	
	private static int n10(int row){
		int len = Integer.toString(row).length();
		int tens = 1;
		for(int i = 0; i < len;++i){
			tens *= 10;
		}
		return tens;
		// returns number of 10s in a number
		// i.e. 2930 => 1000
		
		// slow method
		
	}
}
