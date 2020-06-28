package me.gmasclet.mazecraft;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import net.morbz.minecraft.blocks.SimpleBlock;
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
        level.setSpawnPoint(50, 1, 10);

        World world = new World(level, layers);
        createArena(world);

        Maze maze = new MazeGenerator().generate(20);
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

    private static void createArena(World world) {
        for (int x = 0; x < 100; x++) {
            for (int z = 0; z < 100; z++) {
                world.setBlock(x, 0, z, SimpleBlock.BEDROCK);
            }
        }

        for (int x = 1; x < 99; x++) {
            for (int z = 1; z < 99; z++) {
                world.setBlock(x, 1, z, SimpleBlock.GRASS);
            }
        }

        for (int x = 0; x < 100; x++) {
            for (int y = 1; y < 6; y++) {
                world.setBlock(x, y, 0, SimpleBlock.BEDROCK);
                world.setBlock(x, y, 99, SimpleBlock.BEDROCK);
            }
        }

        for (int z = 1; z < 99; z++) {
            for (int y = 1; y < 6; y++) {
                world.setBlock(0, y, z, SimpleBlock.BEDROCK);
                world.setBlock(99, y, z, SimpleBlock.BEDROCK);
            }
        }
    }

    private static void setBlocks(World world, Maze maze) {
        // TODO this is a stub
    }
}
