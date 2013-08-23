package net.kingsbery.skiena.chap1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Minesweeper {

	public static int counter=0;
	
	public static void main(String args[]) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(new File("prob1.6.2.txt"))));
		while(minesweep(reader));
	}

	private static boolean minesweep(BufferedReader reader) throws IOException {
		String[] first = reader.readLine().split(" ");
		int rows = Integer.parseInt(first[0]);
		int cols = Integer.parseInt(first[1]);
		if(0==rows && 0==cols){
			return false;
		}
		char[][] field = new char[rows][cols];

		for (int i = 0; i < rows; i++) {
			String line = reader.readLine();
			for (int j = 0; j < cols; j++) {
				field[i][j] = line.charAt(j);
			}
		}
		char[][] solved = solve(field);
		System.out.println("Field #" + ++counter + ":");
		printField(solved);
		return true;
	}

	private static char[][] solve(char[][] field) {
		int rows = field.length;
		int cols = field[0].length;
		char[][] solved = new char[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				solved[i][j] = '0';
			}
		}
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if ('*'==field[i][j]) {
					solved[i][j] = '*';
					incrementNeighbors(solved, i, j);
				}
			}
		}
		return solved;
	}

	private static void printField(char[][] field) {
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field[i].length; j++) {
				System.out.print(field[i][j]);
			}
			System.out.println();
		}
	}

	private static void incrementNeighbors(char[][] field, int row, int col) {
		int[][] coords = { { -1, -1 }, { -1, 0 }, { -1, 1 }, { 0, -1 },
				{ 0, 1 }, { 1, -1 }, { 1, 0 }, { 1, 1 } };
		for (int[] c : coords) {
			if (inbounds(row + c[0], col + c[1], field) && field[row + c[0]][ col + c[1]]!='*') {
				field[row + c[0]][col + c[1]]++;
			}
		}
	}

	private static boolean inbounds(int row, int col, char[][] field) {
		int rows = field.length;
		int cols = field[0].length;
		return row >= 0 && col >= 0 && row < rows && col < cols;
	}

}
