package com.example.examplemod.common.init;

import com.example.examplemod.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class ModItems {

    public static Item.Properties props() {
        return new Item.Properties();
    }

    public static <T extends Item> Supplier<T> register(String name, Supplier<T> item) {
        return Services.REGISTRY.register(BuiltInRegistries.ITEM, name, item);
    }

    public static void load() {};
}
