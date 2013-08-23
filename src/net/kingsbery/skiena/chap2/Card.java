package net.kingsbery.skiena.chap2;

import java.util.ArrayList;
import java.util.List;




public class Card implements Comparable<Card>{

	public static String values="23456789TJQKA";
	
	public static List<Card> getDeck(){
		List<Card> result = new ArrayList<Card>();
		for(Suit suit : Suit.values()){
			for(char c : Card.values.toCharArray()){
				result.add(new Card(suit,c));				
			}
		}
		return result;
	}
	
	public Card(Suit suit, char c) {
		this.suit=suit;
		this.value=c;
	}
	public Suit suit;
	public char value;
	
	public String toString(){
		return suit.toString()+Character.toString(value);
	}

	@Override
	public int compareTo(Card other) {
		return values.indexOf(value)-values.indexOf(other.value);
	}
	
}