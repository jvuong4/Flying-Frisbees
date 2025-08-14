package io.github.jvuong4.flyingfrisbees.client;

import io.github.jvuong4.flyingfrisbees.FlyingFrisbees;
import io.github.jvuong4.flyingfrisbees.client.render.FrisbeeEntityRenderer;
import io.github.jvuong4.flyingfrisbees.client.render.FrisbeeEntityModel;
import io.github.jvuong4.flyingfrisbees.entity.FrisbeeEntity;
import io.github.jvuong4.flyingfrisbees.registry.FFEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.EntityRenderers;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class FlyingFrisbeesClient implements ClientModInitializer {
	public static final EntityModelLayer MODEL_FRISBEE_LAYER = new EntityModelLayer(Identifier.of(FlyingFrisbees.MOD_ID, "frisbee"), "main");

	@Override
	public void onInitializeClient() {

		//this didnt work lol
		//EntityRendererRegistry.register(FFEntities.FRISBEE, FrisbeeEntityRenderer::new);

		FFClientRegistry.registerRenderers(EntityRendererRegistry::register);
	}
}
