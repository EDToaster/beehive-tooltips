package ca.edtoaster.beehivetooltips.subscriptions;

import ca.edtoaster.Utils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Optional;

@Mod.EventBusSubscriber(modid = "beehivetooltips", bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class BeehiveItemSubscription {

    @SubscribeEvent
    public static void tooltip(ItemTooltipEvent event) {
        if (!event.getFlags().isAdvanced()) return;

        ItemStack stack = event.getItemStack();
        List<Component> tooltip = event.getToolTip();
        if (Utils.isBeehive(stack)) {
            Optional<Utils.BeeData> op = Utils.extractBeeData(stack);

            if (op.isPresent()) {
                Utils.BeeData data = op.get();
                tooltip.add(1, Utils.getBeeText(data.numBees, data.numAdults));
                tooltip.add(2, Utils.getHoneyText(data.honeyLevel));
            } else {
                // invalid beehive
                tooltip.add(1, Utils.getUnplacedText());
            }
        }
    }
}
