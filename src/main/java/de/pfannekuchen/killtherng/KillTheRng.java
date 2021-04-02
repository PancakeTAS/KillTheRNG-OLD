package de.pfannekuchen.killtherng;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import de.pfannekuchen.killtherng.utils.HijackedRandom;
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
		/* Override Math.random() */
		try {
			Field field = Class.forName("java.lang.Math$RandomNumberGeneratorHolder").getDeclaredField("randomNumberGenerator");
			field.setAccessible(true);

			Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

			field.set(null, new HijackedRandom());
		} catch (Exception e) {
			e.printStackTrace();
			FMLCommonHandler.instance().exitJava(-2, true);
		}
	}
	
}
