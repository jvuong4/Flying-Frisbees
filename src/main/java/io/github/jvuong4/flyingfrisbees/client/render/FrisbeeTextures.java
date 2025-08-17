package io.github.jvuong4.flyingfrisbees.client.render;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import io.github.jvuong4.flyingfrisbees.FlyingFrisbees;
import io.github.jvuong4.flyingfrisbees.entity.FrisbeeEntity;
import io.github.jvuong4.flyingfrisbees.item.Frisbee;
import io.github.jvuong4.flyingfrisbees.registry.FlyingFrisbeesEntities;
import io.github.jvuong4.flyingfrisbees.registry.FlyingFrisbeesItems;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;
import org.jetbrains.annotations.Nullable;

public class FrisbeeTextures {
	private static final BiMap<Identifier, Frisbee> entries = HashBiMap.create();
	public static final Codecs.IdMapper<Identifier, ? extends Frisbee> MAPPER = new Codecs.IdMapper<>();
	public static final Codec<Frisbee> CODEC = Identifier.CODEC.flatXmap(
		id -> DataResult.success(entries.get(id)),
		frisbee -> DataResult.success(entries.inverse().get(frisbee))
	);
	public static final PacketCodec<ByteBuf, Frisbee> PACKET_CODEC = Identifier.PACKET_CODEC.xmap(entries::get, entries.inverse()::get);

	//public static ProjectionView PROJECTION = register("projection", new ProjectionView());
	//public static FrisbeeEntity FRISBEE = register("frisbee", new FrisbeeEntity());

	private static <T extends Frisbee> T register(String id, T frisbee) {
		entries.put(FlyingFrisbees.id(id), frisbee);
		return frisbee;
	}

	@Nullable
	public static Frisbee get(Identifier id) {
		return entries.get(id);
	}

	@Nullable
	public static Identifier getId(Frisbee frisbee) {
		return entries.inverse().get(frisbee);
	}
}
