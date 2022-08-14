package ca.edtoaster.mixin;

import ca.edtoaster.Utils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.*;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Optional;

@Mixin(Block.class)
public class BeehiveItemMixin {
    /**
     * Injects code at the end of the block build tooltip function.
     *
     * @param stack   the ItemStack to display
     * @param tooltip the list of tooltip texts to append to
     * @param options the tooltip options, used to determine if we are in
     *                advanced mode
     */
    @Environment(EnvType.CLIENT)
    @Inject(at = @At("TAIL"), method = "appendTooltip")
    private void buildTooltip(
        ItemStack stack, BlockView view, List<Text> tooltip,
        TooltipContext options, CallbackInfo info
    ) {
        if (!options.isAdvanced()) return;

        if (Utils.isBeehive(stack)) {
            Optional<Utils.BeeData> op = Utils.extractBeeData(stack);

            if (op.isPresent()) {
                Utils.BeeData data = op.get();
                tooltip.add(Utils.getBeeText(data.numBees, data.numAdults,
                                             data.numBabies));
                tooltip.add(Utils.getHoneyText(data.honeyLevel));
            } else {
                // invalid beehive
                tooltip.add(Utils.getUnplacedText());
            }
        }
    }
}
