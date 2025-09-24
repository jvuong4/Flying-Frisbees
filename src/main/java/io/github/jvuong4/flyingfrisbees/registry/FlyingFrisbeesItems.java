package io.github.jvuong4.flyingfrisbees.registry;

import com.google.common.collect.ImmutableList;
import io.github.jvuong4.flyingfrisbees.FlyingFrisbees;
import io.github.jvuong4.flyingfrisbees.item.*;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.block.dispenser.ProjectileDispenserBehavior;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import io.github.jvuong4.flyingfrisbees.entity.FrisbeeEntity;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;
import net.minecraft.item.ProjectileItem;

public class FlyingFrisbeesItems {
	private static final List<Item> allItems = new ArrayList<>();

	public static final Frisbee FRISBEE = registerFrisbee("frisbee", Frisbee::new);
	public static final Frisbee WHITE_FRISBEE = registerFrisbee("white_frisbee", Frisbee::new);
	public static final Frisbee LIGHT_GRAY_FRISBEE = registerFrisbee("light_gray_frisbee", Frisbee::new);
	public static final Frisbee GRAY_FRISBEE = registerFrisbee("gray_frisbee", Frisbee::new);
	public static final Frisbee BLACK_FRISBEE = registerFrisbee("black_frisbee", Frisbee::new);
	public static final Frisbee BROWN_FRISBEE = registerFrisbee("brown_frisbee", Frisbee::new);
	public static final Frisbee RED_FRISBEE = registerFrisbee("red_frisbee", Frisbee::new);
	public static final Frisbee ORANGE_FRISBEE = registerFrisbee("orange_frisbee", Frisbee::new);
	public static final Frisbee YELLOW_FRISBEE = registerFrisbee("yellow_frisbee", Frisbee::new);
	public static final Frisbee LIME_FRISBEE = registerFrisbee("lime_frisbee", Frisbee::new);
	public static final Frisbee GREEN_FRISBEE = registerFrisbee("green_frisbee", Frisbee::new);
	public static final Frisbee CYAN_FRISBEE = registerFrisbee("cyan_frisbee", Frisbee::new);
	public static final Frisbee LIGHT_BLUE_FRISBEE = registerFrisbee("light_blue_frisbee", Frisbee::new);
	public static final Frisbee BLUE_FRISBEE = registerFrisbee("blue_frisbee", Frisbee::new);
	public static final Frisbee PURPLE_FRISBEE = registerFrisbee("purple_frisbee", Frisbee::new);
	public static final Frisbee MAGENTA_FRISBEE = registerFrisbee("magenta_frisbee", Frisbee::new);
	public static final Frisbee PINK_FRISBEE = registerFrisbee("pink_frisbee", Frisbee::new);
	public static final Frisbee DELTA_FRISBEE = registerFrisbee("delta_frisbee", Frisbee::new);

	public static final Frisboomerang FRISBOOMERANG = registerFrisboomerang("loyal_frisbee", Frisboomerang::new);
	public static final Frisboomerang YEEHAW_FRISBEE = registerFrisboomerang("lasso_frisbee", YeehawFrisbee::new);

	public static final Frisbee YOINK_FRISBEE = registerFrisbee("capture_frisbee", YoinkFrisbee::new,1);
	public static final Frisbee FRISBOOM = registerFrisbee("bomb_frisbee", Frisboom::new,2);

	private static Frisbee registerFrisbee(String name, Function<Item.Settings, Item> function)
	{
		return registerFrisbee(name,function,0);
	}

	private static Frisbee registerFrisbee(String name, Function<Item.Settings, Item> function, int specialEffect)
	{
		Item item = Registry.register(Registries.ITEM, FlyingFrisbees.id(name),
			function.apply(new Item.Settings()
				.maxCount(1)
				.equippableUnswappable(EquipmentSlot.HEAD)
				//.useCooldown(0.5f)
				.registryKey(RegistryKey.of(RegistryKeys.ITEM, FlyingFrisbees.id(name)))));

		allItems.add(item);
		DispenserBlock.registerBehavior(item, new ProjectileDispenserBehavior(item) {
			public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
				Direction direction = (Direction)pointer.state().get(DispenserBlock.FACING);
				//BlockPos blockPos = pointer.pos().offset(direction);

				ServerWorld serverWorld = pointer.world();

				if(item instanceof ProjectileItem projectileItem) {
					ProjectileItem.Settings projectileSettings = projectileItem.getProjectileSettings();

					Position position = projectileSettings.positionFunction().getDispensePosition(pointer, direction);
					FrisbeeEntity frisbee = (FrisbeeEntity)ProjectileEntity.spawnWithVelocity(projectileItem.createEntity(serverWorld, position, stack, direction), serverWorld, stack, (double)direction.getOffsetX(), (double)direction.getOffsetY(), (double)direction.getOffsetZ(), projectileSettings.power(), projectileSettings.uncertainty()*0.2f);
					frisbee.constructFrisbee(true);
					switch(specialEffect)
					{
						case 0:
							break;
						case 1: //capturing
							frisbee.isCapturing = true;
							break;
						case 2: //explosive
							frisbee.isExploding = true;
							break;
					}
					stack.decrement(1);
				}
				return stack;
			}
		});
		return (Frisbee)item;
	}

	private static Frisboomerang registerFrisboomerang(String name, Function<Item.Settings, Item> function)
	{
		Item item = Registry.register(Registries.ITEM, FlyingFrisbees.id(name),
			function.apply(new Item.Settings()
				.maxCount(1)
				.equippableUnswappable(EquipmentSlot.HEAD)
				//.useCooldown(0.5f)
				.registryKey(RegistryKey.of(RegistryKeys.ITEM, FlyingFrisbees.id(name)))));

		allItems.add(item);
		//Loyal Frisbees cannot be dispensed.
		return (Frisboomerang)item;
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
