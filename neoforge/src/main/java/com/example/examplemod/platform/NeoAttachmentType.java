package com.example.examplemod.platform;

import com.mojang.serialization.Codec;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.attachment.AttachmentSyncHandler;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.common.util.INBTSerializable;

import java.util.function.Supplier;

public class NeoAttachmentType<T> implements IAttachmentType<T> {
    private final AttachmentType<T> attachmentType;
    private final Supplier<T> defaultAttachment;


    public  NeoAttachmentType(Supplier<T> defaultValue, Codec<T> codec, StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec, boolean copyOnDeath) {
        AttachmentType.Builder<T> builder = AttachmentType.builder(defaultValue);

        if (codec != null) {
            builder.serialize(codec);
            if (copyOnDeath) {
                builder.copyOnDeath();
            }
        }

        if (streamCodec != null) builder.sync(streamCodec);

        this.attachmentType = builder.build();
        this.defaultAttachment = defaultValue;
    }


    @Override
    public AttachmentType<T> get() {
        return attachmentType;
    }

    @Override
    public T getDefault() {
        return defaultAttachment.get();
    }
}
