package io.github.jvuong4.flyingfrisbees;

import io.github.jvuong4.flyingfrisbees.Entity.FrisbeeEntityRenderer;
import io.github.jvuong4.flyingfrisbees.Entity.FrisbeeEntityModel;
import io.github.jvuong4.flyingfrisbees.Registry.FFEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class FlyingFrisbeesClient implements ClientModInitializer {
	//public static final EntityModelLayer MODEL_FRISBEE_LAYER = new EntityModelLayer(Identifier.of(FlyingFrisbees.MOD_ID, "frisbee"), "main");
	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.register(FFEntities.FRISBEE, (context) -> {
			return new FrisbeeEntityRenderer(context);
		});

		EntityModelLayerRegistry.registerModelLayer(MODEL_FRISBEE_LAYER, FrisbeeEntityModel::getTexturedModelData);
		EntityRendererRegistry.register(FFEntities.FRISBEE, FrisbeeEntityRenderer::new);
	}
}
