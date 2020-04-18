package ca.edtoaster.mixin;

import ca.edtoaster.Utils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.*;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Block.class)
public class BeehiveItemMixin {

    /**
     * Injects code at the end of the block build tooltip function.
     * @param stack the ItemStack to display
     * @param tooltip the list of tooltip texts to append to
     * @param options the tooltip options, used to determine if we are in advanced mode
     */
    @Environment(EnvType.CLIENT)
    @Inject(at = @At("TAIL"), method = "buildTooltip")
    private void buildTooltip(ItemStack stack, BlockView view, List<Text> tooltip, TooltipContext options, CallbackInfo info) {
        if (!options.isAdvanced()) return;

        Item i = stack.getItem();
        if (!(i instanceof BlockItem)) return;
        BlockItem blockItem = (BlockItem) i;
        Block block = blockItem.getBlock();
        if (!(block instanceof BeehiveBlock)) return;

        int numBees = Utils.extractNumberOfBees(stack);

        tooltip.add(Utils.getBeeText(numBees));
    }
}
