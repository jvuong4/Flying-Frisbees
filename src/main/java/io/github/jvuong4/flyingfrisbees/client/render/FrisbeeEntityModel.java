package io.github.jvuong4.flyingfrisbees.client.render;

import io.github.jvuong4.flyingfrisbees.FlyingFrisbees;
import io.github.jvuong4.flyingfrisbees.entity.FrisbeeEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;


public class FrisbeeEntityModel extends GeoModel<FrisbeeEntity> {

	public FrisbeeEntityModel() {

	}

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
