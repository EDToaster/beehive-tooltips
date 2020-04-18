package ca.edtoaster.mixin;

import ca.edtoaster.Utils;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


/**
 * Currently doesn't work without Server side support
 * As such, it is disabled.
 */
@Mixin(Item.class)
public class BeehiveBlockMixin {

    @Inject(at = @At("HEAD"), method = "useOnBlock")
    private void useOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> info) {
        if (!context.getWorld().isClient()) return;

        BlockEntity e = context.getWorld().getBlockEntity(context.getBlockPos());
        if (!(e instanceof BeehiveBlockEntity)) return;

        BeehiveBlockEntity beehive = (BeehiveBlockEntity) e;
        PlayerEntity p = context.getPlayer();
        if (p == null) return;

        p.addChatMessage(Utils.getBeeText(beehive.getBeeCount()), true);
        p.addChatMessage(beehive.getBees().toText(), false);
    }
}
