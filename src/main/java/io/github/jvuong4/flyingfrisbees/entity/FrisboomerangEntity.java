package io.github.jvuong4.flyingfrisbees.entity;

import io.github.jvuong4.flyingfrisbees.FlyingFrisbees;
import io.github.jvuong4.flyingfrisbees.item.Frisboom;
import io.github.jvuong4.flyingfrisbees.item.Frisboomerang;
import io.github.jvuong4.flyingfrisbees.item.YoinkFrisbee;
import io.github.jvuong4.flyingfrisbees.registry.FlyingFrisbeesDamageTypes;
import io.github.jvuong4.flyingfrisbees.registry.FlyingFrisbeesEntities;
import io.github.jvuong4.flyingfrisbees.registry.FlyingFrisbeesItems;
import io.github.jvuong4.flyingfrisbees.registry.FlyingFrisbeesRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.animatable.processing.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Iterator;
import java.util.List;

public class FrisboomerangEntity extends PersistentProjectileEntity implements GeoEntity {
	protected static final RawAnimation SPINNING_ANIM = RawAnimation.begin().thenLoop("animation.frisbee.spinning");
	protected static final RawAnimation STOPPED_ANIM = RawAnimation.begin().thenLoop("animation.frisbee.stopped");

	protected final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

	protected static final boolean DEFAULT_DEALT_DAMAGE = false;
	protected boolean dealtDamage = false;
	protected boolean isSpinning = true;
	private int life;

	private int timeInGround;
	private boolean isReturning = false;
	public boolean isCapturing = false;

	public boolean isDispensed = false;



	public FrisboomerangEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
		super(entityType, world);
		constructFrisbee(true);
	}

	public FrisboomerangEntity(World world, LivingEntity owner, ItemStack stack) {
		super(FlyingFrisbeesEntities.FRISBOOMERANG, owner, world, stack, (ItemStack) null);
		constructFrisbee(true);
	}

	public FrisboomerangEntity(World world, double x, double y, double z, ItemStack stack) {
		super(FlyingFrisbeesEntities.FRISBOOMERANG, x, y, z, world, stack, stack);
		constructFrisbee(true);
	}

	public void constructFrisbee() {
		isSpinning = true;
		onSetItemStack(getItemStack());
		Item item = this.getItemStack().getItem();
		isCapturing = false;
		timeInGround = 0;
	}

	public void constructFrisbee(boolean pickupable) {
		if (pickupable)
			pickupType = PickupPermission.ALLOWED;
		else
			pickupType = PickupPermission.DISALLOWED;
		constructFrisbee();
	}

	@Override
	protected double getGravity() {
		return 0.01;
	}

	@Override
	protected ItemStack getDefaultItemStack() {
		return new ItemStack(FlyingFrisbeesItems.FRISBOOMERANG);
	}

	private void onSetItemStack(ItemStack itemStack) {
		var path = itemStack.getRegistryEntry().getKey().get().getValue().getPath();
		this.setAttached(FlyingFrisbeesRegistry.FRISBEE_TEXTURE_ATTACHMENT, path);
	}

	/**
	 * Since if the code itself depended on a now-missing method, other mods probably will.
	 */
	@Deprecated(forRemoval = true)
	public void setItem(ItemStack itemStack) {
		this.setStack(itemStack);
	}

	@Override
	public void setStack(ItemStack itemStack) {
		super.setStack(itemStack);
		this.onSetItemStack(itemStack);
	}

	@Nullable
	protected EntityHitResult getEntityCollision(Vec3d currentPosition, Vec3d nextPosition) {
		return this.dealtDamage ? null : super.getEntityCollision(currentPosition, nextPosition);
	}

	protected void onEntityHit(EntityHitResult entityHitResult) {
		if (getWorld().isClient) return;

		Entity entity = entityHitResult.getEntity();

		// facing = motion vector points opposite of entity look vector
		boolean facing = this.getVelocity().dotProduct(entity.getRotationVector()) < 0;

		if (//frisbee blacklist logic has greatest priority
			!entity.getType().isIn(FlyingFrisbees.Tags.FRISBEE_BLACKLIST)
				//loyal frisbees cannot catch entities while returning
				&& (!isReturning)
				//check if entity not already riding something
				&& !this.hasPassengers()
				//check if in whitelist or if it is a MobEntity or LivingEntity
				&& (entity.getType().isIn(FlyingFrisbees.Tags.FRISBEE_WHITELIST) || entity instanceof MobEntity || entity.getType() == EntityType.PHANTOM)
				//frisbee catch logic
				&& (
				//capturing frisbees ignore ground and height checks
				isCapturing ||
					entity.getType() == EntityType.PHANTOM ||
					(!entity.isOnGround() && this.getY() - entity.getY() < 0.75 + (double) entity.getDimensions(this.getPose()).height() * 0.25)
			)
		) {
			entity.startRiding(this);
			this.setVelocity(this.getVelocity().add(this.getVelocity().getX() * 1.5, this.getVelocity().getY() + 0.1, this.getVelocity().getZ() * 1.5));
			return;
		}

		//Loyal frisbees cannot be caught.

		//this thing's weak as hell lmao
		float f = 1.0F;
		Entity entity2 = this.getOwner();
		World var7 = this.getWorld();
		DamageSource damageSource; // = this.getDamageSources().trident(this, (Entity)(entity2 == null ? this : entity2));

		this.dealtDamage = true;
		ServerWorld serverWorld = (ServerWorld) var7;
		switch (this.getRandom().nextBetween(1, 3)) {
			case 1:
				damageSource = var7.getDamageSources().create(FlyingFrisbeesDamageTypes.FRISBEE_KEY, this, (Entity) (entity2 == null ? this : entity2));
				break;
			case 2:
				damageSource = var7.getDamageSources().create(FlyingFrisbeesDamageTypes.FRISBEE2_KEY, this, (Entity) (entity2 == null ? this : entity2));
				break;
			case 3:
				damageSource = var7.getDamageSources().create(FlyingFrisbeesDamageTypes.FRISBEE3_KEY, this, (Entity) (entity2 == null ? this : entity2));
				break;
			default:
				damageSource = var7.getDamageSources().create(FlyingFrisbeesDamageTypes.FRISBEE_KEY, this, (Entity) (entity2 == null ? this : entity2));
				break;
		}

		if (entity.damage(serverWorld, damageSource, f)) {
			if (entity.getType() == EntityType.ENDERMAN) {
				return;
			}

			var7 = this.getWorld();
			if (var7 instanceof ServerWorld) {

				EnchantmentHelper.onTargetDamaged(serverWorld, entity, damageSource, this.getWeaponStack(), (item) -> this.kill(serverWorld));
			}

			if (entity instanceof LivingEntity) {
				LivingEntity livingEntity = (LivingEntity) entity;
				this.knockback(livingEntity, damageSource);
				this.onHit(livingEntity);
			}
		}

		this.deflect(ProjectileDeflection.SIMPLE, entity, this.getOwner(), false);
		this.setVelocity(this.getVelocity().multiply(0.2, 0.8, 0.2));
		this.playSound(SoundEvents.ENTITY_ITEM_FRAME_ADD_ITEM, 1.0F, 1.0F);
	}

	@Override
	public void tick() {
		super.tick();

		if (this.isInGround()) {
			if (timeInGround++ > 4)
				this.dealtDamage = true;
		}

			Entity entity = this.getOwner();
			if (this.dealtDamage || this.isNoClip() && entity != null) {
				//drop as item when owner is dead
				if (!this.isOwnerAlive()) {
					World var4 = this.getWorld();
					if (var4 instanceof ServerWorld) {
						ServerWorld serverWorld = (ServerWorld) var4;
						if (this.pickupType == PickupPermission.ALLOWED) {
							this.dropStack(serverWorld, this.asItemStack(), 0.1F);
						}
					}
					this.discard();
				} else {
					//after returning to non-player entity
					if (!(entity instanceof PlayerEntity) && this.getPos().distanceTo(entity.getEyePos()) < (double) entity.getWidth() + 1.0) {
						this.discard();
						return;
					}

					this.setNoClip(true);
					Vec3d vec3d = entity.getEyePos().subtract(this.getPos());
					//this.setPos(this.getX(), this.getY() + vec3d.y * 0.015 * (double)1, this.getZ());
					this.setVelocity(this.getVelocity().multiply(0.95).add(vec3d.normalize().multiply(0.05)));
					//if (!isReturning) {
					//this.playSound(SoundEvents.ITEM_TRIDENT_RETURN, 10.0F, 1.0F);
					//isReturning = true;
					//isSpinning = true;
					//}


				}
		}
	}

	private boolean isOwnerAlive() {
		Entity entity = this.getOwner();
		if (entity != null && entity.isAlive()) {
			return !(entity instanceof ServerPlayerEntity) || !entity.isSpectator();
		} else {
			return false;
		}
	}

	@Override
	protected void age() {
	}

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
		controllers.add(
			// Add our animation controller. Loyal Frisbees always spin
			new AnimationController<>(1, state -> state.setAndContinue(SPINNING_ANIM))
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
