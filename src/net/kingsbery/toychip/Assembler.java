package net.kingsbery.toychip;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Assembler {

	public static enum Type {
		REGISTER, LITERAL
	}

	public static class AssemblyTree {
		List<String> instructions = new ArrayList<String>();
		Map<String, String> symbols;

		public String assemblyCode() {
			StringBuffer result = new StringBuffer();
			for (String str : instructions) {
				codeLine(str, result);
			}
			return result.toString();
		}

		public void codeLine(String str, StringBuffer result) {
			System.out.println(str);
			String parts[] = str.toUpperCase().trim().split("\\s+");
			if (parts[0].equals("HALT")) {
				result.append("100\n");
			} else if (parts[0].equals("MOV")) {
				assert !parts[2].isEmpty();
				if (getType(parts[2]) == Type.LITERAL) {
					result.append("2" + getRegister(parts[1])
							+ getLiteral(parts[2]) + "\n");
				} else {
					result.append("5" + getRegister(parts[1])
							+ getRegister(parts[2]) + "\n");
				}
			} else if (parts[0].equals("ADD")) {
				if (getType(parts[2]) == Type.LITERAL) {
					result.append("3" + getRegister(parts[1])
							+ getLiteral(parts[2]) + "\n");
				} else {
					result.append("6" + getRegister(parts[1])
							+ getRegister(parts[2]) + "\n");
				}
			} else if (parts[0].equals("MUL")) {
				if (getType(parts[2]) == Type.LITERAL) {
					result.append("4" + getRegister(parts[1])
							+ getLiteral(parts[2]) + "\n");
				} else {
					result.append("7" + getRegister(parts[1])
							+ getRegister(parts[2]) + "\n");
				}
			} else if (parts[0].equals("LOAD")) {
				result.append("8" + getRegister(parts[1])
						+ getRegister(parts[2]) + "\n");
			} else if (parts[0].equals("STORE")) {
				result.append("9" + getRegister(parts[1])
						+ getRegister(parts[2]) + "\n");
			} else if (parts[0].equals("JZ")) {
				int a = getRegister(parts[1]);
				result.append("0" + a + getRegister(parts[2]) + "\n");
			}
		}
	}

	// TODO add a layer of indirection to first load the whole program so that
	// we can get symbols.
	public static void main(String args[]) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(new File("example.tsm"))));
		String str;
		// StringBuffer result = new StringBuffer();
		AssemblyTree tree = new AssemblyTree();
		boolean codeMode = true;
		while ((str = reader.readLine()) != null) {
			str = str.trim();
			if (codeMode) {
				interpretCode(str, tree);
			} else {
				// interpretData(str,tree);
			}
		}

		System.out.println(tree.assemblyCode());
		List<String> exec = new ArrayList<String>();
		for (String s : tree.assemblyCode().split("\n")) {
			exec.add(s);
		}
		Interpreter.handleCase(exec);
	}

	private static void interpretData(String str, StringBuffer result) {
		// TODO Auto-generated method stub

	}

	private static void interpretCode(String str, AssemblyTree tree) {
		if (!str.trim().isEmpty()) {
			tree.instructions.add(str);
		}

	}

	private static int getLiteral(String string) {
		return Integer.parseInt(string);
	}

	public static int getRegister(String input) {
		return Integer.valueOf(input.substring(1));
	}

	public static Type getType(String input) {
		if (input.startsWith("D")) {
			return Type.REGISTER;
		} else {
			return Type.LITERAL;
		}
	}
}
