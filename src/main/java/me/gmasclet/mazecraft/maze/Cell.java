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

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents a unit of the maze's grid.
 */
public class Cell {

    private Cell north;
    private Cell east;
    private Cell south;
    private Cell west;
    private final Set<Cell> links;

    Cell() {
        this.links = new HashSet<>();
    }

    void setEast(Cell east) {
        this.east = east;
        east.west = this;
    }

    void setSouth(Cell south) {
        this.south = south;
        south.north = this;
    }

    void link(Cell cell) {
        links.add(cell);
        cell.links.add(this);
    }

    /**
     * @return The north neighbor of this cell
     */
    public Cell getNorth() {
        return north;
    }

    /**
     * @return The east neighbor of this cell
     */
    public Cell getEast() {
        return east;
    }

    /**
     * @return The south neighbor of this cell
     */
    public Cell getSouth() {
        return south;
    }

    /**
     * @return The west neighbor of this cell
     */
    public Cell getWest() {
        return west;
    }

    /**
     * Lists all neighbors of this cell. A cell has 2, 3 or 4 neighbors
     * depending of it is in the corner, side or elsewhere respectively.
     *
     * @return A list of cells
     */
    public List<Cell> getNeighbors() {
        return Stream.of(north, east, south, west)
                .filter(x -> x != null)
                .collect(Collectors.toList());
    }

    /**
     * Lists all cells linked with this cell.
     *
     * @return A list of cells
     */
    public List<Cell> getLinks() {
        return getNeighbors().stream()
                .filter(this::isLinked)
                .collect(Collectors.toList());
    }

    /**
     * Indicates if this cell is linked to the cell provided as argument, that
     * is to say if there is no wall between this cell and the other one. They
     * have to be neighbors, otherwise this method returns false.
     *
     * @param cell Another cell
     * @return true if this cell is linked to the other one
     */
    public boolean isLinked(Cell cell) {
        return links.contains(cell);
    }

    /**
     * Indicates if this cell is part of a "room" shape, that is to say a square
     * of four interconnected cells, spanning on north and west. Useful for
     * prettier maze rendering.
     *
     * @return true if this cell the south-east part of a room.
     */
    public boolean isRoomOnNorthWest() {
        return north != null
                && west != null
                && links.contains(north)
                && links.contains(west)
                && north.links.contains(north.west)
                && west.links.contains(west.north);
    }
}
