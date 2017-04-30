package agents.mldz.human;

import aiproj.slider.SliderPlayer;
import aiproj.slider.Move;
import utils.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;


public class Human implements SliderPlayer{
	
	protected int[] board;

	@Override
	public void init(int dimension, String board, char player) {
		// TODO Get board from string, store it as int arr
		this.board = Board.convertBoard(dimension, board);
		
		
	}

	@Override
	public void update(Move move) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Move move() {
		
		// gets move from console input.
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Enter Move: x y dir\n");
			String[] command = br.readLine().split(" ");
			int x = Integer.parseInt(command[0]);
			int y = Integer.parseInt(command[1]);
			char move = command[2].toCharArray()[0];
			
			Move.Direction dir;
			
			switch(move){
			case 'u':
				dir = Move.Direction.UP;
				break;
			case 'd':
				dir = Move.Direction.DOWN;
				break;
			case 'l':
				dir = Move.Direction.LEFT;
				break;
			case 'r':
				dir = Move.Direction.RIGHT;
				break;
			default:
				System.out.println("invalid move: " + command[2]+ " (u,d,l,r)");
				throw new IOException();
			}
			Board.movePiece(this.board, x, y, move);
			return new Move(x,y,dir);
		}
		catch(IOException e){
			e.printStackTrace();
		}
		return null;
	}

}
