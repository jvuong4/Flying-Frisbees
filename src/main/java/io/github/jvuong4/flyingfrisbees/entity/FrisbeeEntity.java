package io.github.jvuong4.flyingfrisbees.entity;

import io.github.jvuong4.flyingfrisbees.registry.FlyingFrisbeesEntities;
import io.github.jvuong4.flyingfrisbees.registry.FlyingFrisbeesItems;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.animatable.processing.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class FrisbeeEntity extends PersistentProjectileEntity implements GeoEntity {
	protected static final RawAnimation SPINNING_ANIM = RawAnimation.begin().thenLoop("animation.frisbee.spinning");
	protected static final RawAnimation STOPPED_ANIM = RawAnimation.begin().thenLoop("animation.frisbee.stopped");

	private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

	public static final ItemStack defaultItemStack = new ItemStack(FlyingFrisbeesItems.FRISBEE);
	private static final boolean DEFAULT_DEALT_DAMAGE = false;
	private boolean dealtDamage = false;
	private boolean isSpinning = true;

	public FrisbeeEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
		super(entityType, world);
		isSpinning = true;
	}

	public FrisbeeEntity(World world, LivingEntity owner, ItemStack stack) {
		super(FlyingFrisbeesEntities.FRISBEE, owner, world, stack, (ItemStack)null);
		isSpinning = true;
	}

	public FrisbeeEntity(World world, double x, double y, double z, ItemStack stack) {
		super(FlyingFrisbeesEntities.FRISBEE, x, y, z, world, stack, stack);
		isSpinning = true;
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

	@Nullable
	protected EntityHitResult getEntityCollision(Vec3d currentPosition, Vec3d nextPosition) {
		return this.dealtDamage ? null : super.getEntityCollision(currentPosition, nextPosition);
	}

	protected void onEntityHit(EntityHitResult entityHitResult) {
		if (getWorld().isClient) return;
		Entity entity = entityHitResult.getEntity();

		// catch in mouth
		if (entityHitResult.getPos().distanceTo(entity.getEyePos()) < 0.5 && entity instanceof LivingEntity living && living.getEquippedStack(EquipmentSlot.HEAD).isEmpty()) {
			living.equipStack(EquipmentSlot.HEAD, this.getItemStack());
			return;
		}

		//this thang weak as hell lmao
		float f = 1.0F;
		Entity entity2 = this.getOwner();
		//TODO: make a frisbee damage source
		DamageSource damageSource = this.getDamageSources().trident(this, (Entity)(entity2 == null ? this : entity2));
		World var7 = this.getWorld();
		//kind of unnecessary lol
		// h: crashes because getWeaponStack is null; try getItemStack() since you aren't using a bow or other launcher
//		if (var7 instanceof ServerWorld serverWorld) {
//			f = EnchantmentHelper.getDamage(serverWorld, this.getWeaponStack(), entity, damageSource, f);
//		}

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

	@Override
	public void tick() {
		super.tick();
		if(this.isInGround())
			isSpinning = false;
	}

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
		controllers.add(
			// Add our animation controller
			new AnimationController<>(10, state -> state.setAndContinue(this.isSpinning ? SPINNING_ANIM : STOPPED_ANIM))
		);
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.geoCache;
	}

	@Override
	public double getTick(@Nullable Object object) {
		return 0;
	}
}
