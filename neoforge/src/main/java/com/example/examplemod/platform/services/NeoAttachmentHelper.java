package com.example.examplemod.platform.services;

import com.example.examplemod.platform.IAttachmentType;
import com.example.examplemod.platform.NeoAttachmentType;
import com.example.examplemod.platform.Services;
import com.mojang.serialization.Codec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.checkerframework.checker.units.qual.N;

import java.util.function.Supplier;

public class NeoAttachmentHelper implements IAttachmentHelper {
    @Override
    public <T> Supplier<IAttachmentType<T>> register(String name, Supplier<T> defaultValue) {
        return register(name, defaultValue, null, false, null);
    }

    @Override
    public <T> Supplier<IAttachmentType<T>> register(String name, Supplier<T> defaultValue, Codec<T> codec, boolean copyOnDeath) {
        return register(name, defaultValue, codec, copyOnDeath, null);
    }

    @Override
    public <T> Supplier<IAttachmentType<T>> register(String name, Supplier<T> defaultValue, Codec<T> codec, boolean copyOnDeath, StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec) {
        IAttachmentType<T> attachment = new NeoAttachmentType<>(defaultValue, codec, streamCodec, copyOnDeath);
        Services.REGISTRY.register(NeoForgeRegistries.ATTACHMENT_TYPES, name, () -> (AttachmentType<T>) attachment.get());
        return () -> attachment;
    }

    @Override
    public <T> T get(Object holder, IAttachmentType<T> attachment) {
        return null;
    }

    @Override
    public <T> void set(Object holder, IAttachmentType<T> attachment, T value) {

    }
}
