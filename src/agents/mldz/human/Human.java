package agents.mldz.human;

import aiproj.slider.Move;
import board.Board;
import utils.MOPS;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import agents.mldz.Interplay;


/** Human controlled agent 
 */
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
			case 'f':
				dir = MOPS.forward(this.me);
				break;
			case 'l':
				dir = MOPS.left(this.me);
				break;
			case 'r':
				dir = MOPS.right(this.me);
				break;
			default:
				System.out.println("invalid move: " + command[2]+ " (f,l,r) for forward left or right");
				throw new IOException();
			}
			
			// ahh we have a move
			// update board and return move
			this.board.movePiece(x, y, dir);
			return new Move(x,y,dir);
		}
		catch(IOException e){
			e.printStackTrace();
		}
		return null;
	}
	
	public void humaniseBoard(){
		
		
		String str = "";
		
		for(int y = this.board.getLen() - 1; y >= 0; --y){
			str += y + " ";
			for(int x = 0; x < this.board.getLen(); ++x){
				str += (Board.BLOCKS[this.board.tileAt(x, y)]);
				if(x != this.board.getLen()-1){
					str += (" ");
				}
			}
			str += "\n";
		}
		str += "x ";
		for(int f = 0; f < this.board.getLen(); ++f){
			str += f + " ";
		}
		str += "\n";
		
		System.out.println(str);
	}
	
}
