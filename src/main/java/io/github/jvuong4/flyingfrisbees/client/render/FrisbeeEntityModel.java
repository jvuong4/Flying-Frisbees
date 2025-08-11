package io.github.jvuong4.flyingfrisbees.client.render;

import io.github.jvuong4.flyingfrisbees.entity.FrisbeeEntity;
import io.github.jvuong4.flyingfrisbees.FlyingFrisbees;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

// Made with Blockbench 4.12.5
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class FrisbeeEntityModel extends GeoModel<FrisbeeEntity> {

	@Override
	public Identifier getModelResource(GeoRenderState renderState) {
		return Identifier.of(FlyingFrisbees.MOD_ID, "geckolib/models/frisbee.geo.json");
	}

	@Override
	public Identifier getTextureResource(GeoRenderState renderState) {
		return Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/frisbee.png");
	}

	@Override
	public Identifier getAnimationResource(FrisbeeEntity animatable) {
		return Identifier.of(FlyingFrisbees.MOD_ID, "geckolib/animations/frisbee.animation.json");
	}
}

///public final void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
///         this.getRootPart().render(matrices, vertices, light, overlay, color);
///     }
///
///     public final void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay) {
///         this.render(matrices, vertices, light, overlay, -1);
///     }
