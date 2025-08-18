package io.github.jvuong4.flyingfrisbees.registry;

import io.github.jvuong4.flyingfrisbees.FlyingFrisbees;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class FlyingFrisbeesRegistry {

	public static final AttachmentType<String> FRISBEE_TEXTURE_ATTACHMENT = AttachmentRegistry.create(FlyingFrisbees.id("frisbee_texture"),
		builder -> builder.syncWith(PacketCodecs.STRING, AttachmentSyncPredicate.all()));

	public static void init() {
		FlyingFrisbeesItems.init();
		FlyingFrisbeesEntities.init();

		Registry.register(Registries.ITEM_GROUP, FlyingFrisbeesItems.ITEM_GROUP_KEY, FlyingFrisbeesItems.ITEM_GROUP);

		ItemGroupEvents.modifyEntriesEvent(FlyingFrisbeesItems.ITEM_GROUP_KEY).register(group -> FlyingFrisbeesItems.getAllItems().forEach(group::add));
	}
}
