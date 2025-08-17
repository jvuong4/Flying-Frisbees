package io.github.jvuong4.flyingfrisbees.client.render;

import io.github.jvuong4.flyingfrisbees.FlyingFrisbees;
import io.github.jvuong4.flyingfrisbees.entity.FrisbeeEntity;
import io.github.jvuong4.flyingfrisbees.registry.FlyingFrisbeesItems;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.entity.state.FoxEntityRenderState;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;

import java.util.HashMap;
import java.util.Map;


public class FrisbeeEntityRenderer<R extends EntityRenderState & GeoRenderState> extends GeoEntityRenderer<FrisbeeEntity, R> {
	public static Map<Item, Identifier> frisbeeTextures = new HashMap<>();

	public FrisbeeEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new FrisbeeEntityModel());
		/*
		//this uses <String,Identifier>
		frisbeeTextures.put("frisbee",Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/frisbee.png"));
		frisbeeTextures.put("black_frisbee",Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/black_frisbee.png"));
		frisbeeTextures.put("blue_frisbee",Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/blue_frisbee.png"));
		frisbeeTextures.put("brown_frisbee",Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/brown_frisbee.png"));
		frisbeeTextures.put("cyan_frisbee",Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/cyan_frisbee.png"));
		frisbeeTextures.put("gray_frisbee",Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/gray_frisbee.png"));
		frisbeeTextures.put("green_frisbee",Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/green_frisbee.png"));
		frisbeeTextures.put("light_blue_frisbee",Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/light_blue_frisbee.png"));
		frisbeeTextures.put("light_gray_frisbee",Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/light_gray_frisbee.png"));
		frisbeeTextures.put("lime_frisbee",Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/lime_frisbee.png"));
		frisbeeTextures.put("magenta_frisbee",Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/magenta_frisbee.png"));
		frisbeeTextures.put("orange_frisbee",Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/orange_frisbee.png"));
		frisbeeTextures.put("pink_frisbee",Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/pink_frisbee.png"));
		frisbeeTextures.put("purple_frisbee",Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/purple_frisbee.png"));
		frisbeeTextures.put("red_frisbee",Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/red_frisbee.png"));
		frisbeeTextures.put("white_frisbee",Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/white_frisbee.png"));
		frisbeeTextures.put("yellow_frisbee",Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/yellow_frisbee.png"));
		*/
		frisbeeTextures.put(FlyingFrisbeesItems.FRISBEE,Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/frisbee.png"));
		frisbeeTextures.put(FlyingFrisbeesItems.BLACK_FRISBEE,Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/black_frisbee.png"));
		frisbeeTextures.put(FlyingFrisbeesItems.BLUE_FRISBEE,Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/blue_frisbee.png"));
		frisbeeTextures.put(FlyingFrisbeesItems.BROWN_FRISBEE,Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/brown_frisbee.png"));
		frisbeeTextures.put(FlyingFrisbeesItems.CYAN_FRISBEE,Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/cyan_frisbee.png"));
		frisbeeTextures.put(FlyingFrisbeesItems.GRAY_FRISBEE,Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/gray_frisbee.png"));
		frisbeeTextures.put(FlyingFrisbeesItems.GREEN_FRISBEE,Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/green_frisbee.png"));
		frisbeeTextures.put(FlyingFrisbeesItems.LIGHT_BLUE_FRISBEE,Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/light_blue_frisbee.png"));
		frisbeeTextures.put(FlyingFrisbeesItems.LIGHT_GRAY_FRISBEE,Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/light_gray_frisbee.png"));
		frisbeeTextures.put(FlyingFrisbeesItems.LIME_FRISBEE,Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/lime_frisbee.png"));
		frisbeeTextures.put(FlyingFrisbeesItems.MAGENTA_FRISBEE,Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/magenta_frisbee.png"));
		frisbeeTextures.put(FlyingFrisbeesItems.ORANGE_FRISBEE,Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/orange_frisbee.png"));
		frisbeeTextures.put(FlyingFrisbeesItems.PINK_FRISBEE,Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/pink_frisbee.png"));
		frisbeeTextures.put(FlyingFrisbeesItems.PURPLE_FRISBEE,Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/purple_frisbee.png"));
		frisbeeTextures.put(FlyingFrisbeesItems.RED_FRISBEE,Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/red_frisbee.png"));
		frisbeeTextures.put(FlyingFrisbeesItems.WHITE_FRISBEE,Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/white_frisbee.png"));
		frisbeeTextures.put(FlyingFrisbeesItems.YELLOW_FRISBEE,Identifier.of(FlyingFrisbees.MOD_ID, "textures/entity/yellow_frisbee.png"));


		//, new FrisbeeEntityModel()); //, new FrisbeeEntityModel(context.getPart(FrisbeeEntityModel.FRISBEE)), 0.5f);
	}
/*
	public Identifier getTexture(FrisbeeEntityRenderState FrisbeeEntityRenderState) {
		if (foxEntityRenderState.type == FoxEntity.Variant.RED) {
			return foxEntityRenderState.sleeping ? SLEEPING_TEXTURE : TEXTURE;
		} else {
			return foxEntityRenderState.sleeping ? SLEEPING_SNOW_TEXTURE : SNOW_TEXTURE;
		}
	}
	*/

	/*
	@Override
 	public void addRenderData(FrisbeeEntity animatable, RenderData relatedObject, R renderState) {

	}
	 */
}
