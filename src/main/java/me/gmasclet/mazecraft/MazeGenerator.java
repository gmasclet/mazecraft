package me.gmasclet.mazecraft;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class MazeGenerator {

    private final Random random;

    public MazeGenerator() {
        this.random = new Random();
    }

    public Maze generate(int size) {
        Maze maze = new Maze(size);

        Set<Cell> visited = new HashSet<>(size * size);
        List<Cell> unvisited = new ArrayList<>(size * size);
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (x == 0 && y == 0) {
                    visited.add(maze.getCell(x, y));
                } else {
                    unvisited.add(maze.getCell(x, y));
                }
            }
        }

        while (!unvisited.isEmpty()) {
            List<Cell> walk = new ArrayList<>();
            walk.add(getRandom(unvisited));
            Cell next = null;
            while (!visited.contains(next)) {
                next = getRandom(walk.get(walk.size() - 1).getNeighbors());
                if (walk.contains(next)) {
                    walk.subList(walk.indexOf(next) + 1, walk.size()).clear();
                } else {
                    walk.add(next);
                }
            }

            for (int i = 0; i < walk.size() - 1; i++) {
                Cell cell = walk.get(i);
                cell.link(walk.get(i + 1));
                visited.add(cell);
                unvisited.remove(cell);
            }
        }
        return maze;
    }

    private Cell getRandom(List<Cell> cells) {
        return cells.get(random.nextInt(cells.size()));
    }
}
