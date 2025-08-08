package io.github.jvuong4.flyingfrisbees.Entity;

import io.github.jvuong4.flyingfrisbees.FlyingFrisbees;
import io.github.jvuong4.flyingfrisbees.FlyingFrisbeesClient;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;


public class FrisbeeEntityRenderer extends EntityRenderer<FrisbeeEntity, FrisbeeEntityModel> {

	public FrisbeeEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new FrisbeeEntityModel(context.getPart(FlyingFrisbeesClient.MODEL_FRISBEE_LAYER)), 0.5f);
	}

	@Override
	public Identifier getTexture(FrisbeeEntity entity) {
		return Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/frisbee.png");
	};

	//@Override
	public FrisbeeEntityRenderState createRenderState() {
		return new FrisbeeEntityRenderState();
	}

	public void updateRenderState(FrisbeeEntity frisbeeEntity, FrisbeeEntityRenderState frisbeeEntityRenderState, float f) {
		super.updateRenderState(frisbeeEntity, frisbeeEntityRenderState, f);
		frisbeeEntityRenderState.yaw = frisbeeEntity.getLerpedYaw(f);
		frisbeeEntityRenderState.pitch = frisbeeEntity.getLerpedPitch(f);
		//tridentEntityRenderState.enchanted = tridentEntity.isEnchanted();
	}
}
