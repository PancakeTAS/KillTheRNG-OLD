package de.pfannekuchen.killtherng;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Random;

import de.pfannekuchen.killtherng.utils.EntityRandom;
import de.pfannekuchen.killtherng.utils.UnseededWorldRandom;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;

/**
 * This is the Mod for the Forge Loader
 * @author Pancake
 */
@Mod(canBeDeactivated = false, modid = "killtherng", name = "KillTheRng", clientSideOnly = true, version = "1.0.0")
public class KillTheRng {

	/** Displays Debug Information @author Pancake */
	public static final boolean ISDEV = true;
	
	/** Is Mod disabled */
	public static boolean ISDISABLED = false;
	
	/**
	 * 
	 * Do Stuff as Initialization of the Mod
	 * 
	 * @author Pancake
	 * @param event Initialization Event given by Forge
	 */
	@EventHandler
	public void init(FMLInitializationEvent event) {
		/* Check if any other TAS Mods are loaded */
		if (Loader.isModLoaded("lotas")) {
			System.err.println("\n\nLoTAS detected! Disabling KillTheRng as it is not compatible with LoTAS!\n\n");
			ISDISABLED = true;
			Loader.instance().runtimeDisableMod("killtherng");
			return;
		} else if (Loader.isModLoaded("tasmod")) {
			System.err.println("\n\nTASmod detected! Disabling TASmod as it is already packed into TASmod");
			ISDISABLED = true;
			Loader.instance().runtimeDisableMod("killtherng");
			return;
		}
		
		/* Override Math.random() and more */
		changeField("java.lang.Math$RandomNumberGeneratorHolder", "randomNumberGenerator", new EntityRandom(), true);
		changeField("net.minecraft.inventory.InventoryHelper", FMLLaunchHandler.isDeobfuscatedEnvironment() ? "RANDOM" : "field_180177_a", new EntityRandom(), true);
		changeField("net.minecraft.tileentity.TileEntityDispenser", FMLLaunchHandler.isDeobfuscatedEnvironment() ? "RNG" : "field_174913_f", new UnseededWorldRandom(), true);
		changeField("net.minecraft.util.math.MathHelper", FMLLaunchHandler.isDeobfuscatedEnvironment() ? "RANDOM" : "field_188211_c", new EntityRandom(), true);
		changeField("net.minecraft.tileentity.TileEntityEnchantmentTable", FMLLaunchHandler.isDeobfuscatedEnvironment() ? "rand" : "field_145923_r", new UnseededWorldRandom(), true);
		changeField("net.minecraft.util.datafix.fixes.ZombieProfToType", FMLLaunchHandler.isDeobfuscatedEnvironment() ? "RANDOM" : "field_190049_a", new EntityRandom(), true);
		changeField("net.minecraft.item.Item", FMLLaunchHandler.isDeobfuscatedEnvironment() ? "itemRand" : "field_77697_d", new EntityRandom(), false);
	}
	
	public static void changeField(String clazz, String name, Random rng, boolean isFinal) {
		try {
			Field field = Class.forName(clazz).getDeclaredField(name);			
			field.setAccessible(true);
			if (isFinal) {
				Field modifiersField = Field.class.getDeclaredField("modifiers");
				modifiersField.setAccessible(true);
				modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
			}
			field.set(null, rng);
		} catch (NoSuchFieldException | SecurityException | ClassNotFoundException | IllegalArgumentException
				| IllegalAccessException e) {
			e.printStackTrace();
			System.err.println("\n\nCouldn't hack " + clazz + ":" + name + " #7\n\n");
			FMLCommonHandler.instance().exitJava(-2, true);
		}
	}
	
}
