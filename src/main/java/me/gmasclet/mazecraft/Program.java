package me.gmasclet.mazecraft;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import net.morbz.minecraft.blocks.SimpleBlock;
import net.morbz.minecraft.blocks.StoneBlock;
import net.morbz.minecraft.level.FlatGenerator;
import net.morbz.minecraft.level.GameType;
import net.morbz.minecraft.level.IGenerator;
import net.morbz.minecraft.level.Level;
import net.morbz.minecraft.world.DefaultLayers;
import net.morbz.minecraft.world.World;

public class Program {

    public static void main(String[] args) {

        DefaultLayers layers = new DefaultLayers();
        IGenerator generator = new FlatGenerator(layers);

        Level level = new Level("Mazecraft-" + Instant.now().getEpochSecond(), generator);
        level.setGameType(GameType.CREATIVE);
        level.setSpawnPoint(-3, 1, -3);

        Maze maze = new MazeGenerator().generate(20);

        World world = new World(level, layers);
        createArena(world, -5, maze.getSize() * 3 + 6);
        setBlocks(world, maze);

        try {
            world.save();

            Files.move(
                    Paths.get("worlds", level.getLevelName()),
                    Paths.get(System.getProperty("user.home"), ".minecraft", "saves", level.getLevelName()));

        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
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
