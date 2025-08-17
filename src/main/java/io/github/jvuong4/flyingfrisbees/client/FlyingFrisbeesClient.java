package io.github.jvuong4.flyingfrisbees.client;

import io.github.jvuong4.flyingfrisbees.FlyingFrisbees;
import io.github.jvuong4.flyingfrisbees.client.render.FrisbeeEntityRenderer;
import io.github.jvuong4.flyingfrisbees.entity.FrisbeeEntity;
import io.github.jvuong4.flyingfrisbees.item.Frisbee;
import io.github.jvuong4.flyingfrisbees.registry.FlyingFrisbeesEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class FlyingFrisbeesClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.register(FlyingFrisbeesEntities.FRISBEE, FrisbeeEntityRenderer::new);

		/*
		ClientPlayNetworking.registerGlobalReceiver(
			S2CCaptureImagePayload.ID,  //what would i replace this with...?,
			(payload,context) -> {
				ClientWorld world = context.client().world;
				FrisbeeEntity subject = (FrisbeeEntity)world.getEntity(payload.subject());
				if (subject != null)
				{
					subject.setAttached(FlyingFrisbeesEntities.FRISBEE_COLOR = FrisbeeEntityRenderer.frisbeeTextures.get(subject.getItemStack().getItem()));
				}
			}
		);
		*/
	}
}
