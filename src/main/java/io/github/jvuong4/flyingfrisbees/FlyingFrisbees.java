package io.github.jvuong4.flyingfrisbees;

import io.github.jvuong4.flyingfrisbees.registry.FlyingFrisbeesRegistry;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.EntityTypeTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlyingFrisbees implements ModInitializer {
	public static final String MOD_ID = "flying_frisbees";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final class Tags {
		public static final TagKey<Item> FETCHABLE = TagKey.of(RegistryKeys.ITEM, Identifier.of(MOD_ID, "fetchable"));
		public static final TagKey<Item> FRISBEE = TagKey.of(RegistryKeys.ITEM, Identifier.of(MOD_ID, "frisbee"));
		public static final TagKey<EntityType<?>> FRISBEE_BLACKLIST = TagKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(MOD_ID, "frisbee_catch_blacklist"));
		public static final TagKey<EntityType<?>> FRISBEE_WHITELIST = TagKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(MOD_ID, "frisbee_catch_whitelist"));
	}

	@Override
	public void onInitialize() {
		FlyingFrisbeesRegistry.init();
		LOGGER.info("Flying Frisbees flying freely for fun. Fetch!");
	}
	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}
}
