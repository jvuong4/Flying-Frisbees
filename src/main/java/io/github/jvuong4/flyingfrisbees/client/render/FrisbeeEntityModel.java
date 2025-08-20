package io.github.jvuong4.flyingfrisbees.client.render;

import io.github.jvuong4.flyingfrisbees.FlyingFrisbees;
import io.github.jvuong4.flyingfrisbees.entity.FrisbeeEntity;
import io.github.jvuong4.flyingfrisbees.registry.FlyingFrisbeesItems;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.model.DefaultedGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

import java.util.HashMap;
import java.util.Map;


public class FrisbeeEntityModel extends DefaultedEntityGeoModel<FrisbeeEntity> {
	public static Map<Item, Identifier> frisbeeTextures = new HashMap<>();

	public FrisbeeEntityModel() {
		super(FlyingFrisbees.id("frisbee"));
	}

	@Override
	public Identifier getTextureResource(GeoRenderState renderState) {

		return frisbeeTextures.get(renderState.getGeckolibData(DataTickets.ITEM));
	}

	@Override
	public Identifier getModelResource(GeoRenderState renderState) {
		return Identifier.of(FlyingFrisbees.MOD_ID, "geckolib/models/frisbee.geo.json");
	}

	@Override
	public Identifier getAnimationResource(FrisbeeEntity animatable) {
		return Identifier.of(FlyingFrisbees.MOD_ID, "geckolib/animations/frisbee.animation.json");
	}

}
