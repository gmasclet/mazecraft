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
package me.gmasclet.mazecraft;

import me.gmasclet.mazecraft.maze.Maze;
import me.gmasclet.mazecraft.maze.Cell;
import me.gmasclet.mazecraft.maze.MazeGenerator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import net.morbz.minecraft.blocks.SimpleBlock;
import net.morbz.minecraft.blocks.StoneBlock;
import net.morbz.minecraft.level.FlatGenerator;
import net.morbz.minecraft.level.GameType;
import net.morbz.minecraft.level.IGenerator;
import net.morbz.minecraft.level.Level;
import net.morbz.minecraft.world.DefaultLayers;
import net.morbz.minecraft.world.World;

/**
 * Generates a Minecraft map containing a ramdomly created maze. This map is
 * saved in Minecraft saves directory.
 */
public class Program {

    /**
     * Entry point.
     *
     * @param args Unused
     */
    public static void main(String[] args) {
        int size = promptForSize();
        String levelName = String.format(
                "Mazecraft %1$dx%1$d %2$s",
                size,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));

        Maze maze = new MazeGenerator().generate(size);

        World world = newWorld(levelName);
        createArena(world, -5, maze.getSize() * 3 + 6);
        setBlocks(world, maze);

        try {
            world.save();

            Files.move(
                    Paths.get("worlds", levelName),
                    Paths.get(System.getProperty("user.home"), ".minecraft", "saves", levelName));

        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }

    private static int promptForSize() {
        System.out.println("Select maze size:");
        Scanner scanner = new Scanner(System.in);
        return Math.max(scanner.nextInt(), 0);
    }

    private static World newWorld(String levelName) {
        DefaultLayers layers = new DefaultLayers();
        IGenerator generator = new FlatGenerator(layers);

        Level level = new Level(levelName, generator);
        level.setGameType(GameType.CREATIVE);
        level.setMapFeatures(false);
        level.setSpawnPoint(-3, 1, -3);

        return new World(level, layers);
    }

    private static void createArena(World world, int min, int max) {
        for (int x = min; x < max; x++) {
            for (int z = min; z < max; z++) {
                world.setBlock(x, 0, z, SimpleBlock.BEDROCK);
            }
        }

        for (int x = min + 1; x < max - 1; x++) {
            for (int z = min + 1; z < max - 1; z++) {
                world.setBlock(x, 1, z, StoneBlock.STONE);
            }
        }

        for (int x = min; x < max; x++) {
            for (int y = 1; y < 6; y++) {
                world.setBlock(x, y, min, SimpleBlock.BEDROCK);
                world.setBlock(x, y, max - 1, SimpleBlock.BEDROCK);
            }
        }

        for (int z = min + 1; z < max - 1; z++) {
            for (int y = 1; y < 6; y++) {
                world.setBlock(min, y, z, SimpleBlock.BEDROCK);
                world.setBlock(max - 1, y, z, SimpleBlock.BEDROCK);
            }
        }
    }

    private static void setBlocks(World world, Maze maze) {
        for (int x = 0; x < maze.getSize(); x++) {
            for (int y = 0; y < maze.getSize(); y++) {
                setPillar(world, 3 * x, 3 * y);

                Cell cell = maze.getCell(x, y);
                if (!cell.isLinked(cell.getNorth())) {
                    setPillar(world, 3 * x + 1, 3 * y);
                    setPillar(world, 3 * x + 2, 3 * y);
                }
                if (!(x == 0 && y == 0) && !cell.isLinked(cell.getWest())) {
                    setPillar(world, 3 * x, 3 * y + 1);
                    setPillar(world, 3 * x, 3 * y + 2);
                }
            }
        }

        for (int x = 0; x < maze.getSize(); x++) {
            setPillar(world, 3 * x, 3 * maze.getSize());
            setPillar(world, 3 * x + 1, 3 * maze.getSize());
            setPillar(world, 3 * x + 2, 3 * maze.getSize());
        }
        for (int y = 0; y < maze.getSize(); y++) {
            setPillar(world, 3 * maze.getSize(), 3 * y);
            if (y != maze.getSize() - 1) {
                setPillar(world, 3 * maze.getSize(), 3 * y + 1);
                setPillar(world, 3 * maze.getSize(), 3 * y + 2);
            }
        }
        setPillar(world, 3 * maze.getSize(), 3 * maze.getSize());
    }

    private static void setPillar(World world, int x, int z) {
        for (int y = 2; y < 5; y++) {
            world.setBlock(x, y, z, SimpleBlock.BRICK_BLOCK);
        }
    }
}
