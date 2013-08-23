package net.kingsbery.skiena.chap1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class LiquidCrystalDisplay {
	public static void main(String args[]) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(new File("prob1.6.4.txt"))));
		String str;
		while (!(str = reader.readLine()).trim().equals( "0 0")) {
			String splits[] = str.split(" ");
			int size = Integer.parseInt(splits[0]);
			String number = splits[1];			
			new LiquidCrystalDisplay(number, size).printDisplay();
		}
	}

	private String number;
	private int digitSize;

	private void printDisplay() {
		printDisplay(this.display);
	}

	public LiquidCrystalDisplay(String number, int digitSize) {
		this.number = number;
		this.digitSize = digitSize;
		displayHeight = 2 * digitSize + 3;
		displayWidth = (digitSize + 2) * number.length() + number.length() - 1;
		display = new char[displayHeight][displayWidth];
		clear(display);
		for (int i = 0; i < number.length(); i++) {
			int x = Integer.parseInt(number.substring(i, i + 1));
			char[][] digit = getDisplay(digitSize, x);
			copyToDisplay(display, digit, i);
		}

	}

	private void copyToDisplay(char[][] display, char[][] digit, int i) {
		int startCol = (digitSize + 3) * i;
		for (int row = 0; row < displayHeight; row++) {
			for (int col = 0; col < digitSize + 2; col++) {
				display[row][startCol + col] = digit[row][col];
			}
		}
	}

	private static char[][] getDisplay(int size, int x) {
		char[][] lcd = new char[2 * size + 3][size + 2];
		clear(lcd);
		setDisplay(lcd, size, x);
		return lcd;
	}

	static boolean zero[] = { true, false, true, true, true, true, true };
	static boolean one[] = { false, false, false, false, false, true, true };
	static boolean two[] = { true, true, true, false, true, true, false };
	static boolean three[] = { true, true, true, false, false, true, true };
	static boolean four[] = { false, true, false, true, false, true, true };
	static boolean five[] = { true, true, true, true, false, false, true };
	static boolean six[] = { true, true, true, true, true, false, true };
	static boolean seven[] = { true, false, false, false, false, true, true };
	static boolean eight[] = { true, true, true, true, true, true, true };
	static boolean nine[] = { true, true, false, true, false, true, true };

	static boolean chars[][] = { zero, one, two, three, four, five, six, seven,
			eight, nine };
	private char[][] display;
	private int displayHeight;
	private int displayWidth;

	private static void setDisplay(char[][] lcd, int size, int i) {
		boolean bmp[] = chars[i];
		// top
		if (bmp[0]) {
			for (int col = 1; col < size + 1; col++) {
				lcd[0][col] = '-';
			}
		}
		// middle
		if (bmp[1]) {
			for (int col = 1; col < size + 1; col++) {
				lcd[size + 1][col] = '-';
			}
		}
		// bottom
		if (bmp[2]) {
			for (int col = 1; col < size + 1; col++) {
				lcd[(2 * size + 3) - 1][col] = '-';
			}
		}
		// top-left
		if (bmp[3]) {
			for (int row = 1; row < size + 1; row++) {
				lcd[row][0] = '|';
			}
		}
		// bottom-left
		if (bmp[4]) {
			for (int row = size + 2; row < (size + 2) + size; row++) {
				lcd[row][0] = '|';
			}
		}
		// top-right
		if (bmp[5]) {
			for (int row = 1; row < size + 1; row++) {
				lcd[row][size + 1] = '|';
			}
		}
		// bottom-right
		if (bmp[6]) {
			for (int row = size + 2; row < (size + 2) + size; row++) {
				lcd[row][size + 1] = '|';
			}
		}
	}

	private static void clear(char[][] field) {
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field[i].length; j++) {
				field[i][j] = ' ';
			}
		}
	}

	private static void printDisplay(char[][] field) {
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field[i].length; j++) {
				System.out.print(field[i][j]);
			}
			System.out.println();
		}
	}

}
