package ca.edtoaster;

import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
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

        String honeyLevel = blockStateTag.getString("honey_level");
        return Optional.of(new BeeData(numBees, adults, babies, honeyLevel));
    }

    public static Text getBeeText(int numBees, int numAdults, int numBabies) {
        return new LiteralText("Bees: ").setStyle(YELLOW_STYLE).append(new LiteralText(String.format("%d (%d:%d)", numBees, numAdults, numBabies)).setStyle(WHITE_STYLE));
    }

    public static Text getHoneyText(String honeyLevel) {
        return new LiteralText("Honey Level: ").setStyle(YELLOW_STYLE).append(new LiteralText(String.format("%s", honeyLevel)).setStyle(WHITE_STYLE));
    }

    public static Text getUnplacedText() {
        return new LiteralText("Unplaced").setStyle(INVALID_STYLE);
    }

    public static final Style WHITE_STYLE = Style.EMPTY.withColor(Formatting.WHITE);
    public static final Style YELLOW_STYLE = Style.EMPTY.withColor(Formatting.YELLOW);
    public static final Style INVALID_STYLE = Style.EMPTY.withItalic(true).withColor(Formatting.GRAY);
}
