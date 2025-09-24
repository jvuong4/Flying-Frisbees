package io.github.jvuong4.flyingfrisbees.entity;

import io.github.jvuong4.flyingfrisbees.FlyingFrisbees;
import io.github.jvuong4.flyingfrisbees.item.Frisboom;
import io.github.jvuong4.flyingfrisbees.item.Frisboomerang;
import io.github.jvuong4.flyingfrisbees.item.YoinkFrisbee;
import io.github.jvuong4.flyingfrisbees.registry.FlyingFrisbeesDamageTypes;
import io.github.jvuong4.flyingfrisbees.registry.FlyingFrisbeesEntities;
import io.github.jvuong4.flyingfrisbees.registry.FlyingFrisbeesItems;
import io.github.jvuong4.flyingfrisbees.registry.FlyingFrisbeesRegistry;
import net.minecraft.component.type.FireworkExplosionComponent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.item.Item;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
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
import net.minecraft.entity.player.PlayerEntity;

import java.util.Iterator;
import java.util.List;

public class FrisbeeEntity extends PersistentProjectileEntity implements GeoEntity {
	//public static final DataTicket<Integer> FRISBEE_TYPE = DataTicket.create("frisbee_type", Integer.class);

	protected static final RawAnimation SPINNING_ANIM = RawAnimation.begin().thenLoop("animation.frisbee.spinning");
	protected static final RawAnimation STOPPED_ANIM = RawAnimation.begin().thenLoop("animation.frisbee.stopped");

	protected final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

	protected static final boolean DEFAULT_DEALT_DAMAGE = false;
	protected boolean dealtDamage = false;
	protected boolean isSpinning = true;
	protected int life;

	public boolean isLoyal;
	private int timeInGround;
	private boolean isReturning = false;

	public boolean isCapturing;
	public boolean isExploding;

	public boolean isDispensed = false;


	public FrisbeeEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
		super(entityType, world);
		Item item = this.getItemStack().getItem();
		this.isLoyal = (item instanceof Frisboomerang);
		//this.dataTracker.set(LOYALTY, (byte)0);
		constructFrisbee(true);
	}

	public FrisbeeEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world, boolean loyal) {
		super(entityType, world);
		this.isLoyal = loyal;
		constructFrisbee(true);
	}

	public FrisbeeEntity(World world, LivingEntity owner, ItemStack stack) {
		super(FlyingFrisbeesEntities.FRISBEE, owner, world, stack, (ItemStack) null);
		Item item = this.getItemStack().getItem();
		this.isLoyal = (item instanceof Frisboomerang);
		constructFrisbee(true);
	}
	public FrisbeeEntity(World world, LivingEntity owner, ItemStack stack, boolean loyal) {
		super(FlyingFrisbeesEntities.FRISBEE, owner, world, stack, (ItemStack) null);
		this.isLoyal = loyal;
		constructFrisbee(true);
	}

	public FrisbeeEntity(World world, double x, double y, double z, ItemStack stack) {
		super(FlyingFrisbeesEntities.FRISBEE, x, y, z, world, stack, stack);
		Item item = this.getItemStack().getItem();
		this.isLoyal = (item instanceof Frisboomerang);
		constructFrisbee(true);
	}

	public FrisbeeEntity(World world, double x, double y, double z, ItemStack stack, boolean loyal) {
		super(FlyingFrisbeesEntities.FRISBEE, x, y, z, world, stack, stack);
		this.isLoyal = loyal;
		constructFrisbee(true);
	}

	public void constructFrisbee() {
		isSpinning = true;
		onSetItemStack(getItemStack());
		Item item = this.getItemStack().getItem();
		//isLoyal = (item instanceof Frisboomerang);
		if(this.isLoyal)
		{
			FlyingFrisbees.LOGGER.info("it's loyal!");
		}
		else
		{
			FlyingFrisbees.LOGGER.info("not loyal!");
		}
		isCapturing = (item instanceof YoinkFrisbee);
		isExploding = (item instanceof Frisboom);
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
		return new ItemStack(FlyingFrisbeesItems.FRISBEE);
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
				&& (!this.isLoyal || (this.isLoyal && !isReturning))
				//check if entity not already riding something
				&& !entity.hasVehicle()
				//check if in whitelist or if it is a MobEntity or LivingEntity
				&& (entity.getType().isIn(FlyingFrisbees.Tags.FRISBEE_WHITELIST) || entity instanceof MobEntity || entity.getType() == EntityType.PHANTOM)
				//frisbee catch logic
				&& (
				//capturing frisbees ignore ground and height checks
				isCapturing ||
					//Modfest dispensed frisbee tweak
					(isDispensed && !entity.isOnGround()) ||
					entity.getType() == EntityType.PHANTOM ||
					(!entity.isOnGround() && this.getY() - entity.getY() < 0.75 + (double) entity.getDimensions(this.getPose()).height() * 0.25)
			)
		) {
			entity.startRiding(this);
			this.setVelocity(this.getVelocity().add(this.getVelocity().getX() * 1.5, this.getVelocity().getY() + 0.1, this.getVelocity().getZ() * 1.5));
			return;
		}


		//Catch Logic
		//Loyal and Capture frisbees cannot be caught. Exploding frisbees can! scary...
		if (!this.isLoyal && !isCapturing && !isDispensed && facing && entity instanceof PlayerEntity living) {
			EquipmentSlot slot = entityHitResult.getPos().distanceTo(entity.getEyePos()) < 0.5 &&
				living.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty() ? EquipmentSlot.MAINHAND
				: living.getEquippedStack(EquipmentSlot.OFFHAND).isEmpty() ? EquipmentSlot.OFFHAND
				: living.getEquippedStack(EquipmentSlot.HEAD).isEmpty() ? EquipmentSlot.HEAD
				: null;

			if (slot != null) {
				living.equipStack(slot, this.getItemStack());
				this.setRemoved(RemovalReason.DISCARDED);
				return;
			}
		}

		//this thing's weak as hell lmao
		float f = 1.0F;
		Entity entity2 = this.getOwner();
		World var7 = this.getWorld();
		DamageSource damageSource; // = this.getDamageSources().trident(this, (Entity)(entity2 == null ? this : entity2));

		this.dealtDamage = true;
		ServerWorld serverWorld = (ServerWorld) var7;
		switch (this.getRandom().nextBetween(1, 3) + (isExploding ? 3 : 0)) {
			case 1:
				damageSource = var7.getDamageSources().create(FlyingFrisbeesDamageTypes.FRISBEE_KEY, this, (Entity) (entity2 == null ? this : entity2));
				break;
			case 2:
				damageSource = var7.getDamageSources().create(FlyingFrisbeesDamageTypes.FRISBEE2_KEY, this, (Entity) (entity2 == null ? this : entity2));
				break;
			case 3:
				damageSource = var7.getDamageSources().create(FlyingFrisbeesDamageTypes.FRISBEE3_KEY, this, (Entity) (entity2 == null ? this : entity2));
				break;
			case 4:
				damageSource = var7.getDamageSources().create(FlyingFrisbeesDamageTypes.FRISBEE4_KEY, this, (Entity) (entity2 == null ? this : entity2));
				break;
			case 5:
				damageSource = var7.getDamageSources().create(FlyingFrisbeesDamageTypes.FRISBEE5_KEY, this, (Entity) (entity2 == null ? this : entity2));
				break;
			case 6:
				damageSource = var7.getDamageSources().create(FlyingFrisbeesDamageTypes.FRISBEE6_KEY, this, (Entity) (entity2 == null ? this : entity2));
				break;
			default:
				damageSource = var7.getDamageSources().create(FlyingFrisbeesDamageTypes.FRISBEE_KEY, this, (Entity) (entity2 == null ? this : entity2));
				break;
		}

		if (isExploding) {
			explode(serverWorld, damageSource);
			//10% chance to detroy an exploding frisbee when it explodes :(
			if (this.getRandom().nextFloat() < 0.1F) {
				this.discard();
				return;
			}
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
			if (isExploding)
				explodeSelf();
			if (!this.isLoyal)
				isSpinning = false;
			if (timeInGround++ > 4)
				this.dealtDamage = true;
		}

		if (this.isLoyal) {
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
	}

	private void explode(ServerWorld world, DamageSource source) {
		float damage = this.getRandom().nextFloat() * 3.0F + 5.5F;
		Entity shooter = this.getOwner();
		if (shooter instanceof LivingEntity) {
			shooter.damage(world, source, damage);

			//double d = 5.0; //this doesnt seem to be used...
			this.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE.value(), 1.0F, 1.0F);
			Vec3d vec3d = this.getPos();
			List<LivingEntity> list2 = this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(5.0));
			Iterator var8 = list2.iterator();

			while (true) {
				LivingEntity livingEntity;
				do {
					do {
						if (!var8.hasNext()) {
							return;
						}

						livingEntity = (LivingEntity) var8.next();
					} while (livingEntity == shooter);
				} while (this.squaredDistanceTo(livingEntity) > 25.0);

				boolean bl = false;

				for (int i = 0; i < 2; ++i) {
					Vec3d vec3d2 = new Vec3d(livingEntity.getX(), livingEntity.getBodyY(0.5 * (double) i), livingEntity.getZ());
					HitResult hitResult = this.getWorld().raycast(new RaycastContext(vec3d, vec3d2, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this));
					if (hitResult.getType() == HitResult.Type.MISS) {
						bl = true;
						break;
					}
				}

				if (bl) {
					float g = damage * (float) Math.sqrt((5.0 - (double) this.distanceTo(livingEntity)) / 5.0);
					livingEntity.damage(world, source, g);
				}
			}
		}
	}

	private void explodeSelf() {
		World var21 = this.getWorld();
		if (var21 instanceof ServerWorld) {
			ServerWorld serverWorld3 = (ServerWorld) var21;
			DamageSource damageSource;
			Entity entity2 = this.getOwner();
			switch (this.getRandom().nextBetween(1, 3) + (isExploding ? 3 : 0)) {
				case 4:
					damageSource = serverWorld3.getDamageSources().create(FlyingFrisbeesDamageTypes.FRISBEE4_KEY, this, (Entity) (entity2 == null ? this : entity2));
					break;
				case 5:
					damageSource = serverWorld3.getDamageSources().create(FlyingFrisbeesDamageTypes.FRISBEE5_KEY, this, (Entity) (entity2 == null ? this : entity2));
					break;
				case 6:
					damageSource = serverWorld3.getDamageSources().create(FlyingFrisbeesDamageTypes.FRISBEE6_KEY, this, (Entity) (entity2 == null ? this : entity2));
					break;
				default:
					damageSource = serverWorld3.getDamageSources().create(FlyingFrisbeesDamageTypes.FRISBEE4_KEY, this, (Entity) (entity2 == null ? this : entity2));
					break;
			}
			explode(serverWorld3, damageSource);
			//10% chance to destroy an exploding frisbee when it explodes :(
			if (!(this.getRandom().nextFloat() < 0.1F)) {
				this.dropStack(serverWorld3, this.asItemStack().copy(), 0.1F);
			}
		}
		this.discard();
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
		++this.life;
		//frisbees will only last for 40 seconds before dropping as an item
		if (this.life >= 800 && !this.isLoyal) {
			World var21 = this.getWorld();
			if (var21 instanceof ServerWorld) {
				ServerWorld serverWorld3 = (ServerWorld) var21;
				this.dropStack(serverWorld3, this.asItemStack().copy(), 0.1F);
			}
			this.discard();
		}

	}

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
		controllers.add(
			// Add our animation controller. Loyal Frisbees always spin
			new AnimationController<>(1, state -> state.setAndContinue((this.isSpinning || this.isLoyal) ? SPINNING_ANIM : STOPPED_ANIM))
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
