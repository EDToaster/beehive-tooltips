package ca.edtoaster.beehivetooltips.client;

import net.fabricmc.api.ClientModInitializer;

@net.fabricmc.api.Environment(net.fabricmc.api.EnvType.CLIENT)
public class BeehiveTooltipsFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        System.out.println("Nothing to initialize here ...");
        System.out.println("Seriously why are you wasting your time");
    }
}