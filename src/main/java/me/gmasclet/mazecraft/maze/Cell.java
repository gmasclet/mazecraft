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
}
