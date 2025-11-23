package com.example.examplemod.common.init;

import com.example.examplemod.platform.IAttachmentType;
import com.example.examplemod.platform.Services;
import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

import java.util.function.Supplier;

public class ModAttachments {

    public static <T> Supplier<IAttachmentType<T>> register(String name, Supplier<T> defaultValue) {
        return Services.ATTACHMENT.register(name, defaultValue);
    }

    public static <T> Supplier<IAttachmentType<T>> register(String name, Supplier<T> defaultValue, Codec<T> codec, boolean copyOnDeath) {
        return Services.ATTACHMENT.register(name, defaultValue, codec, copyOnDeath);
    }

    public static <T> Supplier<IAttachmentType<T>> register(String name, Supplier<T> defaultValue, Codec<T> codec, boolean copyOnDeath, StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec) {
        return Services.ATTACHMENT.register(name, defaultValue, codec, copyOnDeath, streamCodec);
    }

    public static void load() {}

}
