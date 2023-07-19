package gripe._90.megacells.datagen;

import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import appeng.api.features.P2PTunnelAttunement;
import appeng.core.definitions.BlockDefinition;
import appeng.core.definitions.ItemDefinition;

import gripe._90.megacells.definition.MEGABlocks;
import gripe._90.megacells.definition.MEGAParts;
import gripe._90.megacells.definition.MEGATags;

abstract class TagProvider {
    static class Items extends FabricTagProvider.ItemTagProvider {
        Items(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registries, BlockTagProvider block) {
            super(output, registries, block);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
            tag(P2PTunnelAttunement.getAttunementTag(P2PTunnelAttunement.ENERGY_TUNNEL))
                    .add(getKey(MEGABlocks.MEGA_ENERGY_CELL));
            tag(MEGATags.MEGA_PATTERN_PROVIDER)
                    .add(getKey(MEGABlocks.MEGA_PATTERN_PROVIDER))
                    .add(getKey(MEGAParts.MEGA_PATTERN_PROVIDER));
        }

        private ResourceKey<Item> getKey(ItemDefinition<?> item) {
            return BuiltInRegistries.ITEM.getResourceKey(item.asItem()).orElse(null);
        }
    }

    static class Blocks extends FabricTagProvider.BlockTagProvider {
        Blocks(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registries) {
            super(output, registries);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
            MEGABlocks.getBlocks().forEach(block -> tag(BlockTags.MINEABLE_WITH_PICKAXE).add(getKey(block)));
        }

        private ResourceKey<Block> getKey(BlockDefinition<?> block) {
            return BuiltInRegistries.BLOCK.getResourceKey(block.block()).orElse(null);
        }
    }
}
