package io.github.jvuong4.flyingfrisbees.Mixin;

import io.github.jvuong4.flyingfrisbees.FlyingFrisbees;
import io.github.jvuong4.flyingfrisbees.Item.Frisbee;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//import io.github.jvuong4.flyingfrisbees; //.Registry;


@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
	private PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	//you can steal people's frisbees if they are equipped on the head slot :^) also, they have to be sneaking. that way, they can't just be stolen :(
	//this allows people to play fetch in a sense
	@Inject(method = "interact", at = @At("TAIL"), cancellable = true)
	public void interact(Entity entity, Hand hand, CallbackInfoReturnable<ActionResult> ci)
	{
		if (this.getStackInHand(hand) != ItemStack.EMPTY) return;

		if (entity instanceof LivingEntity e
			&& entity.isSneaking()
			&& e.getEquippedStack(EquipmentSlot.HEAD).isIn(FlyingFrisbees.Tags.FETCHABLE))
		{
			var s = e.getEquippedStack(EquipmentSlot.HEAD);
			var result = ((Frisbee)s.getRegistryEntry().value()).retrieve((PlayerEntity) (Entity) this, e, e.getEquippedStack(EquipmentSlot.HEAD),this.getActiveHand());
			if (result != null)
			{
				ci.setReturnValue(result);
				ci.cancel();
			}
		}
	}
}
