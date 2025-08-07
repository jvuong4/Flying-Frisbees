package io.github.jvuong4.flyingfrisbees.Entity;

import io.github.jvuong4.flyingfrisbees.FlyingFrisbees;
import io.github.jvuong4.flyingfrisbees.FlyingFrisbeesClient;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;


public class FrisbeeEntityRenderer extends ProjectileEntityRenderer<FrisbeeEntity, FrisbeeEntityModel> {

	public FrisbeeEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new FrisbeeEntityModel(context.getPart(FlyingFrisbeesClient.MODEL_FRISBEE_LAYER)), 0.5f);
	}

	@Override
	public Identifier getTexture(FrisbeeEntity entity) {
		return Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/frisbee.png");
	};

}
