package com.example.examplemod.platform.services;

import com.example.examplemod.platform.FabricClientPayloadContext;
import com.example.examplemod.platform.FabricServerPayloadContext;
import com.example.examplemod.platform.IPayloadContext;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class FabricNetworkHelper implements INetworkHelper {

    public static final List<ClientReceiver<? extends CustomPacketPayload>> CLIENT_RECEIVERS = new ArrayList<>();

    @Override
    public <T extends CustomPacketPayload> void registerToClient(CustomPacketPayload.Type<T> type, StreamCodec<RegistryFriendlyByteBuf, T> streamCodec, BiConsumer<T, IPayloadContext> handler) {
        PayloadTypeRegistry.playS2C().register(type, streamCodec);
        CLIENT_RECEIVERS.add(new ClientReceiver<>(type, handler));
    }

    @Override
    public <T extends CustomPacketPayload> void registerToServer(CustomPacketPayload.Type<T> type, StreamCodec<RegistryFriendlyByteBuf, T> streamCodec, BiConsumer<T, IPayloadContext> handler) {
        PayloadTypeRegistry.playC2S().register(type, streamCodec);
        ServerPlayNetworking.registerGlobalReceiver(type, (payload, context) -> handler.accept(payload, new FabricServerPayloadContext(context)));
    }

    @Override
    public <T extends CustomPacketPayload> void registerBiDirectional(CustomPacketPayload.Type<T> type, StreamCodec<RegistryFriendlyByteBuf, T> streamCodec, BiConsumer<T, IPayloadContext> handler) {
        registerToClient(type, streamCodec, handler);
        registerToServer(type, streamCodec, handler);
    }

    @Override
    public <T extends CustomPacketPayload> void sendToPlayer(ServerPlayer player, T payload) {
        ServerPlayNetworking.send(player, payload);
    }

    @Override
    public <T extends CustomPacketPayload> void sendToAllPlayers(T payload, ServerLevel level) {
        for (ServerPlayer player : PlayerLookup.world(level)) sendToPlayer(player, payload);
    }

    @Override
    public <T extends CustomPacketPayload> void sendToServer(T payload) {
        ClientPlayNetworking.send(payload);
    }

    public record ClientReceiver<T extends CustomPacketPayload> (CustomPacketPayload.Type<T> type, BiConsumer<T, IPayloadContext> basicHandler) {
        public void registerClientReceiver() {
            ClientPlayNetworking.registerGlobalReceiver(type(), (payload, context) -> basicHandler.accept(payload, new FabricClientPayloadContext(context)));
        }
    };
}
