package dev.abyss.client.mixins.client.renderer;

import com.mojang.blaze3d.platform.GlStateManager;
import dev.abyss.client.Abyss;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.UseAction;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(HeldItemRenderer.class)
public abstract class MixinHeldItemRenderer {

    @Shadow
    @Final
    private MinecraftClient client;

    @Shadow
    private ItemStack mainHand;

    @Shadow
    private float equipProgress;

    @Shadow
    private float lastEquipProgress;

    @Shadow
    protected abstract void rotate(float pitch, float yaw);

    @Shadow
    protected abstract void applyPlayerLighting(AbstractClientPlayerEntity player);

    @Shadow
    protected abstract void applyPlayerRotation(ClientPlayerEntity player, float tickDelta);

    @Shadow
    protected abstract void renderMap(AbstractClientPlayerEntity player, float pitch, float equipProgress, float swingProgress);

    @Shadow
    protected abstract void applyEquipAndSwingOffset(float equipProgress, float swingProgress);

    @Shadow
    protected abstract void applyEatOrDrinkTransformation(AbstractClientPlayerEntity player, float tickDelta);

    @Shadow
    protected abstract void applySwordBlockTransformation();

    @Shadow
    protected abstract void applyBowTransformation(float tickDelta, AbstractClientPlayerEntity player);

    @Shadow
    protected abstract void translateSwingProgress(float swingProgress);

    @Shadow
    public abstract void renderItem(LivingEntity entity, ItemStack stack, ModelTransformation.Mode renderMode);

    @Shadow
    protected abstract void renderArm(AbstractClientPlayerEntity player, float equipProgress, float swingProgress);

    /**
     * @author iLofiz
     * @reason 1.7 Visuals
     */
    @Overwrite
    public void renderArmHoldingItem(float tickDelta) {

        boolean enabled = Abyss.getInstance().getModuleRegistry().getModule("1.7 Visuals").isToggled();

        boolean blockHitting = enabled && Abyss.getInstance().getModuleRegistry().getModule("1.7 Visuals").getSetting("Block Hitting").isToggled();
        boolean eating = enabled && Abyss.getInstance().getModuleRegistry().getModule("1.7 Visuals").getSetting("Eating/Drinking").isToggled();
        boolean bow = enabled && Abyss.getInstance().getModuleRegistry().getModule("1.7 Visuals").getSetting("Bow Animations").isToggled();

        float f = 1.0F - (this.lastEquipProgress + (this.equipProgress - this.lastEquipProgress) * tickDelta);
        ClientPlayerEntity player = this.client.player;
        float g = player.getHandSwingProgress(tickDelta);
        float h = player.prevPitch + (player.pitch - player.prevPitch) * tickDelta;
        float i = player.prevYaw + (player.yaw - player.prevYaw) * tickDelta;

        this.rotate(h, i);
        this.applyPlayerLighting(player);
        this.applyPlayerRotation(player, tickDelta);

        GlStateManager.enableRescaleNormal();
        GlStateManager.pushMatrix();

        if (this.mainHand != null) {
            if (this.mainHand.getItem() == Items.FILLED_MAP) {
                this.renderMap(player, h, f, g);
            } else if (player.getItemUseTicks() > 0) {
                UseAction useAction = this.mainHand.getUseAction();
                switch (useAction) {
                    case NONE:
                        this.applyEquipAndSwingOffset(f, 0.0F);
                        break;
                    case EAT:
                    case DRINK:
                        this.applyEatOrDrinkTransformation(player, tickDelta);
                        this.applyEquipAndSwingOffset(f, eating ? g : 0.0f);
                        break;
                    case BLOCK:
                        this.applyEquipAndSwingOffset(f, blockHitting ? g : 0.0f);
                        this.applySwordBlockTransformation();
                        break;
                    case BOW:
                        this.applyEquipAndSwingOffset(f, bow ? g : 0.0f);
                        this.applyBowTransformation(tickDelta, player);
                }
            } else {
                this.translateSwingProgress(g);
                this.applyEquipAndSwingOffset(f, g);
            }

            this.renderItem(player, this.mainHand, ModelTransformation.Mode.FIRST_PERSON);
        } else if (!player.isInvisible()) {
            this.renderArm(player, f, g);
        }

        GlStateManager.popMatrix();
        GlStateManager.disableRescaleNormal();
        DiffuseLighting.disable();
    }
}
