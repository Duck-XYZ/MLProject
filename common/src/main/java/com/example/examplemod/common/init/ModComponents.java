package com.example.examplemod.common.init;

import com.example.examplemod.platform.Services;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class ModComponents {

    public static <T> Supplier<DataComponentType<T>> register(String name, UnaryOperator<DataComponentType.Builder<T>> comp) {
        return Services.REGISTRY.register(BuiltInRegistries.DATA_COMPONENT_TYPE, name, () -> (comp.apply(DataComponentType.builder())).build());
    }

    public static void load() {};
}
