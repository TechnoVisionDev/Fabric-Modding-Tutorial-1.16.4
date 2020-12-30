package com.technovision.tutorial;

import com.technovision.tutorial.registry.ModBlocks;
import com.technovision.tutorial.registry.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.ConstantLootTableRange;
import net.minecraft.loot.UniformLootTableRange;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LootTableEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.util.Identifier;

public class Tutorial implements ModInitializer {

    //Mod ID
    public static final String MOD_ID = "tutorial";

    //Item Groups
    public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(
            new Identifier(MOD_ID, "general"),
            () -> new ItemStack(ModItems.RUBY));

    public static final ItemGroup OTHER_GROUP = FabricItemGroupBuilder.create(
            new Identifier(MOD_ID, "other"))
            .icon(() -> new ItemStack(Blocks.ENCHANTING_TABLE))
            .appendItems(stacks -> {
                stacks.add(new ItemStack(ModBlocks.RUBY_BLOCK));
                stacks.add(new ItemStack(Items.APPLE));
                stacks.add(new ItemStack(ModItems.RUBY));
                stacks.add(new ItemStack(Items.GLOWSTONE_DUST));
            }).build();

    //Loot Tables
    private static final Identifier EMERALD_ORE_LOOT_TABLE_ID = new Identifier("minecraft", "blocks/emerald_ore");
    private static final Identifier RUBY_BLOCK_LOOT_TABLE_ID = new Identifier(MOD_ID, "blocks/ruby_block");

    @Override
    public void onInitialize() {
        //Registration
        ModItems.registerItems();
        ModBlocks.registerItems();

        //Other
        modifyLootTables();
    }

    private void modifyLootTables() {
        LootTableLoadingCallback.EVENT.register((resourceManager, lootManager, id, supplier, setter) -> {
            //Only apply to emerald ore
            if (EMERALD_ORE_LOOT_TABLE_ID.equals(id)) {

                // Add individual drop
                FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                        .rolls(ConstantLootTableRange.create(1))
                        .with(ItemEntry.builder(Items.GOLD_NUGGET)) // Add individual drop
                        .withFunction(SetCountLootFunction.builder(UniformLootTableRange.between(1.0f, 4.0f)).build());
                supplier.withPool(poolBuilder.build());

                // Inject custom loot table
                FabricLootPoolBuilder poolBuilder2 = FabricLootPoolBuilder.builder()
                        .rolls(ConstantLootTableRange.create(1))
                        .with(LootTableEntry.builder(RUBY_BLOCK_LOOT_TABLE_ID));
                supplier.withPool(poolBuilder2.build());
            }
        });
    }
}
