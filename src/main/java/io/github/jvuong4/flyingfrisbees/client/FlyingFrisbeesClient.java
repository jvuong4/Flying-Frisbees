package io.github.jvuong4.flyingfrisbees.client;

import io.github.jvuong4.flyingfrisbees.client.render.FrisbeeEntityRenderer;
import io.github.jvuong4.flyingfrisbees.registry.FFEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

@Environment(EnvType.CLIENT)
public class FlyingFrisbeesClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.register(FFEntities.FRISBEE, FrisbeeEntityRenderer::new);
	}
}
