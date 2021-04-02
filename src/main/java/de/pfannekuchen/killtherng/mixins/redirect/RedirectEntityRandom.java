package de.pfannekuchen.killtherng.mixins.redirect;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import de.pfannekuchen.killtherng.utils.HijackedRandom;
import net.minecraft.entity.Entity;

@Mixin(Entity.class)
public class RedirectEntityRandom {

	@Redirect(method = "<init>", at = @At(value = "NEW", target = "Ljava/util/Random;<init>()Ljava/util/Random;"))
	public Random redirectRandom() {
		return new HijackedRandom();
	}
	
}
