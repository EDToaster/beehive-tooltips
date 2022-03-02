package ca.edtoaster.beehivetooltips.client;

import ca.edtoaster.beehivetooltips.Utils;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;

@net.fabricmc.api.Environment(net.fabricmc.api.EnvType.CLIENT)
public class BeehiveTooltipsFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        System.out.println("Finally, something tasty ...");
        ItemTooltipCallback.EVENT.register((stack, context, lines) -> {
            Utils.buildBeehiveTooltip(stack, lines, context);
        });
    }
}