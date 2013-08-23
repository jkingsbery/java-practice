package net.kingsbery.skiena.chap2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Deal Cards to Four Players
 * A Game consists of several hands
 * A Hand consists of several tricks
 * For now, Players aren't very good, they just pick cards at Random
 * For each trick, figure out who lost the trick. That person leads the next trick, and accumulates any penalty points
 * @author jkingsbery
 *
 */

public class HeartsCardGame {

	public static Random rand = new Random(1);
	
	public static class Player {
		int handScore=0;
		List<Card> hand;
		
		public List<Card> validPlays(Card leadCard){
			ArrayList<Card> result = new ArrayList<Card>();
			if(leadCard==null || !handContainsSuit(leadCard.suit)){
				result.addAll(hand);
			}else{
				result.addAll(cardsOfSuit(leadCard.suit));
			}
			return result;
		}
		
		private Collection<? extends Card> cardsOfSuit(Suit suit) {
			ArrayList<Card> result = new ArrayList<Card>();
			for(Card c : hand){
				if(c.suit==suit){
					result.add(c);
				}
			}
			return result;
		}

		private boolean handContainsSuit(Suit suit) {
			return !cardsOfSuit(suit).isEmpty();
		}

		public Card getCard(Card leadCard){
			//todo presumably there should be some strategy?
			List<Card> valid = validPlays(leadCard);
			Card card = valid.get(rand.nextInt(valid.size()));
			hand.remove(card);
			return card;
		}
		
	}

	List<Player> players = new ArrayList<Player>();
	int dealerIndex=0;
	int leadTrick=(dealerIndex+1)%4;
	
	public HeartsCardGame() {
		init();
	}

	public void hand() {
		List<Card> deck = Card.getDeck();
		Collections.shuffle(deck);
		for (int i = 0; i < 4; i++) {
			players.get(i).hand = new ArrayList<Card>();
			players.get(i).hand.addAll(deck.subList(13 * i, 13 * (i + 1)));
		}
		
		for(int i=0; i<13; i++){
			System.out.println("================================================");
			trick();
			for(Player p : players){
				System.out.println(p.hand);
			}
		}
		for(Player p : players){
			System.out.println(p.handScore);
		}
	}

	public void trick(){
		Map<Integer,Card> followedSuitCards = new HashMap<Integer,Card>();
		Set<Card> playedCards = new HashSet<Card>();
		Card leadCard = players.get(leadTrick).getCard(null);
		followedSuitCards.put(leadTrick,leadCard);
		for(int i=1; i<4; i++){
			int playerIndex = (i+leadTrick)%4;
			Card card = players.get(playerIndex).getCard(leadCard);
			if(card.suit==leadCard.suit){
				followedSuitCards.put(playerIndex,card);
			}
			playedCards.add(card);
		}
		int index = getLoser(followedSuitCards);
		leadTrick=index;
		players.get(index).handScore+=getPenalty(playedCards);
		
	}
	
	private int getPenalty(Collection<Card> values) {
		int penalty=0;
		for(Card c : values){
			if(c.suit==Suit.HEARTS){
				penalty++;
			}else if(c.suit==Suit.SPADES && c.value=='Q'){
				penalty+=13;
			}
		}
		return penalty;
	}

	private int getLoser(Map<Integer, Card> playedCards) {
		Card max=null;
		int loser=0;
		for(Integer player : playedCards.keySet()){
			if(max==null || playedCards.get(player).compareTo(max)>0){
				max=playedCards.get(player);
				loser=player;
			}
		}
		return loser;
	}

	private void init() {
		players.add(new Player());
		players.add(new Player());
		players.add(new Player());
		players.add(new Player());
	}

	public static void main(String args[]) {
		HeartsCardGame game = new HeartsCardGame();
		game.hand();
		
	}

}
