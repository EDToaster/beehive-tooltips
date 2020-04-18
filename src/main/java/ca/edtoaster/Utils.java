package ca.edtoaster;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class Utils {
    /**
     * Extracts the number of bees in the itemStack
     * Precondition: the itemstack is a Beehive blockItem
     *
     * @param stack the itemstack to display
     * @return the number of Bees. If there are no nbt tags in the
     * itemstack (For example, upon loading or getting the beehive from
     * the creative menu), then this method will return 0.
     */
    public static int extractNumberOfBees(ItemStack stack) {
        CompoundTag tag;
        if ((tag = stack.getTag()) == null) return 0;

        CompoundTag blockEntityTag = tag.getCompound("BlockEntityTag");
        ListTag beesTag = blockEntityTag.getList("Bees", 10);
        return beesTag.size();
    }

    public static Text getBeeText(int numBees) {
        return new LiteralText(numBees + " ")
                .setStyle(WHITE_STYLE)
                .append(BEES_TEXT);
    }

    public static final Style WHITE_STYLE = new Style().setColor(Formatting.WHITE);
    public static final Style YELLOW_STYLE = new Style().setColor(Formatting.YELLOW);
    public static final Text BEES_TEXT = new LiteralText("Bees").setStyle(YELLOW_STYLE);
}
