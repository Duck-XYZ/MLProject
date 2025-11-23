package com.example.examplemod;


import com.example.examplemod.common.CommonClass;
import com.example.examplemod.common.Constants;
import com.example.examplemod.platform.services.NeoNetworkHelper;
import com.example.examplemod.platform.services.NeoRegistryHelper;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(Constants.MOD_ID)
public class ExampleMod {

    public ExampleMod(IEventBus eventBus) {

        // This method is invoked by the NeoForge mod loader when it is ready
        // to load your mod. You can access NeoForge and Common code in this
        // project.

        // Use NeoForge to bootstrap the Common mod.
        Constants.LOG.info("Hello NeoForge world!");
        CommonClass.init();

        eventBus.addListener(NeoNetworkHelper::registerPackets);
        NeoRegistryHelper.REGISTRIES.registerAll(eventBus);
    }
}