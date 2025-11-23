package com.example.examplemod.common.init;

import com.example.examplemod.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

public class ModBlocks {

    public static <T extends Block> Supplier<T> register(String name, Supplier<T> block) {
        Supplier<T> toRet = Services.REGISTRY.register(BuiltInRegistries.BLOCK, name, block);
        ModItems.register(name, () -> new BlockItem(toRet.get(), new Item.Properties()));
        return toRet;
    }

    public static void load() {};
}
