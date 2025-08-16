package io.github.jvuong4.flyingfrisbees.registry;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class FlyingFrisbeesRegistry {

	public static void init() {
		FlyingFrisbeesItems.init();
		FlyingFrisbeesEntities.init();

		Registry.register(Registries.ITEM_GROUP, FlyingFrisbeesItems.ITEM_GROUP_KEY, FlyingFrisbeesItems.ITEM_GROUP);

		ItemGroupEvents.modifyEntriesEvent(FlyingFrisbeesItems.ITEM_GROUP_KEY).register(group -> FlyingFrisbeesItems.getAllItems().forEach(group::add));
	}
}
