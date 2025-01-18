package dev.abyss.client.mixins.client.network;

import dev.abyss.client.utils.KDTracker;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.CombatEventS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class MixinClientPlayNetworkHandler {
  @Inject(method = "onCombatEvent", at = @At("HEAD"))
  public void onCombat(CombatEventS2CPacket packet, CallbackInfo ci) {
    if(packet.type == CombatEventS2CPacket.Type.ENTITY_DIED) {
      if(packet.attackerEntityId == MinecraftClient.getInstance().player.getEntityId()) {
        KDTracker.addKill();
      } else if(packet.entityId == MinecraftClient.getInstance().player.getEntityId()) {
        KDTracker.addDeath();
      }
    }
  }
}
