package io.github.jvuong4.flyingfrisbees.entity;

import io.github.jvuong4.flyingfrisbees.FlyingFrisbees;
import io.github.jvuong4.flyingfrisbees.registry.FlyingFrisbeesDamageTypes;
import io.github.jvuong4.flyingfrisbees.registry.FlyingFrisbeesEntities;
import io.github.jvuong4.flyingfrisbees.registry.FlyingFrisbeesItems;
import io.github.jvuong4.flyingfrisbees.registry.FlyingFrisbeesRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
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


import net.minecraft.registry.RegistryKeys;

public class FrisbeeEntity extends PersistentProjectileEntity implements GeoEntity {
	//public static final DataTicket<Integer> FRISBEE_TYPE = DataTicket.create("frisbee_type", Integer.class);

	protected static final RawAnimation SPINNING_ANIM = RawAnimation.begin().thenLoop("animation.frisbee.spinning");
	protected static final RawAnimation STOPPED_ANIM = RawAnimation.begin().thenLoop("animation.frisbee.stopped");

	private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

	public static final ItemStack defaultItemStack = new ItemStack(FlyingFrisbeesItems.FRISBEE);
	private static final boolean DEFAULT_DEALT_DAMAGE = false;
	private boolean dealtDamage = false;
	private boolean isSpinning = true;
	private int life;

	public FrisbeeEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
		super(entityType, world);
		constructFrisbee();
	}

	public FrisbeeEntity(World world, LivingEntity owner, ItemStack stack) {
		super(FlyingFrisbeesEntities.FRISBEE, owner, world, stack, (ItemStack)null);
		constructFrisbee();
	}

	public FrisbeeEntity(World world, double x, double y, double z, ItemStack stack) {
		super(FlyingFrisbeesEntities.FRISBEE, x, y, z, world, stack, stack);
		constructFrisbee();
	}

	public void constructFrisbee()
	{
		isSpinning = true;
		pickupType = PickupPermission.ALLOWED;
	}

	/*
	public static DefaultAttributeContainer.Builder createAttributes() {
		return
	}
	 */

	/*
	public FrisbeeEntity(EntityType<Entity> entityEntityType, World world) {
	}
	 */

	@Override
	protected double getGravity() {
		return 0.01;
	}

	@Override
	protected ItemStack getDefaultItemStack()
	{
		return defaultItemStack;
	}
	public void setItem(ItemStack itemStack)
	{
		setStack(itemStack);
		var path = itemStack.getRegistryEntry().getKey().get().getValue().getPath();
		this.setAttached(FlyingFrisbeesRegistry.FRISBEE_TEXTURE_ATTACHMENT, path);
	}

	@Nullable
	protected EntityHitResult getEntityCollision(Vec3d currentPosition, Vec3d nextPosition) {
		return this.dealtDamage ? null : super.getEntityCollision(currentPosition, nextPosition);
	}

	protected void onEntityHit(EntityHitResult entityHitResult) {
		if (getWorld().isClient) return;
		Entity entity = entityHitResult.getEntity();

		if (!entity.isOnGround() && this.getY() - entity.getY() < 0.5 + (double)entity.getHeight() / 0.25) {
			entity.startRiding(this);
			return;
		}

		// facing = motion vector points opposite of entity look vector
		boolean facing = this.getVelocity().dotProduct(entity.getRotationVector()) < 0;
		if (facing && entity instanceof LivingEntity living) {
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
		ServerWorld serverWorld = (ServerWorld)var7;
		switch(this.getRandom().nextBetween(1,3))
		{
			case 1:
				damageSource = var7.getDamageSources().create(FlyingFrisbeesDamageTypes.FRISBEE_KEY, this, (Entity)(entity2 == null ? this : entity2));
				break;
			case 2:
				damageSource = var7.getDamageSources().create(FlyingFrisbeesDamageTypes.FRISBEE2_KEY, this, (Entity)(entity2 == null ? this : entity2));
				break;
			case 3:
				damageSource = var7.getDamageSources().create(FlyingFrisbeesDamageTypes.FRISBEE3_KEY, this, (Entity)(entity2 == null ? this : entity2));
				break;
			default:
				damageSource = var7.getDamageSources().create(FlyingFrisbeesDamageTypes.FRISBEE_KEY, this, (Entity)(entity2 == null ? this : entity2));
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
				LivingEntity livingEntity = (LivingEntity)entity;
				this.knockback(livingEntity, damageSource);
				this.onHit(livingEntity);
			}
		}

		this.deflect(ProjectileDeflection.SIMPLE, entity, this.getOwner(), false);
		this.setVelocity(this.getVelocity().multiply(0.2, 0.8, 0.2));
		this.playSound(SoundEvents.ENTITY_FOX_HURT, 1.0F, 1.0F);
	}

	@Override
	public void tick() {
		super.tick();
		if(this.isInGround())
			isSpinning = false;
	}

	@Override
	protected void age() {
		++this.life;
		if (this.life >= 1200) {
			World var21 = this.getWorld();
			if (var21 instanceof ServerWorld) {
				ServerWorld serverWorld3 = (ServerWorld)var21;
				this.dropStack(serverWorld3, this.asItemStack(), 0.1F);
			}
			this.discard();
		}

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
