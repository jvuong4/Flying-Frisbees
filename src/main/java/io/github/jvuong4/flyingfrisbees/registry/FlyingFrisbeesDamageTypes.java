package io.github.jvuong4.flyingfrisbees.registry;

import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.entry.RegistryEntry;
import io.github.jvuong4.flyingfrisbees.FlyingFrisbees;
import net.minecraft.world.World;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.registry.Registry;

public class FlyingFrisbeesDamageTypes {

	public static final RegistryKey<DamageType> FRISBEE_KEY = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, FlyingFrisbees.id("frisbee"));
	public static final RegistryKey<DamageType> FRISBEE2_KEY = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, FlyingFrisbees.id("frisbee2"));
	public static final RegistryKey<DamageType> FRISBEE3_KEY = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, FlyingFrisbees.id("frisbee3"));
	public static final RegistryKey<DamageType> FRISBEE4_KEY = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, FlyingFrisbees.id("frisbee4"));
	public static final RegistryKey<DamageType> FRISBEE5_KEY = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, FlyingFrisbees.id("frisbee5"));
	public static final RegistryKey<DamageType> FRISBEE6_KEY = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, FlyingFrisbees.id("frisbee6"));

	public static void init()
	{

	}
}
