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
		super(FlyingFrisbees.id("frisbee"));//Identifier.of(FlyingFrisbees.MOD_ID,"frisbee"));
	}

	@Override
	public Identifier getTextureResource(GeoRenderState renderState) {
		//renderState.getGeckolibData(DataTickets.ITEM);
		//return frisbeeTextures.get(renderState.getOrDefaultGeckolibData(DataTickets.ITEM, FlyingFrisbeesItems.FRISBEE));
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

	/*
	@Override
	public Identifier getTextureResource(GeoRenderState renderState) {
		return Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/frisbee.png");
	}
*/
}

///public final void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
///         this.getRootPart().render(matrices, vertices, light, overlay, color);
///     }
///
///     public final void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay) {
///         this.render(matrices, vertices, light, overlay, -1);
///     }
