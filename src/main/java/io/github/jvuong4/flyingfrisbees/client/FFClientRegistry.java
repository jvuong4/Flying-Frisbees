package io.github.jvuong4.flyingfrisbees.client;

import io.github.jvuong4.flyingfrisbees.client.render.FrisbeeEntityRenderer;
import io.github.jvuong4.flyingfrisbees.registry.FFEntities;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;

import java.util.function.BiConsumer;

public final class FFClientRegistry {
	public static void registerRenderers(BiConsumer<EntityType<? extends Entity>, EntityRendererFactory> entityRenderers)
	{
		entityRenderers.accept(FFEntities.FRISBEE, FrisbeeEntityRenderer::new);
//		entityRenderers.accept(EntityRegistry.BAT.get(), BatRenderer::new);

	}
}
