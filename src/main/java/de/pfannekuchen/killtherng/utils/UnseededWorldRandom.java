package de.pfannekuchen.killtherng.utils;

import java.util.Random;

import de.pfannekuchen.killtherng.KillTheRng;
import net.minecraftforge.fml.common.FMLCommonHandler;

/**
 * This Random forces a Seed for all 'Random' Operations and logs them into a file.
 * @author Pancake
 */
public final class UnseededWorldRandom extends Random {
	
	public static long currentSeed = 0L;
	
	/**
	 * Set the serialVersionUID to be the same as in {@link Random} so that Deserialization is compatible. 
	 * @author Pancake
	 */
	private static final long serialVersionUID = 3905348978240129619L;
	
	/**
	 * Replace all constructors to make redirecting easier.
	 * @author Pancake
	 */
	public UnseededWorldRandom() {
		this(false);
    }
	
	/**
	 * Replace all constructors to make redirecting easier.
	 * @author Pancake
	 */
    public UnseededWorldRandom(final long seed) {
    	this(false);
    }
	
	/**
	 * Set the seed to the seed given by the player.
	 * @author Pancake
	 * @param seed Given seed for the Random Number Generator (ignored)
	 */
    private UnseededWorldRandom(boolean nullptr) {
    	setSeed(currentSeed);
    }
    
    /**
     * Idiot Check, in case my disabling didn't work
     * @author Pancake
     */
    @Override
    protected int next(int bits) {
    	if (KillTheRng.ISDISABLED) {
    		System.err.println("\n\nKillTheRng shouldn't have been enabled!\n\n");
    		FMLCommonHandler.instance().exitJava(-1, true);
    	}
    	return super.next(bits);
    }
    
	// Go into every public method and set the seed before executing the RNG action
	
	@Override
	public boolean nextBoolean() {
		setSeed(currentSeed);
		return super.nextBoolean();
	}
	
	@Override
	public double nextDouble() {
		setSeed(currentSeed);
		return super.nextDouble();
	}
	
	@Override
	public int nextInt() {
		setSeed(currentSeed);
		return super.nextInt();
	}
	
	@Override
	public int nextInt(int bound) {
		setSeed(currentSeed);
		return super.nextInt(bound);
	}
	
	@Override
	public float nextFloat() {
		setSeed(currentSeed);
		return super.nextFloat();
	}
	
	@Override
	public void nextBytes(byte[] bytes) {
		setSeed(currentSeed);
		super.nextBytes(bytes);
	}
	
	@Override
	public synchronized double nextGaussian() {
		setSeed(currentSeed);
		return super.nextGaussian();
	}
	
	@Override
	public long nextLong() {
		setSeed(currentSeed);
		return super.nextLong();
	}
	
}
