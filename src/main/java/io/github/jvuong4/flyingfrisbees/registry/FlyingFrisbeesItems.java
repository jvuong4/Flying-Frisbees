package io.github.jvuong4.flyingfrisbees.registry;

import com.google.common.collect.ImmutableList;
import io.github.jvuong4.flyingfrisbees.FlyingFrisbees;
import io.github.jvuong4.flyingfrisbees.item.Frisbee;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class FlyingFrisbeesItems {
	private static final List<Item> allItems = new ArrayList<>();

	public static final Item FRISBEE = registerFrisbee("frisbee", Frisbee::new);
	public static final Item WHITE_FRISBEE = registerFrisbee("white_frisbee", Frisbee::new);
	public static final Item LIGHT_GRAY_FRISBEE = registerFrisbee("light_gray_frisbee", Frisbee::new);
	public static final Item GRAY_FRISBEE = registerFrisbee("gray_frisbee", Frisbee::new);
	public static final Item BLACK_FRISBEE = registerFrisbee("black_frisbee", Frisbee::new);
	public static final Item BROWN_FRISBEE = registerFrisbee("brown_frisbee", Frisbee::new);
	public static final Item RED_FRISBEE = registerFrisbee("red_frisbee", Frisbee::new);
	public static final Item ORANGE_FRISBEE = registerFrisbee("orange_frisbee", Frisbee::new);
	public static final Item YELLOW_FRISBEE = registerFrisbee("yellow_frisbee", Frisbee::new);
	public static final Item LIME_FRISBEE = registerFrisbee("lime_frisbee", Frisbee::new);
	public static final Item GREEN_FRISBEE = registerFrisbee("green_frisbee", Frisbee::new);
	public static final Item CYAN_FRISBEE = registerFrisbee("cyan_frisbee", Frisbee::new);
	public static final Item LIGHT_BLUE_FRISBEE = registerFrisbee("light_blue_frisbee", Frisbee::new);
	public static final Item BLUE_FRISBEE = registerFrisbee("blue_frisbee", Frisbee::new);
	public static final Item PURPLE_FRISBEE = registerFrisbee("purple_frisbee", Frisbee::new);
	public static final Item MAGENTA_FRISBEE = registerFrisbee("magenta_frisbee", Frisbee::new);
	public static final Item PINK_FRISBEE = registerFrisbee("pink_frisbee", Frisbee::new);


	private static Item registerFrisbee(String name, Function<Item.Settings, Item> function)
	{
		Item item = Registry.register(Registries.ITEM, FlyingFrisbees.id(name),
			function.apply(new Item.Settings()
				.maxCount(1)
				.equippableUnswappable(EquipmentSlot.HEAD)
				.registryKey(RegistryKey.of(RegistryKeys.ITEM, FlyingFrisbees.id(name)))));
		allItems.add(item);
		return item;
	}

	//ItemGroup Stuff :o

	public static final RegistryKey<ItemGroup> ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), FlyingFrisbees.id("item_group"));
	public static final ItemGroup ITEM_GROUP = FabricItemGroup.builder()
		.icon(FRISBEE::getDefaultStack)
		.displayName(Text.translatable("itemGroup." + FlyingFrisbees.MOD_ID))
		.build();

	public static List<Item> getAllItems() { return ImmutableList.copyOf(allItems); }

	public static void init() {}
}
