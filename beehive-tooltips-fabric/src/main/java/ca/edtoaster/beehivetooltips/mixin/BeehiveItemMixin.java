package ca.edtoaster.beehivetooltips.mixin;

import ca.edtoaster.beehivetooltips.Utils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Block.class)
public class BeehiveItemMixin {
    /**
     * Injects code at the end of the block build tooltip function.
     *
     * @param itemStack     the ItemStack to display
     * @param list          the list of tooltip texts to append to
     * @param tooltipFlag   the tooltip options, used to determine if we are in advanced mode
     */
    @Environment(EnvType.CLIENT)
    @Inject(at = @At("TAIL"), method = "appendHoverText")
    private void buildTooltip(ItemStack itemStack, @Nullable BlockGetter blockGetter, List<Component> list, TooltipFlag tooltipFlag, CallbackInfo info) {
        Utils.buildBeehiveTooltip(itemStack, list, tooltipFlag);
    }
}