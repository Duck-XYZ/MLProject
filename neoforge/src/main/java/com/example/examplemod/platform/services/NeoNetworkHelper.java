package com.example.examplemod.platform.services;

import com.example.examplemod.platform.IPayloadContext;
import com.example.examplemod.platform.NeoPayloadContext;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class NeoNetworkHelper implements INetworkHelper {

    private static final List<PacketData<? extends CustomPacketPayload>> CLIENT_PACKETS = new ArrayList<>();
    private static final List<PacketData<? extends CustomPacketPayload>> SERVER_PACKETS = new ArrayList<>();
    private static final List<PacketData<? extends CustomPacketPayload>> BI_PACKETS = new ArrayList<>();

    public static void registerPackets(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");

        for (PacketData<?> packet : CLIENT_PACKETS) packet.registerClient(registrar);
        for (PacketData<?> packet : SERVER_PACKETS) packet.registerServer(registrar);
        for (PacketData<?> packet : BI_PACKETS) packet.registerBiDirectional(registrar);
    }

    @Override
    public <T extends CustomPacketPayload> void registerToClient(CustomPacketPayload.Type<T> type, StreamCodec<RegistryFriendlyByteBuf, T> streamCodec, BiConsumer<T, IPayloadContext> handler) {
        CLIENT_PACKETS.add(new PacketData<T>(type, streamCodec, handler));
    }

    @Override
    public <T extends CustomPacketPayload> void registerToServer(CustomPacketPayload.Type<T> type, StreamCodec<RegistryFriendlyByteBuf, T> streamCodec, BiConsumer<T, IPayloadContext> handler) {
        SERVER_PACKETS.add(new PacketData<T>(type, streamCodec, handler));
    }

    @Override
    public <T extends CustomPacketPayload> void registerBiDirectional(CustomPacketPayload.Type<T> type, StreamCodec<RegistryFriendlyByteBuf, T> streamCodec, BiConsumer<T, IPayloadContext> handler) {
        BI_PACKETS.add(new PacketData<T>(type, streamCodec, handler));
    }

    @Override
    public <T extends CustomPacketPayload> void sendToPlayer(ServerPlayer player, T payload) {
        PacketDistributor.sendToPlayer(player, payload);
    }

    @Override
    public <T extends CustomPacketPayload> void sendToAllPlayers(T payload, ServerLevel level) {
        PacketDistributor.sendToAllPlayers(payload);
    }

    @Override
    public <T extends CustomPacketPayload> void sendToServer(T payload) {
        PacketDistributor.sendToServer(payload);
    }

    public record PacketData<T extends CustomPacketPayload> (CustomPacketPayload.Type<T> type, StreamCodec<RegistryFriendlyByteBuf, T> streamCodec, BiConsumer<T, IPayloadContext> basicHandler) {

        public void registerClient(PayloadRegistrar registrar) {
            registrar.playToClient(type(), streamCodec(), (payload, context) -> basicHandler().accept(payload, new NeoPayloadContext(context)));
        }

        public void registerServer(PayloadRegistrar registrar) {
            registrar.playToServer(type(), streamCodec(), (payload, context) -> basicHandler().accept(payload, new NeoPayloadContext(context)));
        }

        public void registerBiDirectional(PayloadRegistrar registrar) {
            registrar.playBidirectional(type(), streamCodec(), (payload, context) -> basicHandler.accept(payload, new NeoPayloadContext(context)));
        }
    };
}
