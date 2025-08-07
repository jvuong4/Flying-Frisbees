package io.github.jvuong4.flyingfrisbees.Registry;

import io.github.jvuong4.flyingfrisbees.Entity.FrisbeeEntity;
import io.github.jvuong4.flyingfrisbees.FlyingFrisbees;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class FFEntities {

	public static final RegistryKey<EntityType<?>> FRISBEE_KEY = RegistryKey.of(
		Registries.ENTITY_TYPE.getKey(),
		Identifier.of(FlyingFrisbees.MOD_ID, "frisbee")
	);

	public static final EntityType<FrisbeeEntity> FRISBEE = Registry.register(
		Registries.ENTITY_TYPE,
		FRISBEE_KEY.getValue(),
		EntityType.Builder.<FrisbeeEntity>create(FrisbeeEntity::new, SpawnGroup.MISC)
			.dimensions(0.5F, 0.0625F)
			//.trackRangeBlocks(4).trackedUpdateRate(10)
			.build(FRISBEE_KEY)
	);

	public static void init() {}
}
