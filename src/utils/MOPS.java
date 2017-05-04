package utils;

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
}
