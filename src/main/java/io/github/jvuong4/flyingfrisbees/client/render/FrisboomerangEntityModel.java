package io.github.jvuong4.flyingfrisbees.client.render;

import io.github.jvuong4.flyingfrisbees.FlyingFrisbees;
import io.github.jvuong4.flyingfrisbees.entity.FrisbeeEntity;
import io.github.jvuong4.flyingfrisbees.entity.FrisboomerangEntity;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

import java.util.HashMap;
import java.util.Map;


public class FrisboomerangEntityModel extends DefaultedEntityGeoModel<FrisboomerangEntity> {

	public FrisboomerangEntityModel() {
		super(FlyingFrisbees.id("frisbee"));
	}

	@Override
	public Identifier getTextureResource(GeoRenderState renderState) {
		return Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/loyal_frisbee.png");
	}

	@Override
	public Identifier getModelResource(GeoRenderState renderState) {
		return Identifier.of(FlyingFrisbees.MOD_ID, "geckolib/models/frisbee.geo.json");
	}

	@Override
	public Identifier getAnimationResource(FrisboomerangEntity animatable) {
		return Identifier.of(FlyingFrisbees.MOD_ID, "geckolib/animations/frisbee.animation.json");
	}

}
