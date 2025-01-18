package dev.abyss.client.mixins.client;

import dev.abyss.client.Abyss;
import dev.abyss.client.event.impl.KeyEvent;
import dev.abyss.client.event.impl.TickEvent;
import dev.abyss.client.skija.SkiaRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.NullTwitchStream;
import net.minecraft.client.util.TwitchStreamProvider;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {

    @Shadow private TwitchStreamProvider twitchStreamProvider;

    @Inject(method = "initializeGame", at = @At("TAIL"))
    private void initializeGame(CallbackInfo ci) {
        Abyss.getInstance().onPostInit();
    }

    @Inject(method = "stop", at = @At("HEAD"))
    private void stop(CallbackInfo ci) {
        Abyss.getInstance().onShutdown();
    }

    @Inject(method = "onResolutionChanged", at = @At("TAIL"))
    private void onResize(int w, int h, CallbackInfo ci) {
        SkiaRenderer.onResize(w, h);
    }

    @ModifyArg(method = "setPixelFormat", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/Display;setTitle(Ljava/lang/String;)V"))
    private String getTitle(String title) {
        return Abyss.getInstance().getTitle();
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void tick(CallbackInfo ci) {
        new TickEvent().call();

        if(Keyboard.getEventKeyState()) {
            new KeyEvent(Keyboard.getEventKey()).call();
        }
    }

    /**
     * @author thasave14
     * @reason Twitch stream doesn't even work nowadays, so it just slows down game launch and game performance
     */
    @Overwrite
    public void initializeStream() {
        this.twitchStreamProvider = new NullTwitchStream(new Throwable("Who the fuck uses twitch stream"));
    }
}
