package com.example.examplemod.platform.services;

import com.example.examplemod.common.Constants;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class FabricRegistryHelper implements IRegistryHelper {
    @Override
    public <C, T extends C> Supplier<T> register(Registry<C> registry, String name, Supplier<T> supplier) {
        return () -> Registry.register(registry, name, supplier.get());
    }
}
