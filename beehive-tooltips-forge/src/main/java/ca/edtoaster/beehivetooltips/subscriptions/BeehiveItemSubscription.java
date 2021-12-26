package ca.edtoaster.beehivetooltips.subscriptions;

import ca.edtoaster.beehivetooltips.Utils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
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
        List<ITextComponent> list = event.getToolTip();
        ITooltipFlag flags = event.getFlags();
        Utils.buildBeehiveTooltip(stack, list, flags);
    }
}
