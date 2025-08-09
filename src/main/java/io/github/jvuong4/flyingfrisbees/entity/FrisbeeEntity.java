package io.github.jvuong4.flyingfrisbees.entity;

import io.github.jvuong4.flyingfrisbees.registry.FFEntities;
import io.github.jvuong4.flyingfrisbees.registry.FFItems;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ProjectileDeflection;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.PersistentProjectileEntity;

import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class FrisbeeEntity extends PersistentProjectileEntity {
	public static final ItemStack defaultItemStack = new ItemStack(FFItems.FRISBEE);
	private static final boolean DEFAULT_DEALT_DAMAGE = false;
	private boolean dealtDamage = false;

	public FrisbeeEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
		super(entityType, world);
	}

	public FrisbeeEntity(World world, LivingEntity owner, ItemStack stack) {
		super(FFEntities.FRISBEE, owner, world, stack, (ItemStack)null);
	}

	public FrisbeeEntity(World world, double x, double y, double z, ItemStack stack) {
		super(FFEntities.FRISBEE, x, y, z, world, stack, stack);
	}

	/*
	public FrisbeeEntity(EntityType<Entity> entityEntityType, World world) {
	}
	 */

	@Override
	protected ItemStack getDefaultItemStack()
	{
		return defaultItemStack;
	}
	public void setItem(ItemStack itemStack)
	{
		setStack(itemStack);
	}

	protected void onEntityHit(EntityHitResult entityHitResult) {
		Entity entity = entityHitResult.getEntity();
		//this thang weak as hell lmao
		float f = 1.0F;
		Entity entity2 = this.getOwner();
		//TODO: make a frisbee damage source
		DamageSource damageSource = this.getDamageSources().trident(this, (Entity)(entity2 == null ? this : entity2));
		World var7 = this.getWorld();
		//kind of unnecessary lol
		if (var7 instanceof ServerWorld serverWorld) {
			f = EnchantmentHelper.getDamage(serverWorld, this.getWeaponStack(), entity, damageSource, f);
		}

		this.dealtDamage = true;
		ServerWorld serverWorld = (ServerWorld)var7;
		if (entity.damage(serverWorld, damageSource, f)) {
			if (entity.getType() == EntityType.ENDERMAN) {
				return;
			}

			var7 = this.getWorld();
			if (var7 instanceof ServerWorld) {

				EnchantmentHelper.onTargetDamaged(serverWorld, entity, damageSource, this.getWeaponStack(), (item) -> this.kill(serverWorld));
			}

			if (entity instanceof LivingEntity) {
				LivingEntity livingEntity = (LivingEntity)entity;
				this.knockback(livingEntity, damageSource);
				this.onHit(livingEntity);
			}
		}

		this.deflect(ProjectileDeflection.SIMPLE, entity, this.getOwner(), false);
		this.setVelocity(this.getVelocity().multiply(0.02, 0.2, 0.02));
		this.playSound(SoundEvents.ITEM_TRIDENT_HIT, 1.0F, 1.0F);
	}
}
