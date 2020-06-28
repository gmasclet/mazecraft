package me.gmasclet.mazecraft;

public class Maze {

    private final Cell[][] cells;

    public Maze(int size) {
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

    public int getSize() {
        return cells.length;
    }

    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (int y = 0; y < cells.length; y++) {
            for (int x = 0; x < cells.length; x++) {
                Cell cell = cells[x][y];
                builder.append("+");
                if (y == 0 || !cell.isLinked(cell.getNorth())) {
                    builder.append("---");
                } else {
                    builder.append("   ");
                }
            }
            builder.append("+");
            builder.append(System.lineSeparator());

            for (int x = 0; x < cells.length; x++) {
                Cell cell = cells[x][y];

                if (x == 0 || !cell.isLinked(cell.getWest())) {
                    builder.append("|");
                } else {
                    builder.append(" ");
                }
                builder.append("   ");
            }
            builder.append("|");
            builder.append(System.lineSeparator());
        }

        for (int x = 0; x < cells.length; x++) {
            builder.append("+");
            builder.append("---");
        }
        builder.append("+");
        builder.append(System.lineSeparator());

        return builder.toString();
    }
}
