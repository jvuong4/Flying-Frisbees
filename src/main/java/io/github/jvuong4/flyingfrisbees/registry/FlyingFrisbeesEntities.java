package io.github.jvuong4.flyingfrisbees.registry;

import io.github.jvuong4.flyingfrisbees.entity.FrisbeeEntity;
import io.github.jvuong4.flyingfrisbees.FlyingFrisbees;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

public class FlyingFrisbeesEntities {

	public static final RegistryKey<EntityType<?>> FRISBEE_KEY = RegistryKey.of(
		Registries.ENTITY_TYPE.getKey(),
		Identifier.of(FlyingFrisbees.MOD_ID, "frisbee")
	);
	public static final EntityType<FrisbeeEntity> FRISBEE = Registry.register(
		Registries.ENTITY_TYPE,
		FRISBEE_KEY.getValue(),
		EntityType.Builder.<FrisbeeEntity>create(FrisbeeEntity::new, SpawnGroup.MISC)
			.dropsNothing()
			.dimensions(0.5F, 0.0625F)
			.maxTrackingRange(4).trackingTickInterval(20)
			.build(FRISBEE_KEY)
	);

	public static void init() {
	}
}

//we can ignore this teehee :3
//TRIDENT = register("trident", EntityType.Builder.create(TridentEntity::new, SpawnGroup.MISC).dropsNothing().dimensions(0.5F, 0.5F).eyeHeight(0.13F).maxTrackingRange(4).trackingTickInterval(20));
//
