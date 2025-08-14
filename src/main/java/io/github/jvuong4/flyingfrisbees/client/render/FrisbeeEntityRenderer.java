package io.github.jvuong4.flyingfrisbees.client.render;

import io.github.jvuong4.flyingfrisbees.entity.FrisbeeEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.state.EntityRenderState;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;


public class FrisbeeEntityRenderer<R extends EntityRenderState & GeoRenderState> extends GeoEntityRenderer<FrisbeeEntity, R> {

	public FrisbeeEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new FrisbeeEntityModel());

		//, new FrisbeeEntityModel()); //, new FrisbeeEntityModel(context.getPart(FrisbeeEntityModel.FRISBEE)), 0.5f);
	}
}
