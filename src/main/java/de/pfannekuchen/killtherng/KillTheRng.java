package de.pfannekuchen.killtherng;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Random;

import de.pfannekuchen.killtherng.utils.EntityRandom;
import de.pfannekuchen.killtherng.utils.UnseededWorldRandom;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

/**
 * This is the Mod for the Forge Loader
 * @author Pancake
 */
@Mod(canBeDeactivated = false, modid = "killtherng", name = "KillTheRng", clientSideOnly = true, version = "1.0.0")
public class KillTheRng {

	/** Displays Debug Information @author Pancake */
	public static final boolean ISDEV = true;
	
	/**
	 * 
	 * Do Stuff as Initialization of the Mod
	 * 
	 * @author Pancake
	 * @param event Init Event given by Forge
	 */
	@EventHandler
	public void init(FMLInitializationEvent event) {
		/* Override Math.random() and more */
		changeField("java.lang.Math$RandomNumberGeneratorHolder", "randomNumberGenerator", new EntityRandom(), true);
		changeField("net.minecraft.inventory.InventoryHelper", "RANDOM", new EntityRandom(), true);
		changeField("net.minecraft.tileentity.TileEntityDispenser", "RNG", new UnseededWorldRandom(), true);
		changeField("net.minecraft.util.math.MathHelper", "RANDOM", new EntityRandom(), true);
		changeField("net.minecraft.tileentity.TileEntityEnchantmentTable", "rand", new UnseededWorldRandom(), true);
		changeField("net.minecraft.util.datafix.fixes.ZombieProfToType", "RANDOM", new EntityRandom(), true);
		changeField("net.minecraft.item.Item", "itemRand", new EntityRandom(), false);
	}
	
	private void changeField(String clazz, String name, Random rng, boolean isFinal) {
		try {
			Field field = Class.forName(clazz).getDeclaredField(name);			
			field.setAccessible(true);
			if (isFinal) {
				Field modifiersField = Field.class.getDeclaredField("modifiers");
				modifiersField.setAccessible(true);
				modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
			}
			field.set(null, new EntityRandom());
		} catch (NoSuchFieldException | SecurityException | ClassNotFoundException | IllegalArgumentException
				| IllegalAccessException e) {
			e.printStackTrace();
			System.err.println("\n\nCouldn't hack " + clazz + ":" + name + " #7\n\n");
			FMLCommonHandler.instance().exitJava(-2, true);
		}
	}
	
}
