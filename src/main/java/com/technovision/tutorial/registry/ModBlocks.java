package com.technovision.tutorial.registry;

import com.technovision.tutorial.Tutorial;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlocks {

    // Hardness: Stone (1.5f), Obsidian (50.0f), etc...
    // Mining Levels: Wood (0), Stone (1), Iron (2), Diamond (3), Netherite (4)
    public static final Block RUBY_BLOCK = new Block(FabricBlockSettings
            .of(Material.METAL, MaterialColor.RED)
            .breakByTool(FabricToolTags.PICKAXES, 0)
            .requiresTool()
            .strength(5.0F, 6.0F)
            .sounds(BlockSoundGroup.METAL));

    public static void registerItems() {
        Registry.register(Registry.BLOCK, new Identifier(Tutorial.MOD_ID, "ruby_block"), RUBY_BLOCK);
    }
}
