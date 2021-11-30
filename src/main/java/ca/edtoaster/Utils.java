package ca.edtoaster;

import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

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
        if (!(i instanceof BlockItem)) return false;
        BlockItem blockItem = (BlockItem) i;
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
        NbtCompound tag;
        if ((tag = stack.getNbt()) == null) return Optional.empty();

        NbtCompound blockEntityTag = tag.getCompound("BlockEntityTag");
        NbtList beesTag = blockEntityTag.getList("Bees", 10);

        int adults = 0, babies = 0;

        for (NbtElement bee : beesTag) {
            NbtCompound entityTag = ((NbtCompound) bee).getCompound("EntityData");
            int age = entityTag.getInt("Age");
            if (age >= 0) adults++;
            else babies++;
        }


        int numBees = adults + babies;

        NbtCompound blockStateTag = tag.getCompound("BlockStateTag");

        // if honeylevel is empty, it must be stored as an int https://bugs.mojang.com/browse/MC-179531
        String honeyLevel = blockStateTag.getString("honey_level");
        if (honeyLevel.isEmpty()) {
            honeyLevel = "" + blockStateTag.getInt("honey_level");
        }

        return Optional.of(new BeeData(numBees, adults, babies, honeyLevel));
    }

    public static Text getBeeText(int numBees, int numAdults) {
        return new TranslatableText("item.minecraft.beehive.bee_prefix").setStyle(YELLOW_STYLE).append(
                new TranslatableText("item.minecraft.beehive.bee_tooltip", numBees, numAdults).setStyle(WHITE_STYLE));
    }

    public static Text getHoneyText(String honeyLevel) {
        return new TranslatableText("item.minecraft.beehive.honey_prefix").setStyle(YELLOW_STYLE).append(
                new TranslatableText("item.minecraft.beehive.honey_tooltip", honeyLevel).setStyle(WHITE_STYLE));
    }

    public static Text getUnplacedText() {
        return new TranslatableText("item.minecraft.beehive.unplaced").setStyle(INVALID_STYLE);
    }

    public static final Style WHITE_STYLE = Style.EMPTY.withColor(Formatting.WHITE);
    public static final Style YELLOW_STYLE = Style.EMPTY.withColor(Formatting.YELLOW);
    public static final Style INVALID_STYLE = Style.EMPTY.withItalic(true).withColor(Formatting.GRAY);
}