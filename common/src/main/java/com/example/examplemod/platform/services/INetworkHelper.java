package com.example.examplemod.platform.services;

import com.example.examplemod.platform.IPayloadContext;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.function.BiConsumer;

public interface INetworkHelper {

    <T extends CustomPacketPayload> void registerToClient(CustomPacketPayload.Type<T> type,
                                                          StreamCodec<RegistryFriendlyByteBuf, T> streamCodec,
                                                          BiConsumer<T, IPayloadContext> handler);

    <T extends CustomPacketPayload> void registerToServer(CustomPacketPayload.Type<T> type,
                                                          StreamCodec<RegistryFriendlyByteBuf, T> streamCodec,
                                                          BiConsumer<T, IPayloadContext> handler);

    <T extends CustomPacketPayload> void registerBiDirectional(CustomPacketPayload.Type<T> type,
                                                               StreamCodec<RegistryFriendlyByteBuf, T> streamCodec,
                                                               BiConsumer<T, IPayloadContext> handler);

    <T extends CustomPacketPayload> void sendToPlayer(ServerPlayer player, T payload);

    <T extends CustomPacketPayload> void sendToAllPlayers(T payload, ServerLevel level);

    <T extends CustomPacketPayload> void sendToServer(T payload);

    record PacketData<T extends CustomPacketPayload> (CustomPacketPayload.Type<T> type, StreamCodec<? extends RegistryFriendlyByteBuf, T> streamCodec, BiConsumer<T, IPayloadContext> basicHandler, BiConsumer<T, IPayloadContext> clientHandler) {
    };
}
