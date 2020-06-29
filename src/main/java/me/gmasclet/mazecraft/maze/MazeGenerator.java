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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * This class generates mazes using Wilson's algorithm.
 *
 * This algorithm generates unbiased mazes, that is to say there is no
 * distinctive pattern among generated mazes. It uses loop-erased random walks.
 */
public class MazeGenerator {

    private final Random random;

    public MazeGenerator() {
        this.random = new Random();
    }

    /**
     * Randomly creates a new maze.
     *
     * @param size The number of cells per side (has to be greater than 0)
     * @return A new maze instance
     */
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
            System.out.println(visited.size() + " / " + size * size);

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
