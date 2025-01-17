package dev.abyss.client.mixins.client.renderer;

import com.mojang.blaze3d.platform.GlStateManager;
import dev.abyss.client.api.cosmetic.CosmeticManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.client.render.entity.feature.CapeFeatureRenderer;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(CapeFeatureRenderer.class)
public class MixinCapeFeatureRenderer {

    @Shadow @Final private PlayerEntityRenderer playerRenderer;

    /**
     * @author iLofiz
     * @reason Cloak Rendering
     */
    @Overwrite
    public void render(AbstractClientPlayerEntity abstractClientPlayerEntity, float f, float g, float h, float i, float j, float k, float l) {

        if (abstractClientPlayerEntity.canRenderCapeTexture() && !abstractClientPlayerEntity.isInvisible() && abstractClientPlayerEntity.isPartVisible(PlayerModelPart.CAPE) && abstractClientPlayerEntity.getSkinId() != null && abstractClientPlayerEntity.getName().equals(MinecraftClient.getInstance().player.getName())) {

            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

            if(CosmeticManager.getCurrentCloak() != null) {
                this.playerRenderer.bindTexture(CosmeticManager.getCurrentCloak().getLocation());
            } else {
                this.playerRenderer.bindTexture(abstractClientPlayerEntity.getSkinId());
            }

            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0F, 0.0F, 0.125F);
            double d = abstractClientPlayerEntity.capeX
                    + (abstractClientPlayerEntity.prevCapeX - abstractClientPlayerEntity.capeX) * (double)h
                    - (abstractClientPlayerEntity.prevX + (abstractClientPlayerEntity.x - abstractClientPlayerEntity.prevX) * (double)h);
            double e = abstractClientPlayerEntity.capeY
                    + (abstractClientPlayerEntity.prevCapeY - abstractClientPlayerEntity.capeY) * (double)h
                    - (abstractClientPlayerEntity.prevY + (abstractClientPlayerEntity.y - abstractClientPlayerEntity.prevY) * (double)h);
            double m = abstractClientPlayerEntity.capeZ
                    + (abstractClientPlayerEntity.prevCapeZ - abstractClientPlayerEntity.capeZ) * (double)h
                    - (abstractClientPlayerEntity.prevZ + (abstractClientPlayerEntity.z - abstractClientPlayerEntity.prevZ) * (double)h);
            float n = abstractClientPlayerEntity.prevBodyYaw + (abstractClientPlayerEntity.bodyYaw - abstractClientPlayerEntity.prevBodyYaw) * h;
            double o = MathHelper.sin(n * (float) Math.PI / 180.0F);
            double p = (-MathHelper.cos(n * (float) Math.PI / 180.0F));
            float q = (float)e * 10.0F;
            q = MathHelper.clamp(q, -6.0F, 32.0F);
            float r = (float)(d * o + m * p) * 100.0F;
            float s = (float)(d * p - m * o) * 100.0F;
            if (r < 0.0F) {
                r = 0.0F;
            }

            float t = abstractClientPlayerEntity.prevStrideDistance + (abstractClientPlayerEntity.strideDistance - abstractClientPlayerEntity.prevStrideDistance) * h;
            q += MathHelper.sin(
                    (abstractClientPlayerEntity.prevHorizontalSpeed + (abstractClientPlayerEntity.horizontalSpeed - abstractClientPlayerEntity.prevHorizontalSpeed) * h)
                            * 6.0F
            )
                    * 32.0F
                    * t;
            if (abstractClientPlayerEntity.isSneaking()) {
                q += 25.0F;
            }

            GlStateManager.rotate(6.0F + r / 2.0F + q, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(s / 2.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.rotate(-s / 2.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
            this.playerRenderer.getModel().renderCape(0.0625F);
            GlStateManager.popMatrix();
        }
    }
}
