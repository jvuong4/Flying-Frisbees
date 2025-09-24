package io.github.jvuong4.flyingfrisbees.client.render;

import io.github.jvuong4.flyingfrisbees.FlyingFrisbees;
import io.github.jvuong4.flyingfrisbees.entity.FrisbeeEntity;
import io.github.jvuong4.flyingfrisbees.entity.FrisboomerangEntity;
import io.github.jvuong4.flyingfrisbees.registry.FlyingFrisbeesItems;
import io.github.jvuong4.flyingfrisbees.registry.FlyingFrisbeesRegistry;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.constant.dataticket.DataTicket;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;

import java.util.HashMap;
import java.util.Map;


public class FrisboomerangEntityRenderer<R extends EntityRenderState & GeoRenderState> extends GeoEntityRenderer<FrisboomerangEntity, R> {
	public FrisboomerangEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new FrisboomerangEntityModel());
	}

	DataTicket<String> TEXTURE = DataTicket.create("frisbee_texture", String.class);

	@Override
	public void addRenderData(
		FrisboomerangEntity frisbee,
		Void ummm,
		R renderState
	) {
		super.addRenderData(frisbee,ummm, renderState);
		renderState.addGeckolibData(
			TEXTURE, frisbee.getAttached(FlyingFrisbeesRegistry.FRISBEE_TEXTURE_ATTACHMENT)
		);
		renderState.addGeckolibData(
			DataTickets.ITEM,
			frisbee.getItemStack().getItem()
		);
	}

	@Override
	public Identifier getTextureLocation(R renderState) {
		return FlyingFrisbees.id("textures/entity/" + renderState.getGeckolibData(TEXTURE) + ".png");
	}
}
