package de.pfannekuchen.killtherng.mixins;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import de.pfannekuchen.killtherng.utils.StacktraceUtils;

import static de.pfannekuchen.killtherng.KillTheRng.ISDEV;

/**
 * This Mixin forces a Seed for all 'Random' Operations and logs them into a file.
 * @author Pancake
 */
@Mixin(Random.class)
public class MixinRandomManipulation {

	/**
	 * This @ModifyArg modifies the long {@link Random#setSeed(long)} which is supposed to set a new seed for the Random Number Generator.
	 * Instead it now replaces the seed with a new custom seed.
	 * 
	 * @param originalSeed This is the original seed that was supposed to be set for the next Pseudo-Random Action
	 * @return Return a custom seed instead of the original one.
	 */
	@ModifyArg(index = 0, method = "setSeed", at = @At(value = "INVOKE", target = "Ljava/util/concurrent/atomic/AtomicLong;set(J)V"))
	public long modifySeed(long originalSeed) {
		if (ISDEV) System.err.println(StacktraceUtils.getStacktrace() + " tried to set seed to " + originalSeed);
		return 0L;
	}
	
	/**
	 * This @ModifyArg modifies the constructor {@link Random#Random(long)} which is supposed to generate a seed for the Random Number Generator.
	 * Instead it now replaces the seed with a new custom seed.
	 * 
	 * @param originalSeed This is the original seed that was supposed to be set for the first Pseudo-Random Action
	 * @return Return a custom seed instead of the original one.
	 */
	@ModifyArg(index = 0, method = "<init>", at = @At(value = "INVOKE", target = "Ljava/util/concurrent/atomic/AtomicLong;<init>(J)V"))
	public long modifySeedInitializationCtr(long originalSeed) {
		if (ISDEV) System.err.println(StacktraceUtils.getStacktrace() + " initialized a class and tried to set the seed to " + originalSeed);
		return 0L;
	}
	
	/**
	 * This @ModifyArg modifies the constructor {@link Random#Random(long)} which is supposed to generate a seed for the Random Number Generator.
	 * Instead it now replaces the seed with a new custom seed.
	 * 
	 * @param originalSeed This is the original seed that was supposed to be set for the first Pseudo-Random Action
	 * @return Return a custom seed instead of the original one.
	 */
	@ModifyArg(index = 0, method = "<init>", at = @At(value = "INVOKE", target = "Ljava/util/concurrent/atomic/AtomicLong;<init>(J)V"))
	public long modifySeedInitializationCtrSubclass(long originalSeed) {
		if (ISDEV) System.err.println(StacktraceUtils.getStacktrace() + " initialized a 'Random' Subclass and tried to set the seed to " + originalSeed);
		return 0L;
	}
	
}
