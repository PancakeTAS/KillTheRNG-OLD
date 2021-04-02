package de.pfannekuchen.killtherng.mixins.redirect;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import de.pfannekuchen.killtherng.utils.UnseededWorldRandom;
import net.minecraft.block.BlockOre;

@Mixin(BlockOre.class)
public class RedirectBlockOre {

	@Redirect(method = "getExpDrop", at = @At(value = "NEW", target = "Ljava/util/Random;<init>()Ljava/util/Random;"))
	public Random redirectRandom() {
		return new UnseededWorldRandom();
	}
	
}
