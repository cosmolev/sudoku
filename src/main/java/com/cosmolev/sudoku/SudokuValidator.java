package com.cosmolev.sudoku;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SudokuValidator {
	private static String linePattern = "(\\d?),(\\d?),(\\d?),(\\d?),(\\d?),(\\d?),(\\d?),(\\d?),(\\d?)";
	private static Pattern patLine = Pattern.compile(linePattern);
	private final int[][] grid = new int[9][9];

	{
		for (int[] row : grid)
			Arrays.fill(row, -1);
	}

	private void readLine(String line, int lineNumber) {
		Matcher matcher = patLine.matcher(line);
		if (!matcher.matches()) {
			throw new SudokuException("not a correct line: " + line);
		}

		for (int i = 0; i < 9; i++) {
			String cell = matcher.group(i + 1);
			if (cell.trim().length() > 0) {
				int value = Integer.parseInt(cell);
				grid[lineNumber][i] = value;
			}
		}
	}

	private void readBuffer(BufferedReader r) throws IOException {
		String line;
		int lineCount = -1;
		while ((line = r.readLine()) != null) {
			lineCount++;
			if (lineCount > 8) {
				throw new SudokuException("too many lines");
			} else {
				readLine(line, lineCount);
			}
		}
		if (lineCount < 8) throw new SudokuException("not enough lines");
	}

	private void loadFile(File file) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		readBuffer(br);
	}

	private String status() {
		DancingLinksSolver dla = new DancingLinksSolver();
		boolean isValid = dla.solve(grid);
		if (isValid) {
			return "0";
		} else {
			return "-1";
		}
	}

	public static void main(String[] args) {
		for (String fileName : args) {
			File file = new File(fileName);
			if(!file.exists() || !file.isFile() || !file.canRead()) {
				System.out.println("-1 can not read file");
				return;
			}
			SudokuValidator grid = new SudokuValidator();
			try {
				grid.loadFile(file);
				System.out.println(grid.status());
			} catch (IOException e) {
				System.out.println("-1 invalid file");
			} catch (SudokuException e) {
				System.out.println("-1 " + e.getMessage());
			}
		}
	}


	private static class SudokuException extends RuntimeException {
		SudokuException(String message) {
			super(message);
		}
	}
}
