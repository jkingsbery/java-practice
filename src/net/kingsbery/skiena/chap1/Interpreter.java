package net.kingsbery.skiena.chap1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * See problem 1.6.6
 * 
 * @author jkingsbery
 * 
 */
public class Interpreter {
	/*
	 * 100 means halt 2dn means set register d to n (between 0 and 9) 3dn means
	 * add n to register d 4dn means multiply register d by n 5ds means set
	 * register d to the value of register s 6ds means add the value of register
	 * s to register d 7ds means multiply register d by the value of register s
	 * 8da means set register d to the value in RAM whose address is in register
	 * a 9sa means set the value in RAM whose address is in register a to that
	 * of register s 0ds means goto the location in register d unless register s
	 * contains 0
	 */

	public static void main(String args[]) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(new File("prob1.6.6.txt"))));
		int cases = Integer.parseInt(reader.readLine());
		reader.readLine();// blank;
		for (int i = 0; i < cases; i++) {
			handleCase(reader);
		}

	}

	public static void handleCase(List<String> result) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {

		Chip chip = new Chip();
		int instructions = 0;
		int ram = 0;
		for(String line:result) {
			chip.ram[ram] = Integer.parseInt(line);
			ram++;
		}

		while (!chip.halted()) {
			int instruction = chip.nextInstruction();
			int first = (instruction / 10) % 10;
			int second = instruction % 10;
			getMethod(chip, instruction / 100).invoke(chip, first, second);
			instructions++;
		}

		System.out.println();
		System.out.println(instructions);
	}

	public static void handleCase(BufferedReader reader) throws IOException,
			IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		String str;
		List<String> result=new ArrayList<String>();
		while((str=reader.readLine())!=null){
			result.add(str);
		}
		handleCase(result);
	}

	private static Method getMethod(Chip chip, int charAt) {
		for (Method method : chip.getClass().getDeclaredMethods()) {
			Command annotation = method.getAnnotation(Command.class);
			if (annotation != null && annotation.value() == charAt) {
				return method;
			}
		}
		throw new IllegalArgumentException("No instruction for " + charAt);
	}

	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	private static @interface Command {
		char value();
	}

	public static class Chip {
		int registers[] = new int[10];
		int ram[] = new int[1000];
		int pc = 0;
		private boolean halt = false;

		public Chip() {

		}

		public int nextInstruction() {
			int result = ram[pc];
			pc++;
			return result;
		}

		public boolean halted() {
			return this.halt;
		}

		@Command(1)
		public void halt(int a, int b) {
			this.halt = true;
		}

		@Command(2)
		void setRegister(int d, int n) {
			registers[d] = n;
		}

		@Command(3)
		void addConst(int d, int n) {
			registers[d] = (registers[d] + n) % 1000;
		}

		@Command(4)
		void mulConst(int d, int n) {
			registers[d] = (registers[d] * n) % 1000;
		}

		@Command(5)
		void copy(int d, int s) {
			registers[d] = registers[s];
		}

		@Command(6)
		void addRegister(int d, int s) {
			registers[d] = (registers[d] + registers[s]) % 1000;
		}

		@Command(7)
		void mulRegister(int d, int s) {
			registers[d] = (registers[d] * registers[s]) % 1000;
		}

		@Command(8)
		void load(int d, int a) {
			registers[d] = ram[registers[a]];
		}

		@Command(9)
		void store(int s, int a) {
			ram[registers[a]] = registers[s];
		}

		@Command(0)
		void jump(int d, int s) {
			if (registers[s] != 0) {
				pc = registers[d];
			}
		}

	}

}
