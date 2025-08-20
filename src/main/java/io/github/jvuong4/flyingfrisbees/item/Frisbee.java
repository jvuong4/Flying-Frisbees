package io.github.jvuong4.flyingfrisbees.item;

import io.github.jvuong4.flyingfrisbees.FlyingFrisbees;
import io.github.jvuong4.flyingfrisbees.entity.FrisbeeEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ProjectileItem;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class Frisbee extends Item implements ProjectileItem {
	public Frisbee(Item.Settings settings) {
		super(settings);
	}

	@Nullable
	public ActionResult retrieve(PlayerEntity player, LivingEntity target, ItemStack targetItemStack, Hand playerHand) {
		//player takes the targetItemStack from target and puts it in their playerHand
		ItemStack item = player.getStackInHand(playerHand);
		player.setStackInHand(playerHand, targetItemStack);
		target.equipStack(EquipmentSlot.HEAD,item);
		return ActionResult.PASS;
	}
	/*
	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {

		return super.finishUsing(stack, world, user);
	}
	*/

	@Override
	public ActionResult use(World world, PlayerEntity user, Hand hand) {
		FlyingFrisbees.LOGGER.info("Spawning Frisbee");
		ItemStack itemStack = user.getStackInHand(hand);
		world.playSound(
			null,
			user.getX(),
			user.getY(),
			user.getZ(),
			SoundEvents.ENTITY_SNOWBALL_THROW,
			SoundCategory.NEUTRAL,
			0.5F,
			0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F)
		);
		if (world instanceof ServerWorld serverWorld) {
			FrisbeeEntity frisbee = new FrisbeeEntity(world, user, itemStack);
			//ServerWorld serverWorld = (ServerWorld)world;
			//FrisbeeEntity frisbee = ProjectileEntity.spawnWithVelocity(FrisbeeEntity::new, serverWorld, itemStack, user, 0.0F, 1.5F, 1.0F);
			frisbee.setItem(itemStack.copy());
			frisbee.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.0F, 1.0F);
			world.spawnEntity(frisbee);
		}

		user.incrementStat(Stats.USED.getOrCreateStat(this));
		itemStack.decrementUnlessCreative(1, user);
		return ActionResult.SUCCESS;
	}

	@Override
	public ProjectileEntity createEntity(World world, Position pos, ItemStack stack, Direction direction) {
		FrisbeeEntity h = new FrisbeeEntity(world, pos.getX(), pos.getY(), pos.getZ(), stack);
		h.setItem(stack);
		return h;
	}
}
