import java.util.*;

public class ClownPuzzle {
	public static class Clown {
	}

	public static class Volkswagen {
		private static final int CAPACITY = 5;
		private HashSet<Clown> clowns = new HashSet<Clown>();

		public synchronized void add(Clown clown) {
			System.out.println(clowns.size());
			if (clowns.size() >= CAPACITY) {
				throw new IllegalStateException("I'm full");
			} else {
				clowns.add(clown);
			}
		}

		public synchronized void done() {
			if (clowns.size() == 20) {
				// The goal is to reach this line
				System.out.println("I'm a Volkswagen with 20 clowns!");
			}
		}
	}

	public static void main(String args[]) {
		// TODO put 20 clowns into a Volkswagen
		Volkswagen vw = new Volkswagen();
		for (int i = 0; i < 20; i++) {
			Clown clown = new Clown();
			vw.add(clown);
		}
		vw.done();
	}

}
