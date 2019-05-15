package com.cosmolev.sudoku;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class SudokuValidatorTest {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	private final PrintStream originalErr = System.err;

	@Before
	public void setUpStreams() {
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));
	}

	@After
	public void restoreStreams() {
		System.setOut(originalOut);
		System.setErr(originalErr);
	}

	@Test
	public void shouldPrint0GivenValidUnsolved() {
		// arrange
		String[] args = {"src/test/resources/valid_unsolved.csv"};

		// act
		SudokuValidator.main(args);

		// assert
		assertEquals("0\n", outContent.toString());
	}

	@Test
	public void shouldPrint0GivenValidSolved() {
		// arrange
		String[] args = {"src/test/resources/valid_solved.csv"};

		// act
		SudokuValidator.main(args);

		// assert
		assertEquals("0\n", outContent.toString());
	}

	@Test
	public void shouldPrint1GivenInvalidSolved() {
		// arrange
		String[] args = {"src/test/resources/invalid_solved.csv"};

		// act
		SudokuValidator.main(args);

		// assert
		assertEquals("-1\n", outContent.toString());
	}

	@Test
	public void shouldPrint0GivenValidSolvedOriginal() {
		// arrange
		String[] args = {"src/test/resources/valid_unsolved_original.txt"};

		// act
		SudokuValidator.main(args);

		// assert
		assertEquals("0\n", outContent.toString());
	}

	@Test
	public void shouldPrint1GivenInvalidUnsolvedWithBadRow() {
		// arrange
		String[] args = {"src/test/resources/invalid_unsolved_bad_column.csv"};

		// act
		SudokuValidator.main(args);

		// assert
		assertEquals("-1\n", outContent.toString());
	}

	@Test
	public void shouldPrint1GivenInvalidUnsolvedWithLargeNumber() {
		// arrange
		String[] args = {"src/test/resources/invalid_unsolved_large_number.csv"};

		// act
		SudokuValidator.main(args);

		// assert
		assertEquals("-1 not a correct line: 10,3,9,1,0,0,7,0,0\n", outContent.toString());
	}

	@Test
	public void shouldPrint1GivenInvalidUnsolvedWithTooManyRows() {
		// arrange
		String[] args = {"src/test/resources/invalid_unsolved_too_many_rows.csv"};

		// act
		SudokuValidator.main(args);

		// assert
		assertEquals("-1 too many lines\n", outContent.toString());
	}

	@Test
	public void shouldPrint1GivenInvalidUnsolvedWithNotEnoughRows() {
		// arrange
		String[] args = {"src/test/resources/invalid_unsolved_not_enough_rows.csv"};

		// act
		SudokuValidator.main(args);

		// assert
		assertEquals("-1 not enough lines\n", outContent.toString());
	}

	@Test
	public void shouldPrint1GivenInvalidUnsolvedWithTooManyColumns() {
		// arrange
		String[] args = {"src/test/resources/invalid_unsolved_too_many_columns.csv"};

		// act
		SudokuValidator.main(args);

		// assert
		assertEquals("-1 not a correct line: 2,3,9,1,0,0,7,0,0,0\n", outContent.toString());
	}

	@Test
	public void shouldPrint1GivenInvalidUnsolvedWithNotEnoughColumns() {
		// arrange
		String[] args = {"src/test/resources/invalid_unsolved_not_enough_columns.csv"};

		// act
		SudokuValidator.main(args);

		// assert
		assertEquals("-1 not a correct line: 2,3,9,1,0,0,7,0\n", outContent.toString());
	}

	@Test
	public void shouldPrint1GivenInvalidUnsolvedWithBadSymbol() {
		// arrange
		String[] args = {"src/test/resources/invalid_unsolved_bad_symbol.csv"};

		// act
		SudokuValidator.main(args);

		// assert
		assertEquals("-1 not a correct line: 0,0,0,6,a,1,5,0,0\n", outContent.toString());
	}

	@Test
	public void shouldPrint1GivenNonExistentFile() {
		// arrange
		String[] args = {"src/test/resources/non_existent_file.csv"};

		// act
		SudokuValidator.main(args);

		// assert
		assertEquals("-1 can not read file\n", outContent.toString());
	}

}
