package de.pfannekuchen.killtherng.mixins.redirect;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import de.pfannekuchen.killtherng.utils.UnseededWorldRandom;
import net.minecraft.command.CommandSpreadPlayers;

@Mixin(CommandSpreadPlayers.class)
public class RedirectCommandSpreadPlayers {

	@Redirect(method = "spread", at = @At(value = "NEW", target = "Ljava/util/Random;<init>()Ljava/util/Random;"))
	public Random redirectRandom() {
		return new UnseededWorldRandom();
	}
	
}
