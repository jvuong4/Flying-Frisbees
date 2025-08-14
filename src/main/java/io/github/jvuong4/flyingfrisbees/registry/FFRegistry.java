package io.github.jvuong4.flyingfrisbees.registry;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class FFRegistry {

	public static void init() {
		FFItems.init();
		FFEntities.init();

		Registry.register(Registries.ITEM_GROUP, FFItems.ITEM_GROUP_KEY, FFItems.ITEM_GROUP);

		ItemGroupEvents.modifyEntriesEvent(FFItems.ITEM_GROUP_KEY).register(group -> FFItems.getAllItems().forEach(group::add));
	}
}
