package com.example.examplemod.client;

import com.example.examplemod.platform.services.FabricNetworkHelper;
import net.fabricmc.api.ClientModInitializer;

public class FabricExampleMod implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        FabricNetworkHelper.CLIENT_RECEIVERS.forEach(FabricNetworkHelper.ClientReceiver::registerClientReceiver);
    }
}
