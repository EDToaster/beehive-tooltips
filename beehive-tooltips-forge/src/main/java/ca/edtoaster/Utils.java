package ca.edtoaster;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.Block;

import java.util.Optional;

public class Utils {
    public static class BeeData {
        public final int numBees;
        public final int numAdults, numBabies;
        public final String honeyLevel;

        public BeeData(int numBees, int numAdults, int numBabies, String honeyLevel) {
            this.numBees = numBees;
            this.numAdults = numAdults;
            this.numBabies = numBabies;
            this.honeyLevel = honeyLevel;
        }
    }

    public static boolean isBeehive(ItemStack stack) {
        Item i = stack.getItem();
        if (!(i instanceof BlockItem blockItem)) return false;
        Block block = blockItem.getBlock();
        return block instanceof BeehiveBlock;
    }

    /**
     * Extracts the data in the itemStack
     * Precondition: the itemstack is a Beehive blockItem
     *
     * @param stack the itemstack to display
     * @return the beehive data. If there are no nbt tags in the
     * itemstack (For example, upon loading or getting the beehive from
     * the creative menu), then this method will return no data.
     */
    public static Optional<BeeData> extractBeeData(ItemStack stack) {
        CompoundTag tag;
        if ((tag = stack.getTag()) == null) return Optional.empty();

        CompoundTag blockEntityTag = tag.getCompound("BlockEntityTag");
        ListTag beesTag = blockEntityTag.getList("Bees", 10);

        int adults = 0, babies = 0;

        for (Tag bee : beesTag) {
            CompoundTag entityTag = ((CompoundTag) bee).getCompound("EntityData");
            int age = entityTag.getInt("Age");
            if (age >= 0) adults++;
            else babies++;
        }


        int numBees = adults + babies;

        CompoundTag blockStateTag = tag.getCompound("BlockStateTag");

        // if honeylevel is empty, it must be stored as an int https://bugs.mojang.com/browse/MC-179531
        String honeyLevel = blockStateTag.getString("honey_level");
        if (honeyLevel.isEmpty()) {
            honeyLevel = "" + blockStateTag.getInt("honey_level");
        }

        return Optional.of(new BeeData(numBees, adults, babies, honeyLevel));
    }

    public static MutableComponent getBeeText(int numBees, int numAdults) {
        return new TranslatableComponent("item.minecraft.beehive.bee_tooltip", numBees, numAdults).setStyle(WHITE_STYLE);
    }

    public static MutableComponent getHoneyText(String honeyLevel) {
        return new TranslatableComponent("item.minecraft.beehive.honey_tooltip", honeyLevel).setStyle(WHITE_STYLE);
    }

    public static MutableComponent getUnplacedText() {
        return new TranslatableComponent("item.minecraft.beehive.unplaced").setStyle(INVALID_STYLE);
    }

    public static final Style WHITE_STYLE = Style.EMPTY.withColor(ChatFormatting.WHITE);
    public static final Style INVALID_STYLE = Style.EMPTY.withItalic(true).withColor(ChatFormatting.GRAY);
}