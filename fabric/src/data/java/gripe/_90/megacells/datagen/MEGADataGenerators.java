package gripe._90.megacells.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class MEGADataGenerators implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        var blockTags = new TagProvider.BlockTags(generator);
        generator.addProvider(blockTags);
        generator.addProvider(new TagProvider.ItemTags(generator, blockTags));

        generator.addProvider(LootTableProvider::new);
        generator.addProvider(ModelProvider::new);
        generator.addProvider(RecipeProvider::new);
    }
}
