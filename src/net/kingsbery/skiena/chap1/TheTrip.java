package net.kingsbery.skiena.chap1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TheTrip {

	public static int counter=0;
	
	public static void main(String args[]) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(new File("prob1.6.3.txt"))));
		while(theTrip(reader));
	}

	private static boolean theTrip(BufferedReader reader) throws IOException {
		
		int studentCount= Integer.parseInt(reader.readLine());
		if(0==studentCount){
			return false;
		}

		List<Integer> paid = new ArrayList<Integer>();
		for (int i = 0; i < studentCount; i++) {
			String line[] = reader.readLine().split("[.]");
			
			paid.add(Integer.parseInt(line[0])*100+Integer.parseInt(line[1]));
		}
		int average=average(paid);
		double exchanged=0.0;
		for(double d:paid){
			exchanged+= d<average?average-d:0;
		}
		//TODO formatting
		System.out.println("$"+(exchanged/100.0));
		return true;
	}

	private static int average(List<Integer> paid) {
		int total=0;
		for(double d:paid){
			total+=d;
		}
		return total/paid.size();
	}


}
