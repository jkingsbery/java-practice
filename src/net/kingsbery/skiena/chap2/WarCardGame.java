package net.kingsbery.skiena.chap2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WarCardGame {

	
	
	public static void main(String args[]){
		List<Integer> results = new ArrayList<Integer>();
		for(int i=0; i<1000; i++){
			int counter = playWar();
			System.out.println(counter);
			results.add(counter);
		}
	}

	private static int playWar() {
		List<Card> deck=Card.getDeck();
		Collections.shuffle(deck);
		List<Card> player1 = new ArrayList<Card>(26);
		player1.addAll(deck.subList(0, deck.size()/2));
		List<Card> player2 = new ArrayList<Card>(26);
		player2.addAll(deck.subList(deck.size()/2, deck.size()));
		int counter=0;
		while(!(player1.isEmpty() || player2.isEmpty())){
			counter++;
			war(player1,player2,new ArrayList<Card>());
		}
		return counter;
	}
	
	public static void war(List<Card> player1,List<Card> player2,List<Card> carryOver){
		if(player2.isEmpty()){
			player1.addAll(carryOver);
			return;
		}else if(player1.isEmpty()){
			player2.addAll(carryOver);
			return;
		}
		Card x = player1.get(0);
		Card y = player2.get(0);
		if(x.compareTo(y)>0){
			player1.remove(0);
			player2.remove(0);
			player1.add(x);
			player1.add(y);
			player1.addAll(carryOver);
		}else if(x.compareTo(y)<0|| (player2.isEmpty())){
			player1.remove(0);
			player2.remove(0);
			player2.add(y);
			player2.add(x);
			player2.addAll(carryOver);
		}
		else{
			carryOver.add(x);
			player1.remove(0);
			carryOver.add(y);
			player2.remove(0);
			for(int i=0; i<Math.min(3, player1.size()-1);i++){
				carryOver.add(player1.get(0));
				player1.remove(0);
			}
			for(int i=0; i<Math.min(3, player2.size()-1);i++){
				carryOver.add(player2.get(0));
				player2.remove(0);
			}
			war(player1,player2,carryOver);
		}
	}
}
