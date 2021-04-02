package de.pfannekuchen.killtherng.utils;

import java.util.ArrayList;
import java.util.Random;

import de.pfannekuchen.killtherng.KillTheRng;
import net.minecraftforge.fml.common.FMLCommonHandler;

/**
 * This Random forces a Seed for all 'Random' Operations and logs them into a file.
 * @author Pancake
 */
public final class SeededWorldRandom extends Random {
	
	/**
	 * Set the serialVersionUID to be the same as in {@link Random} so that Deserialization is Compatible. 
	 * @author Pancake
	 */
	private static final long serialVersionUID = 3905348978240129619L;

	private static volatile ArrayList<Random> instances = new ArrayList<>();
	
	public SeededWorldRandom(long seed) {
		super(seed);
		instances.add(this);
	}
	
	public SeededWorldRandom() {
		this(8682522807148012L);
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
	
	/**
	 * Update the Seed for all Random instances, since they can change it themselves
	 * 
	 * @author Pancake
	 * @param newSeed Set the Seed of every World Random Instance
	 */
	public static void updateSeed(long newSeed) {
		instances.forEach((c) -> {
			c.setSeed(newSeed);
		});
	}
	
}
