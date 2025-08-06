package io.github.jvuong4.flyingfrisbees.Registry;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class FFRegistry {
	public static void init() {
		FFItems.init();

		Registry.register(Registries.ITEM_GROUP, FFItems.ITEM_GROUP_KEY, FFItems.ITEM_GROUP);

		ItemGroupEvents.modifyEntriesEvent(FFItems.ITEM_GROUP_KEY).register(group -> FFItems.getAllItems().forEach(group::add));
	}
}
