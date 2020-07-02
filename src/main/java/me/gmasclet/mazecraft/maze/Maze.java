/*
 * Copyright 2020 Guillaume Masclet <guillaume.masclet@yahoo.fr>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.gmasclet.mazecraft.maze;

/**
 * Represents a maze as a square grid of cells, namely a square divided as n x n
 * cells.
 */
public class Maze {

    private final Cell[][] cells;

    Maze(int size) {
        cells = new Cell[size][size];
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                cells[x][y] = new Cell();
            }
        }

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (x < size - 1) {
                    cells[x][y].setEast(cells[x + 1][y]);
                }
                if (y < size - 1) {
                    cells[x][y].setSouth(cells[x][y + 1]);
                }
            }
        }

    }

    /**
     * @return The number of cells per side of the grid
     */
    public int getSize() {
        return cells.length;
    }

    /**
     * @param x The X coordinate, between 0 and getSize() -1
     * @param y The Y coordinate, between 0 and getSize() -1
     * @return The cell at position (x, y)
     */
    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (int y = 0; y < cells.length; y++) {
            for (int x = 0; x < cells.length; x++) {
                Cell cell = cells[x][y];
                builder.append(cell.isRoomOnNorthWest() ? " " : "+")
                        .append(cell.isLinked(cell.getNorth()) ? "   " : "---");
            }
            builder.append("+").append(System.lineSeparator());

            for (int x = 0; x < cells.length; x++) {
                Cell cell = cells[x][y];
                builder.append(cell.isLinked(cell.getWest()) ? " " : "|").append("   ");
            }
            builder.append("|").append(System.lineSeparator());
        }

        for (int x = 0; x < cells.length; x++) {
            builder.append("+---");
        }
        return builder.append("+")
                .append(System.lineSeparator())
                .toString();
    }
}
