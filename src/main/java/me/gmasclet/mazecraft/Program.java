package me.gmasclet.mazecraft;

import java.io.IOException;
import net.morbz.minecraft.blocks.Material;
import net.morbz.minecraft.level.FlatGenerator;
import net.morbz.minecraft.level.GameType;
import net.morbz.minecraft.level.IGenerator;
import net.morbz.minecraft.level.Level;
import net.morbz.minecraft.world.DefaultLayers;
import net.morbz.minecraft.world.World;

public class Program {

    public static void main(String[] args) {

        DefaultLayers layers = new DefaultLayers();
        layers.setLayer(0, Material.BEDROCK);
        layers.setLayer(1, Material.GRASS);

        IGenerator generator = new FlatGenerator(layers);

        Level level = new Level("Mazecraft", generator);
        level.setGameType(GameType.CREATIVE);
        level.setSpawnPoint(0, 1, 0);

        World world = new World(level, layers);

        try {
            world.save();

        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }
}
