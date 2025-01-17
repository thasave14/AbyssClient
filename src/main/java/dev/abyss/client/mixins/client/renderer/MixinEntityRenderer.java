package dev.abyss.client.mixins.client.renderer;

import dev.abyss.client.socket.SocketClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public abstract class MixinEntityRenderer<T extends Entity> {

    @Shadow public abstract TextRenderer getFontRenderer();

    @Inject(method = "renderLabelIfPresent", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/Tessellator;getBuffer()Lnet/minecraft/client/render/BufferBuilder;"))
    private void renderLabelIfPresent(T entity, String text, double x, double y, double z, int maxDistance, CallbackInfo ci) {

        if(entity instanceof AbstractClientPlayerEntity playerEntity) {

            if(SocketClient.isUser(playerEntity.getCustomName())) {
                MinecraftClient.getInstance().getTextureManager().bindTexture(new Identifier("abyss/logo.png"));
                DrawableHelper.drawTexture(-getFontRenderer().getStringWidth(playerEntity.getCustomName()) / 2 - 12, -2, 10, 10, 10, 10, 10, 10);
            }
        }
    }
}
