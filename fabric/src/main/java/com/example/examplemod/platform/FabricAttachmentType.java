package com.example.examplemod.platform;

import com.example.examplemod.common.Constants;
import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.fabricmc.fabric.impl.attachment.AttachmentTypeImpl;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.Supplier;

public class FabricAttachmentType<T> implements IAttachmentType<T> {
    private final AttachmentType<T> attachmentType;
    private final Supplier<T> defaultValue;

    public FabricAttachmentType(String name, Supplier<T> defaultValue, Codec<T> codec, boolean copyOnDeath, StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec) {
        this.attachmentType = AttachmentRegistry.<T>create(Constants.resLoc(name), (builder) -> {
            if (codec != null) {
                builder.persistent(codec);

                if (copyOnDeath) builder.copyOnDeath();
            }
            if (streamCodec != null) builder.syncWith(streamCodec,  (target, player) -> true);
        });

        this.defaultValue = defaultValue;
    }

    @Override
    public Object get() {
        return attachmentType;
    }

    @Override
    public T getDefault() {
        return defaultValue.get();
    }
}
