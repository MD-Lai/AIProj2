package agents.mldz.human;

import aiproj.slider.Move;
import board.Board;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import agents.mldz.Interplay;

import java.io.IOException;


public class Human extends Interplay{
	

	@Override
	public void update(Move move) {
		// TODO update based on opponent's move;
		if(move != null){
			this.board.movePiece(move.i, move.j, move.d);
			System.out.println(this.op + " " + move.toString());
		}
	}

	@Override
	public Move move() {
		// so the petty human knows what his coords are
		humaniseBoard();
		
		// gets move from console input.
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Enter Move " + this.me + ": x y dir\n");
			String line = br.readLine();
			
			if(line.equals("null")){
				return null;
			}
			String[] command = line.split(" ");
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
			board.movePiece(x, y, dir);
			return new Move(x,y,dir);
		}
		catch(IOException e){
			e.printStackTrace();
		}
		return null;
	}
	
	public void humaniseBoard(){
		byte[][] b = this.board.getTiles();
		
		String str = "";
		
		for(int y = b.length - 1; y >= 0; --y){
			str += y + " ";
			for(int x = 0; x < b.length; ++x){
				str += (Board.BLOCKS[b[x][y]]);
				if(x != b.length-1){
					str += (" ");
				}
			}
			str += "\n";
		}
		str += "x ";
		for(int f = 0; f < b.length; ++f){
			str += f + " ";
		}
		str += "\n";
		
		System.out.println(str);
	}
	
}
