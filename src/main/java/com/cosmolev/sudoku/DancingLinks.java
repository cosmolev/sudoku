package com.cosmolev.sudoku;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

class DancingLinks {

	private ColumnNode header;
	private List<DancingNode> answer;

	boolean isValid() {
		return valid;
	}

	private boolean valid = false;

	private void search(int k) {
		if (header.R == header) {
			// sudoku is solved if we are here
			valid = true;
		} else {
			ColumnNode c = selectColumnNodeHeuristic();
			c.cover();

			for (DancingNode r = c.D; r != c; r = r.D) {
				answer.add(r);

				for (DancingNode j = r.R; j != r; j = j.R) {
					j.C.cover();
				}

				search(k + 1);

				r = answer.remove(answer.size() - 1);
				c = r.C;

				for (DancingNode j = r.L; j != r; j = j.L) {
					j.C.uncover();
				}
			}
			c.uncover();
		}
	}

	private ColumnNode selectColumnNodeHeuristic() {
		int min = Integer.MAX_VALUE;
		ColumnNode ret = null;
		for (ColumnNode c = (ColumnNode) header.R; c != header; c = (ColumnNode) c.R) {
			if (c.size < min) {
				min = c.size;
				ret = c;
			}
		}
		return ret;
	}

	private ColumnNode makeDLXBoard(boolean[][] grid) {
		final int COLS = grid[0].length;

		ColumnNode headerNode = new ColumnNode();
		List<ColumnNode> columnNodes = new ArrayList<>();

		for (int i = 0; i < COLS; i++) {
			ColumnNode n = new ColumnNode();
			columnNodes.add(n);
			headerNode = (ColumnNode) headerNode.hookRight(n);
		}
		headerNode = headerNode.R.C;

		for (boolean[] aGrid : grid) {
			DancingNode prev = null;
			for (int j = 0; j < COLS; j++) {
				if (aGrid[j]) {
					ColumnNode col = columnNodes.get(j);
					DancingNode newNode = new DancingNode(col);
					if (prev == null) {
						prev = newNode;
					}
					col.U.hookDown(newNode);
					prev = prev.hookRight(newNode);
					col.size++;
				}
			}
		}

		headerNode.size = COLS;

		return headerNode;
	}

	DancingLinks(boolean[][] cover) {
		header = makeDLXBoard(cover);
	}

	void runSolver() {
		answer = new LinkedList<>();
		search(0);
	}

}
