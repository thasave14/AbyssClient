package dev.abyss.client.mixins.world;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ThreadedAnvilChunkStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.io.DataInputStream;
import java.io.IOException;

@Mixin(ThreadedAnvilChunkStorage.class)
public class MixinThreadedAnvilChunkStorage {

    /**
     * @author GlideClient
     */
    @Inject(method = "loadChunk", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/NbtIo;read(Ljava/io/DataInputStream;)Lnet/minecraft/nbt/NbtCompound;", shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    private void loadChunk(World world, int x, int z, CallbackInfoReturnable<Chunk> cir, ChunkPos chunkPos, NbtCompound nbtCompound, DataInputStream dataInputStream) throws IOException {
        dataInputStream.close();
    }
}
