package dev.abyss.client.mixins.util;

import dev.abyss.client.api.module.impl.hud.togglesprint.ToggleSprintMovement;
import net.minecraft.client.input.KeyboardInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardInput.class)
public class MixinKeyboardInput {

    @Inject(method = "tick", at = @At("TAIL"))
    private void tick(CallbackInfo ci) {
        ToggleSprintMovement.update();
    }
}
