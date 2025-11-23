package com.example.examplemod.platform.services;

import com.example.examplemod.common.Constants;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class NeoRegistryHelper implements IRegistryHelper {

    public static final RegistryMap REGISTRIES = new RegistryMap();
    public static final DeferredRegister<DataComponentType<?>> COMPONENT_TYPES = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, Constants.MOD_ID);

    @Override
    public <C, T extends C> Supplier<T> register(Registry<C> registry, String name, Supplier<T> supplier) {
        return REGISTRIES.register(registry, name, supplier);
    }

    public static class RegistryMap {

        public final Map<ResourceLocation, DeferredRegister<?>> registries = new HashMap<>();

        @SuppressWarnings("unchecked")
        public <C, T extends C> DeferredHolder<C, T> register(Registry<C> registry, String name, Supplier<T> supplier) {
            DeferredRegister<C> register = (DeferredRegister<C>) registries.computeIfAbsent(registry.key().location(),
                    key -> DeferredRegister.create(key, Constants.MOD_ID));

            return register.register(name, supplier);
        }

        public void registerAll(IEventBus eventBus) {
            registries.values().forEach(register -> register.register(eventBus));
            COMPONENT_TYPES.register(eventBus);
        }
    }
}
