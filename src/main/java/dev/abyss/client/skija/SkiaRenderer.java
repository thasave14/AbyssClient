package dev.abyss.client.skija;

import com.mojang.blaze3d.platform.GlStateManager;
import dev.abyss.client.skia.state.UIState;
import io.github.humbleui.skija.*;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.opengl.GL11;

/**
 * @author iLofiz/quantam
 * The Main Skia Renderer Class
 */
public final class SkiaRenderer {

    private static DirectContext context = null;
    private static Surface surface;
    private static BackendRenderTarget target = null;

    public static Canvas getCanvas() {
        return surface.getCanvas();
    }

    public static void init() {

        if(context == null) {
            context = DirectContext.makeGL();
        }
    }

    public static void create(int width, int height) {

        init();

        int fbo = MinecraftClient.getInstance().getFramebuffer().fbo;

        target = BackendRenderTarget.makeGL(width, height, 0, 8, fbo, GL11.GL_RGBA8);
        surface = Surface.makeFromBackendRenderTarget(context, target, SurfaceOrigin.BOTTOM_LEFT, SurfaceColorFormat.BGRA_8888, ColorSpace.getSRGB());
    }

    public static void onResize(int width, int height) {

        if(surface != null) {
            surface.close();
        }

        if(target != null) {
            target.close();
        }

        create(width, height);
    }

    public static void begin() {

        UIState.save();
        GlStateManager.clearColor(0f, 0f, 0f, 0f);

        if(context != null) {
            context.resetGLAll();
        }

        GL11.glDisable(GL11.GL_ALPHA_TEST);
    }

    public static void end() {

        if(context != null) {
            context.flush();
        }

        UIState.restore();
    }
}
