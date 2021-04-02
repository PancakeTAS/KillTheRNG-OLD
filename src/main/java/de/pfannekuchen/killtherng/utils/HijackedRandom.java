package de.pfannekuchen.killtherng.utils;

import java.lang.reflect.Field;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

/**
 * This Random forces a Seed for all 'Random' Operations and logs them into a file.
 * @author Pancake
 */
public final class HijackedRandom extends Random {
	
	/**
	 * Set the serialVersionUID to be the same as in {@link Random} so that deserialization is compatibile. 
	 * @author Pancake
	 */
	private static final long serialVersionUID = 3905348978240129619L;
	
	/**
	 * Replace all constructors to make redirecting easier.
	 * @author Pancake
	 */
	public HijackedRandom() {
		this(false);
    }
	
	/**
	 * Replace all constructors to make redirecting easier.
	 * @author Pancake
	 */
    public HijackedRandom(final long seed) {
    	this(false);
    }
	
	/**
	 * Set the seed to the seed given by the player.
	 * @author Pancake
	 * @param seed Given seed for the Random Number Generator (ignored)
	 */
    private HijackedRandom(boolean nullptr) {
    	try {
			Field private_seed_field = this.getClass().getSuperclass().getDeclaredField("seed");
			private_seed_field.setAccessible(true);
			private_seed_field.set(this, new AtomicLong(0L)); // TODO: Replace 0L with custom seed.
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
    }
    
	/**
	 * Removed the part where the original Code sets the seed of the Random Number Generator
	 * @author Pancake
	 */
	@Override
    synchronized public void setSeed(final long seed) {
		//this.seed.set(initialScramble(seed)); // Nah I don't fell like changing the seed to be honest.
        //haveNextNextGaussian = false;
    }
	
}
