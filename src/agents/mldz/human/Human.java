package agents.mldz.human;

import aiproj.slider.SliderPlayer;
import aiproj.slider.Move;

public class Human implements SliderPlayer{
	
	protected int[] board;

	@Override
	public void init(int dimension, String board, char player) {
		// TODO Get board from string, store it as int arr
		String[] rows = board.replaceAll(" ", "").split("\n");
		this.board = new int[dimension];
		
	}

	@Override
	public void update(Move move) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Move move() {
		// TODO Auto-generated method stub
		return null;
	}

}
