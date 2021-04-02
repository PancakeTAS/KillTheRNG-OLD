package de.pfannekuchen.killtherng.mixins.redirect;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import de.pfannekuchen.killtherng.utils.HijackedRandom;
import net.minecraft.entity.EntityLivingBase;

@Mixin(EntityLivingBase.class)
public class RedirectEntityLivingBaseRandom {

	@Redirect(method = "decreaseAirSupply", at = @At(value = "INVOKE", target = "Ljava/lang/Math;random()D", ordinal = 0))
	public double redirectRandom() {
		return new HijackedRandom().nextDouble();
	}
	
	@Redirect(method = "decreaseAirSupply", at = @At(value = "INVOKE", target = "Ljava/lang/Math;random()D", ordinal = 1))
	public double redirectRandom2() {
		return new HijackedRandom().nextDouble();
	}
	
}
