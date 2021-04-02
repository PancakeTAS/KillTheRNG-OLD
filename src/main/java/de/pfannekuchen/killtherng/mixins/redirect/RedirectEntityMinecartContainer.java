package de.pfannekuchen.killtherng.mixins.redirect;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import de.pfannekuchen.killtherng.utils.UnseededWorldRandom;
import net.minecraft.entity.item.EntityMinecartContainer;

@Mixin(EntityMinecartContainer.class)
public class RedirectEntityMinecartContainer {

	@Redirect(method = "addLoot", at = @At(value = "NEW", ordinal = 0, target = "Ljava/util/Random;<init>()Ljava/util/Random;"))
	public Random redirectRandom() {
		return new UnseededWorldRandom();
	}
	
	@Redirect(method = "addLoot", at = @At(value = "NEW", ordinal = 1, target = "Ljava/util/Random;<init>(J)Ljava/util/Random;"))
	public Random redirectRandom2(long originalSeed) {
		return new UnseededWorldRandom(originalSeed);
	}
	
}
