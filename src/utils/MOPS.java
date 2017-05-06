package utils;

import aiproj.slider.Move;
import aiproj.slider.Move.Direction;;

public class MOPS{
	
	// functions to retrieve forward left and right from respective player's perspective
	public static Direction forward(char player){
		switch(player){
		
		case 'H':
			return Direction.RIGHT;
		// case 'V': handled by default case	
		default:
			return Direction.UP;
		}
	}
	
	public static Direction right(char player){
		switch(player){
		
		case 'H':
			return Direction.DOWN;
		default:
			return Direction.RIGHT;
		}
	}
	
	public static Direction left(char player){
		switch(player){
		
		case 'H':
			return Direction.UP;
		default:
			return Direction.LEFT;
		}
	}
	
	public static boolean equalMoves(Move m1, Move m2){
		return m1 != null && m2 != null && m1.i == m2.i && m1.j == m2.j && m1.d == m2.d;
	}
}
