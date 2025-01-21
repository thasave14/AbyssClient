package dev.abyss.client.mixins.client.renderer.texture;

import dev.abyss.client.event.impl.ChangePackEvent;
import net.minecraft.client.texture.SpriteAtlasTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SpriteAtlasTexture.class)
public class MixinSpriteAtlasTexture {

    /**
     * @author GlideClient
     */
    @Inject(method = "load", at = @At("RETURN"))
    private void load(CallbackInfo ci) {
        new ChangePackEvent().call();
    }
}
