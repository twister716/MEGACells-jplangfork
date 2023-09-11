package gripe._90.megacells.crafting;

import java.util.Objects;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import appeng.api.crafting.IPatternDetails;
import appeng.api.stacks.AEItemKey;
import appeng.api.stacks.AEKey;
import appeng.api.stacks.GenericStack;

public class DecompressionPattern implements IPatternDetails {
    private final AEItemKey definition;
    private final AEItemKey base;
    private final AEItemKey variant;
    private final int factor;
    private final boolean toCompress;

    public DecompressionPattern(ItemStack stack) {
        this(Objects.requireNonNull(AEItemKey.of(stack)));
    }

    public DecompressionPattern(AEItemKey definition) {
        this.definition = definition;
        var tag = Objects.requireNonNull(definition.getTag());

        base = DecompressionPatternEncoding.getBase(tag);
        variant = DecompressionPatternEncoding.getVariant(tag);
        factor = DecompressionPatternEncoding.getFactor(tag);
        toCompress = DecompressionPatternEncoding.getToCompress(tag);
    }

    @Override
    public AEItemKey getDefinition() {
        return definition;
    }

    @Override
    public IInput[] getInputs() {
        return new IInput[] {toCompress ? new Input(base, factor) : new Input(variant, 1)};
    }

    @Override
    public GenericStack[] getOutputs() {
        return new GenericStack[] {toCompress ? new GenericStack(variant, 1) : new GenericStack(base, factor)};
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null
                && obj.getClass() == getClass()
                && ((DecompressionPattern) obj).definition.equals(definition);
    }

    @Override
    public int hashCode() {
        return definition.hashCode();
    }

    private record Input(AEItemKey input, long multiplier) implements IInput {
        @Override
        public GenericStack[] getPossibleInputs() {
            return new GenericStack[] {new GenericStack(input, 1)};
        }

        @Override
        public long getMultiplier() {
            return multiplier;
        }

        @Override
        public boolean isValid(AEKey input, Level level) {
            return input.matches(getPossibleInputs()[0]);
        }

        @Override
        public AEKey getRemainingKey(AEKey template) {
            return null;
        }
    }
}
