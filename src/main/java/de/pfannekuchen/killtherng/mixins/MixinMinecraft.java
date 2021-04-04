package de.pfannekuchen.killtherng.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import de.pfannekuchen.killtherng.utils.EntityRandom;
import de.pfannekuchen.killtherng.utils.ItemRandom;
import de.pfannekuchen.killtherng.utils.WorldRandom;
import net.minecraft.client.Minecraft;

/**
 * Change the Seed of every Random, every keyboard input
 * @author Pancake
 */
@Mixin(Minecraft.class)
public abstract class MixinMinecraft {
	
	/**
	 * Stuff needed to calculate the next seed
	 * @author Pancake
	 */
    private static final long multiplier = 0x5DEECE66DL;
    private static final long addend = 0xBL;
    private static final long mask = (1L << 48) - 1;
	
    /**
     * Update the seed for every Keyboard Input
     * @param ci Mixin
     */
	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;dispatchKeypresses()V"), method = "runTickKeyboard")
	public void injectRunTickKeyboard(CallbackInfo ci) {
		WorldRandom.update.set(true);
		EntityRandom.currentSeed.set((EntityRandom.currentSeed.get() * multiplier + addend) & mask);
		ItemRandom.currentSeed.set((ItemRandom.currentSeed.get() * multiplier + addend) & mask);
	}
	
}
