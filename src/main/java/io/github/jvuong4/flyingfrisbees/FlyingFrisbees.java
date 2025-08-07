package io.github.jvuong4.flyingfrisbees;

import io.github.jvuong4.flyingfrisbees.Entity.FrisbeeEntity;
import io.github.jvuong4.flyingfrisbees.Registry.FFRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlyingFrisbees implements ModInitializer {
	public static final String MOD_ID = "flying_frisbees";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final class Tags {
		public static final TagKey<Item> FETCHABLE = TagKey.of(RegistryKeys.ITEM, Identifier.of(MOD_ID, "fetchable"));
	}

	@Override
	public void onInitialize() {
		FFRegistry.init();
		LOGGER.info("Flying Frisbees flying freely for fun. Fetch!");
	}
	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}
}
