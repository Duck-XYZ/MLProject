package com.example.examplemod.platform.services;

import com.example.examplemod.platform.FabricAttachmentType;
import com.example.examplemod.platform.IAttachmentType;
import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.attachment.v1.AttachmentTarget;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class FabricAttachmentHelper implements IAttachmentHelper {
    @Override
    public <T> Supplier<IAttachmentType<T>> register(String name, Supplier<T> defaultValue) {
        return register(name, defaultValue, null, false, null);
    }

    @Override
    public <T> Supplier<IAttachmentType<T>> register(String name, Supplier<T> defaultValue, Codec<T> codec, boolean copyOnDeath) {
        return register(name, defaultValue, codec, copyOnDeath, null);
    }

    @Override
    public <T> Supplier<IAttachmentType<T>> register(String name, Supplier<T> defaultValue, @Nullable Codec<T> codec, boolean copyOnDeath, @Nullable StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec) {
        return () -> new FabricAttachmentType<T>(name, defaultValue, codec, copyOnDeath, streamCodec);
    }

    @Override
    public <T> T get(Object holder, IAttachmentType<T> attachment) {
        if (holder instanceof AttachmentTarget target)
            return target.getAttached((AttachmentType<T>) attachment.get());
        return attachment.getDefault();
    }

    @Override
    public <T> void set(Object holder, IAttachmentType<T> attachment, T value) {
        if (holder instanceof AttachmentTarget target)
            target.setAttached((AttachmentType<T>) attachment.get(), value);
    }
}
