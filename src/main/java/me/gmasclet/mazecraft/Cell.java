package me.gmasclet.mazecraft;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Cell {

    private Cell north;
    private Cell east;
    private Cell south;
    private Cell west;
    private final Set<Cell> links;

    public Cell() {
        this.links = new HashSet<>();
    }

    public void setEast(Cell east) {
        this.east = east;
        east.west = this;
    }

    public void setSouth(Cell south) {
        this.south = south;
        south.north = this;
    }

    public void link(Cell cell) {
        links.add(cell);
        cell.links.add(this);
    }

    public Cell getNorth() {
        return north;
    }

    public Cell getEast() {
        return east;
    }

    public Cell getSouth() {
        return south;
    }

    public Cell getWest() {
        return west;
    }

    public List<Cell> getNeighbors() {
        return Stream.of(north, east, south, west)
                .filter(x -> x != null)
                .collect(Collectors.toList());
    }

    public boolean isLinked(Cell cell) {
        return links.contains(cell);
    }
}
