package ca.edtoaster.beehivetooltips;


import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.List;
import java.util.Optional;

public class Utils {
    public static class BeeData {
        public int numBees, numAdults, numBabies;
        public String honeyLevel;

        public BeeData(int numBees, int numAdults, int numBabies, String honeyLevel) {
            this.numBees = numBees;
            this.numAdults = numAdults;
            this.numBabies = numBabies;
            this.honeyLevel = honeyLevel;
        }
    }

    public static void buildBeehiveTooltip(ItemStack itemStack, List<ITextComponent> list, ITooltipFlag tooltipFlag) {
        if (!tooltipFlag.isAdvanced()) return;

        if (Utils.isBeehive(itemStack)) {
            Optional<BeeData> op = Utils.extractBeeData(itemStack);

            if (op.isPresent()) {
                BeeData data = op.get();
                list.add(1, Utils.getBeeText(data.numBees, data.numAdults));
                list.add(2, Utils.getHoneyText(data.honeyLevel));
            } else {
                // invalid beehive
                list.add(1, Utils.getUnplacedText());
            }
        }
    }

    private static boolean isBeehive(ItemStack stack) {
        Item i = stack.getItem();
        if (!(i instanceof BlockItem)) return false;
        Block block = ((BlockItem) i).getBlock();
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
    private static Optional<BeeData> extractBeeData(ItemStack stack) {
        CompoundNBT tag;
        if ((tag = stack.getTag()) == null) return Optional.empty();

        CompoundNBT blockEntityTag = tag.getCompound("BlockEntityTag");
        ListNBT beesTag = blockEntityTag.getList("Bees", 10);

        int adults = 0, babies = 0;

        for (INBT bee : beesTag) {
            CompoundNBT entityTag = ((CompoundNBT) bee).getCompound("EntityData");
            int age = entityTag.getInt("Age");
            if (age >= 0) adults++;
            else babies++;
        }


        int numBees = adults + babies;

        CompoundNBT blockStateTag = tag.getCompound("BlockStateTag");

        // if honeylevel is empty, it must be stored as an int https://bugs.mojang.com/browse/MC-179531
        String honeyLevel = blockStateTag.getString("honey_level");
        if (honeyLevel.isEmpty()) {
            honeyLevel = "" + blockStateTag.getInt("honey_level");
        }

        return Optional.of(new BeeData(numBees, adults, babies, honeyLevel));
    }

    private static ITextComponent getBeeText(int numBees, int numAdults) {
        return new TranslationTextComponent("item.minecraft.beehive.bee_tooltip", numBees, numAdults).setStyle(WHITE_STYLE);
    }

    private static ITextComponent getHoneyText(String honeyLevel) {
        return new TranslationTextComponent("item.minecraft.beehive.honey_tooltip", honeyLevel).setStyle(WHITE_STYLE);
    }

    private static ITextComponent getUnplacedText() {
        return new TranslationTextComponent("item.minecraft.beehive.unplaced").setStyle(INVALID_STYLE);
    }

    public static final Style WHITE_STYLE = Style.EMPTY.withColor(TextFormatting.WHITE);
    public static final Style INVALID_STYLE = Style.EMPTY.withItalic(true).withColor(TextFormatting.GRAY);
}