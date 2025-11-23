package com.example.examplemod.platform.services;

import com.example.examplemod.platform.IAttachmentType;
import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

import java.util.function.Supplier;

public interface IAttachmentHelper {

    <T> Supplier<IAttachmentType<T>> register(String name, Supplier<T> defaultValue);

    <T> Supplier<IAttachmentType<T>> register(String name, Supplier<T> defaultValue, Codec<T> codec, boolean copyOnDeath);

    <T> Supplier<IAttachmentType<T>> register(String name, Supplier<T> defaultValue, Codec<T> codec, boolean copyOnDeath, StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec);

    <T> T get(Object holder, IAttachmentType<T> attachment);

    <T> void set(Object holder, IAttachmentType<T> attachment, T value);
}
