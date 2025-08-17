package io.github.jvuong4.flyingfrisbees.client;

import io.github.jvuong4.flyingfrisbees.FlyingFrisbees;
import io.github.jvuong4.flyingfrisbees.client.render.FrisbeeEntityRenderer;
import io.github.jvuong4.flyingfrisbees.registry.FlyingFrisbeesEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class FlyingFrisbeesClient implements ClientModInitializer {
	public Map<String, Identifier> frisbeeTextures = new HashMap<>();
	@Override
	public void onInitializeClient() {
		frisbeeTextures.put("frisbee",Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/frisbee.png"));
		frisbeeTextures.put("black_frisbee",Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/black_frisbee.png"));
		frisbeeTextures.put("blue_frisbee",Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/blue_frisbee.png"));
		frisbeeTextures.put("brown_frisbee",Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/brown_frisbee.png"));
		frisbeeTextures.put("cyan_frisbee",Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/cyan_frisbee.png"));
		frisbeeTextures.put("gray_frisbee",Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/gray_frisbee.png"));
		frisbeeTextures.put("green_frisbee",Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/green_frisbee.png"));
		frisbeeTextures.put("light_blue_frisbee",Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/light_blue_frisbee.png"));
		frisbeeTextures.put("light_gray_frisbee",Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/light_gray_frisbee.png"));
		frisbeeTextures.put("lime_frisbee",Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/lime_frisbee.png"));
		frisbeeTextures.put("magenta_frisbee",Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/magenta_frisbee.png"));
		frisbeeTextures.put("orange_frisbee",Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/orange_frisbee.png"));
		frisbeeTextures.put("pink_frisbee",Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/pink_frisbee.png"));
		frisbeeTextures.put("purple_frisbee",Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/purple_frisbee.png"));
		frisbeeTextures.put("red_frisbee",Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/red_frisbee.png"));
		frisbeeTextures.put("white_frisbee",Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/white_frisbee.png"));
		frisbeeTextures.put("yellow_frisbee",Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/yellow_frisbee.png"));

		EntityRendererRegistry.register(FlyingFrisbeesEntities.FRISBEE, FrisbeeEntityRenderer::new);
	}
}
