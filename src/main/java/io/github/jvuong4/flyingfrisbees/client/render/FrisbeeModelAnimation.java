// Save this class in your mod and generate all required imports
package io.github.jvuong4.flyingfrisbees.client.render;

import net.minecraft.client.render.entity.animation.*;

/**
 * Made with Blockbench 4.12.5
 * Exported for Minecraft version 1.19 or later with Yarn mappings
 * @author Author
 */
public class FrisbeeModelAnimation {
	public static final AnimationDefinition spinning = AnimationDefinition.Builder.create(0.3333F).looping()
		.addBoneAnimation("body", new Transformation(Transformation.Targets.ROTATE,
			new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
			new Keyframe(0.3333F, AnimationHelper.createRotationalVector(0.0F, 360.0F, 0.0F), Transformation.Interpolations.LINEAR)
		))
		.build();

	public static final AnimationDefinition stopped = AnimationDefinition.Builder.create(0.0F)
		.build();

	/*
	public static void registerControllers(FrisbeeEntity frisbee, AnimatableManager.ControllerRegistrar controllers) {

	}
	 */
}
