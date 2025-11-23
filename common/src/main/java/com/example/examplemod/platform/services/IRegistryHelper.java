package com.example.examplemod.platform.services;

import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public interface IRegistryHelper {

    <C, T extends C> Supplier<T> register(Registry<C> registry, String name, Supplier<T> supplier);
}
