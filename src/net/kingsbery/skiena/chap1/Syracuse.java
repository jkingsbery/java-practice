package net.kingsbery.skiena.chap1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Syracuse {

	public static void main(String args[]) throws Exception {
//		BufferedReader reader = new BufferedReader(new InputStreamReader(
//				new FileInputStream(new File("prob1.6.1.txt"))));
//		
//		String str;
//		while ((str = reader.readLine()) != null) {
//			String s[] = str.split(" ");
//			int first = Integer.parseInt(s[0]);
//			int second = Integer.parseInt(s[1]);
//			System.out.println(first+" " + second + " " + syracuseBetween(first,second));
//		}
		System.out.println(syracuse(10));
	}
	
	

	private static int syracuseBetween(int first, int second) {
		int best=syracuse(first);
		for(int i=first+1;i<=second; i++){
			int next=syracuse(i);
			if(next>best){
				best=next;
			}
		}
		return best;
	}

	private static int syracuse(int second) {
		if (second == 1) {
			return 1;
		} else if (second % 2 == 0) {
			return 1 + syracuse(second / 2);
		} else {
			return 1 + syracuse(3 * second + 1);
		}
	}

}
