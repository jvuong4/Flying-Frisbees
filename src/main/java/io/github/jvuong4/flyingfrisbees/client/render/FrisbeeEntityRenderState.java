package io.github.jvuong4.flyingfrisbees.client.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.entity.AnimationState;


@Environment(EnvType.CLIENT)
public class FrisbeeEntityRenderState extends EntityRenderState {
	public final AnimationState spinningAnimationState = new AnimationState();
	public final AnimationState stoppedAnimationState = new AnimationState();

	public float pitch;
	public float yaw;
	//public boolean enchanted;
	//public boolean onGround;

	public FrisbeeEntityRenderState() {}
}

