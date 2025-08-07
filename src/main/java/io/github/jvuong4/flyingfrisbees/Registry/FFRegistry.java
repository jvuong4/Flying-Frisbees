package io.github.jvuong4.flyingfrisbees.Registry;

import io.github.jvuong4.flyingfrisbees.Entity.FrisbeeEntity;
import io.github.jvuong4.flyingfrisbees.FlyingFrisbees;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
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
		FFEntities.init();

		Registry.register(Registries.ITEM_GROUP, FFItems.ITEM_GROUP_KEY, FFItems.ITEM_GROUP);

		ItemGroupEvents.modifyEntriesEvent(FFItems.ITEM_GROUP_KEY).register(group -> FFItems.getAllItems().forEach(group::add));
	}
}
