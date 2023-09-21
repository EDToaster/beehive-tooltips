package ca.edtoaster.beehivetooltips.subscriptions;

import ca.edtoaster.beehivetooltips.Utils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = "beehivetooltips", bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class BeehiveItemSubscription {

    @SubscribeEvent
    public static void tooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        List<Component> list = event.getToolTip();
        TooltipFlag flags = event.getFlags();
        Utils.buildBeehiveTooltip(stack, list, flags);
    }
}
