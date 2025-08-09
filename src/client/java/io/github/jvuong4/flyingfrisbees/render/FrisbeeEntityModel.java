package io.github.jvuong4.flyingfrisbees.Entity;

import com.google.common.collect.ImmutableList;
import io.github.jvuong4.flyingfrisbees.Entity.FrisbeeEntity;
import io.github.jvuong4.flyingfrisbees.Entity.FrisbeeEntityRenderState;
import io.github.jvuong4.flyingfrisbees.FlyingFrisbees;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

// Made with Blockbench 4.12.5
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class FrisbeeEntityModel extends EntityModel<FrisbeeEntityRenderState> {

	public static final EntityModelLayer FRISBEE = new EntityModelLayer(Identifier.of(FlyingFrisbees.MOD_ID, "frisbee"),"main")

	private final ModelPart body;

	private final Animation spinningAnimation;
	private final Animation stoppedAnimation;

	public FrisbeeEntityModel(ModelPart root) {

		this.body = root.getChild("body");
		this.spinningAnimation = frisbeeModelAnimation.spinning.createAnimation("body");
		this.stoppedAnimation = frisbeeModelAnimation.stopped.createAnimation("body");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(-8, 0).cuboid(-4.0F, -1.0F, -4.0F, 8.0F, 0.0F, 8.0F, new Dilation(0.0F))
		.uv(0, 8).cuboid(-4.0F, -1.0F, -4.0F, 8.0F, 1.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData side_r1 = body.addChild("side_r1", ModelPartBuilder.create().uv(0, 8).cuboid(-4.0F, -1.0F, -4.0F, 8.0F, 1.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		ModelPartData side_r2 = body.addChild("side_r2", ModelPartBuilder.create().uv(0, 8).cuboid(-4.0F, -1.0F, -4.0F, 8.0F, 1.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

		ModelPartData side_r3 = body.addChild("side_r3", ModelPartBuilder.create().uv(0, 8).cuboid(-4.0F, -1.0F, -4.0F, 8.0F, 1.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));
		return TexturedModelData.of(modelData, 16, 9);
	}
	@Override
	public void setAngles(FrisbeeEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		body.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		/*
		ImmutableList.of(this.body).forEach((modelRenderer) -> {
			modelRenderer.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha)
		});
		 */
	}

	@Override
	public Identifier getTexture(FrisbeeEntity entity) {
		return Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/frisbee.png");
	};
}
