package ca.edtoaster.mixin;

import ca.edtoaster.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
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
        System.out.println("used");

        if (!context.getWorld().isClient()) return;

        System.out.println("is client");

        World w = context.getWorld();
        BlockPos pos = context.getBlockPos();
        BlockState state = w.getBlockState(pos);
        Block block = state.getBlock();
        ItemStack stack = block.getPickStack(w, pos, state);

        PlayerEntity p = context.getPlayer();
        if (p == null) return;

        System.out.println("Player valid");
        System.out.println(stack.toString());

//        Utils.extractBeeData(stack).ifPresent(numBees -> {
//            p.addChatMessage(Utils.getBeeText(numBees), true);
//            System.out.println("Found " + numBees + " bees");
//        });
    }
}
