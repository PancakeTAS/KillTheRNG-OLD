package de.pfannekuchen.killtherng.mixins.gui;

import java.io.IOException;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import de.pfannekuchen.killtherng.utils.EntityRandom;
import de.pfannekuchen.killtherng.utils.ItemRandom;
import de.pfannekuchen.killtherng.utils.SeededWorldRandom;
import de.pfannekuchen.killtherng.utils.UnseededWorldRandom;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiScreen;

/**
 * This adds the seeds to the IngameMenu and makes a simple way to edit it.
 * @author Pancake
 */
@Mixin(GuiIngameMenu.class)
public abstract class MixinIngameMenu extends GuiScreen {

	private int selectedIndex = 0;
	private String genSeed = "";
	private String currentSeed = "";
	
	int blinkCursor = 0;
	
	@Override
	public void updateScreen() {
		blinkCursor++;
		super.updateScreen();
	}
	
	/**
	 * Displays all seeds
	 * @author Pancake
	 * @param ci Mixin
	 */
	@Inject(at = @At("RETURN"), method = "drawScreen")
	public void drawMore(CallbackInfo ci) {
		drawString(mc.fontRenderer, "[TAB] to navigate, [DEL] to clear a seed.", 1, height - 10, 0xFFFFFF);
		
		// Draw Seeds
		drawString(mc.fontRenderer, "Entity Seed: " + (selectedIndex == 0 ? "> " : "") + EntityRandom.currentSeed + ((selectedIndex == 0 && (blinkCursor % 20 < 10)) ? "_" : ""), 1, height - 24, 0xFFFFFF);
		drawString(mc.fontRenderer, "Item Seed: " + (selectedIndex == 1 ? "> " : "") + ItemRandom.currentSeed + ((selectedIndex == 1 && (blinkCursor % 20 < 10)) ? "_" : ""), 1, height - 35, 0xFFFFFF);
		drawString(mc.fontRenderer, "World Seed: " + (selectedIndex == 2 ? "> " : "") + UnseededWorldRandom.currentSeed + ((selectedIndex == 2 && (blinkCursor % 20 < 10)) ? "_" : ""), 1, height - 46, 0xFFFFFF);
		drawString(mc.fontRenderer, "World Generation Seed: " + (selectedIndex == 3 ? "> " : "") + genSeed + ((selectedIndex == 3 && (blinkCursor % 20 < 10)) ? "_" : ""), 1, height - 57, 0xFFFFFF);
	}
	
	/**
	 * Watch for the keys 'TAB', 'BACK', 'DEL' and do something.
	 * And watch for all digits and add them to the current seed
	 */
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if (keyCode == 15) {
			selectedIndex--;
			if (selectedIndex == -1) selectedIndex = 3;
			
			switch (selectedIndex) {
			case 0:
				currentSeed = EntityRandom.currentSeed + "";
				break;
			case 1:
				currentSeed = ItemRandom.currentSeed + "";
				break;
			case 2:
				currentSeed = UnseededWorldRandom.currentSeed + "";
				break;
			case 3:
				currentSeed = genSeed;
				break;
			}
			
		} else if (keyCode == 14) {
			if (currentSeed.length() != 0) currentSeed = currentSeed.substring(0, currentSeed.length() - 1);
			
			switch (selectedIndex) {
			case 0:
				EntityRandom.currentSeed = Long.parseLong(currentSeed.isEmpty() ? "0" : currentSeed);
				break;
			case 1:
				ItemRandom.currentSeed = Long.parseLong(currentSeed.isEmpty() ? "0" : currentSeed);
				break;
			case 2:
				UnseededWorldRandom.currentSeed = Long.parseLong(currentSeed.isEmpty() ? "0" : currentSeed);
				break;
			case 3:
				genSeed = currentSeed;
				SeededWorldRandom.updateSeed(Long.parseLong(currentSeed.isEmpty() ? "0" : currentSeed));
				break;
			}
			
		} else if (keyCode == 211) {
			currentSeed = "";
			switch (selectedIndex) {
			case 0:
				EntityRandom.currentSeed = 0;
				break;
			case 1:
				ItemRandom.currentSeed = 0;
				break;
			case 2:
				UnseededWorldRandom.currentSeed = 0;
				break;
			case 3:
				genSeed = currentSeed;
				SeededWorldRandom.updateSeed(0);
				break;
			}
		} else if (Character.isDigit(typedChar) && (currentSeed.length() + 1) <= 18) {
			currentSeed += typedChar;
			
			switch (selectedIndex) {
			case 0:
				EntityRandom.currentSeed = Long.parseLong(currentSeed);
				break;
			case 1:
				ItemRandom.currentSeed = Long.parseLong(currentSeed);
				break;
			case 2:
				UnseededWorldRandom.currentSeed = Long.parseLong(currentSeed);
				break;
			case 3:
				genSeed = currentSeed;
				SeededWorldRandom.updateSeed(Long.parseLong(currentSeed));
				break;
			}
		} 
		super.keyTyped(typedChar, keyCode);
	}
	
}
