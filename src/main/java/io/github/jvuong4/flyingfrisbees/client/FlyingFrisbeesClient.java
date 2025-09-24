package io.github.jvuong4.flyingfrisbees.client;

import io.github.jvuong4.flyingfrisbees.FlyingFrisbees;
import io.github.jvuong4.flyingfrisbees.client.render.FrisbeeEntityModel;
import io.github.jvuong4.flyingfrisbees.client.render.FrisbeeEntityRenderer;
import io.github.jvuong4.flyingfrisbees.client.render.FrisboomerangEntityRenderer;
import io.github.jvuong4.flyingfrisbees.entity.FrisbeeEntity;
import io.github.jvuong4.flyingfrisbees.item.Frisbee;
import io.github.jvuong4.flyingfrisbees.registry.FlyingFrisbeesEntities;
import io.github.jvuong4.flyingfrisbees.registry.FlyingFrisbeesItems;
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
		EntityRendererRegistry.register(FlyingFrisbeesEntities.FRISBOOMERANG, FrisboomerangEntityRenderer::new);

		FrisbeeEntityModel.frisbeeTextures.put(FlyingFrisbeesItems.FRISBEE,Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/frisbee.png"));
		FrisbeeEntityModel.frisbeeTextures.put(FlyingFrisbeesItems.BLACK_FRISBEE,Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/black_frisbee.png"));
		FrisbeeEntityModel.frisbeeTextures.put(FlyingFrisbeesItems.BLUE_FRISBEE,Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/blue_frisbee.png"));
		FrisbeeEntityModel.frisbeeTextures.put(FlyingFrisbeesItems.BROWN_FRISBEE,Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/brown_frisbee.png"));
		FrisbeeEntityModel.frisbeeTextures.put(FlyingFrisbeesItems.CYAN_FRISBEE,Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/cyan_frisbee.png"));
		FrisbeeEntityModel.frisbeeTextures.put(FlyingFrisbeesItems.GRAY_FRISBEE,Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/gray_frisbee.png"));
		FrisbeeEntityModel.frisbeeTextures.put(FlyingFrisbeesItems.GREEN_FRISBEE,Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/green_frisbee.png"));
		FrisbeeEntityModel.frisbeeTextures.put(FlyingFrisbeesItems.LIGHT_BLUE_FRISBEE,Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/light_blue_frisbee.png"));
		FrisbeeEntityModel.frisbeeTextures.put(FlyingFrisbeesItems.LIGHT_GRAY_FRISBEE,Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/light_gray_frisbee.png"));
		FrisbeeEntityModel.frisbeeTextures.put(FlyingFrisbeesItems.LIME_FRISBEE,Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/lime_frisbee.png"));
		FrisbeeEntityModel.frisbeeTextures.put(FlyingFrisbeesItems.MAGENTA_FRISBEE,Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/magenta_frisbee.png"));
		FrisbeeEntityModel.frisbeeTextures.put(FlyingFrisbeesItems.ORANGE_FRISBEE,Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/orange_frisbee.png"));
		FrisbeeEntityModel.frisbeeTextures.put(FlyingFrisbeesItems.PINK_FRISBEE,Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/pink_frisbee.png"));
		FrisbeeEntityModel.frisbeeTextures.put(FlyingFrisbeesItems.PURPLE_FRISBEE,Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/purple_frisbee.png"));
		FrisbeeEntityModel.frisbeeTextures.put(FlyingFrisbeesItems.RED_FRISBEE,Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/red_frisbee.png"));
		FrisbeeEntityModel.frisbeeTextures.put(FlyingFrisbeesItems.WHITE_FRISBEE,Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/white_frisbee.png"));
		FrisbeeEntityModel.frisbeeTextures.put(FlyingFrisbeesItems.YELLOW_FRISBEE,Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/yellow_frisbee.png"));
		FrisbeeEntityModel.frisbeeTextures.put(FlyingFrisbeesItems.DELTA_FRISBEE,Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/delta_frisbee.png"));
		FrisbeeEntityModel.frisbeeTextures.put(FlyingFrisbeesItems.FRISBOOMERANG,Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/loyal_frisbee.png"));
	}
}
