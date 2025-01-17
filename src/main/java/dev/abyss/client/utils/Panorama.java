package dev.abyss.client.utils;

import com.mojang.blaze3d.platform.GlStateManager;
import dev.abyss.client.Abyss;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Project;

import java.awt.*;

public class Panorama extends Screen {

    public static int panoramaTimer = 0;

    private final NativeImageBackedTexture viewportTexture = new NativeImageBackedTexture(256, 256);

    /**
     * An array of all the paths to the panorama pictures.
     */
    private static final Identifier[] titlePanoramaPaths = new Identifier[]{
            new Identifier("abyss", "panorama/panorama_0.png"),
            new Identifier("abyss", "panorama/panorama_1.png"),
            new Identifier("abyss", "panorama/panorama_2.png"),
            new Identifier("abyss", "panorama/panorama_3.png"),
            new Identifier("abyss", "panorama/panorama_4.png"),
            new Identifier("abyss", "panorama/panorama_5.png")
    };

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen() {
        panoramaTimer++;
    }

    /**
     * Draws the main menu panorama
     */
    private void drawPanorama(float drawPanorama3) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder worldrenderer = tessellator.getBuffer();
        GlStateManager.matrixMode(5889);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        Project.gluPerspective(120.0F, 1.0F, 0.05F, 10.0F);
        GlStateManager.matrixMode(5888);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.enableBlend();
        GlStateManager.disableAlphaTest();
        GlStateManager.disableCull();
        GlStateManager.depthMask(false);
        GlStateManager.blendFuncSeparate(770, 771, 1, 0);
        int i = 8;

        for (int j = 0; j < i * i; ++j) {
            GlStateManager.pushMatrix();
            float f = ((float) (j % i) / (float) i - 0.5F) / 64.0F;
            float f1 = ((float) (j / i) / (float) i - 0.5F) / 64.0F;
            float f2 = 0.0F;
            GlStateManager.translate(f, f1, f2);
            GlStateManager.rotate(MathHelper.sin(((float) panoramaTimer + drawPanorama3) / 400.0F) * 25.0F + 20.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(-((float) panoramaTimer + drawPanorama3) * 0.1F, 0.0F, 1.0F, 0.0F);

            for (int k = 0; k < 6; ++k) {
                GlStateManager.pushMatrix();

                switch (k) {
                    case 1:
                        GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
                        break;
                    case 2:
                        GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
                        break;
                    case 3:
                        GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
                        break;
                    case 4:
                        GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
                        break;
                    case 5:
                        GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
                        break;
                }

                client.getTextureManager().bindTexture(titlePanoramaPaths[k]);
                worldrenderer.begin(7, VertexFormats.POSITION_TEXTURE_COLOR);
                int l = 255 / (j + 1);
                worldrenderer.vertex(-1.0, -1.0, 1.0).texture(0.0, 0.0).color(255, 255, 255, l).next();
                worldrenderer.vertex(1.0, -1.0, 1.0).texture(1.0, 0.0).color(255, 255, 255, l).next();
                worldrenderer.vertex(1.0, 1.0, 1.0).texture(1.0, 1.0).color(255, 255, 255, l).next();
                worldrenderer.vertex(-1.0, 1.0, 1.0).texture(0.0, 1.0).color(255, 255, 255, l).next();
                tessellator.draw();
                GlStateManager.popMatrix();
            }

            GlStateManager.popMatrix();
            GlStateManager.colorMask(true, true, true, false);
        }

        worldrenderer.offset(0.0, 0.0, 0.0);
        GlStateManager.colorMask(true, true, true, true);
        GlStateManager.matrixMode(5889);
        GlStateManager.popMatrix();
        GlStateManager.matrixMode(5888);
        GlStateManager.popMatrix();
        GlStateManager.depthMask(true);
        GlStateManager.enableCull();
        GlStateManager.enableDepthTest();
    }

    /**
     * Rotates and blurs the skybox view in the main menu
     */
    private void rotateAndBlurSkybox() {
        Identifier backgroundTexture = client.getTextureManager().registerDynamicTexture("background", viewportTexture);
        client.getTextureManager().bindTexture(backgroundTexture);
        GL11.glTexParameteri(3553, 10241, 9729);
        GL11.glTexParameteri(3553, 10240, 9729);
        GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, 256, 256);
        GlStateManager.enableBlend();
        GlStateManager.blendFuncSeparate(770, 771, 1, 0);
        GlStateManager.colorMask(true, true, true, false);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder worldrenderer = tessellator.getBuffer();
        worldrenderer.begin(7, VertexFormats.POSITION_TEXTURE_COLOR);
        GlStateManager.disableAlphaTest();
        int i = 3;

        for (int j = 0; j < i; ++j) {
            float f = 1.0F / (float) (j + 1);
            int k = width;
            int l = height;
            float f1 = (float) (j - i / 2) / 256.0F;
            worldrenderer.vertex(k, l, zOffset).texture((0.0F + f1), 1.0).color(1.0F, 1.0F, 1.0F, f).next();
            worldrenderer.vertex(k, 0.0, zOffset).texture((1.0F + f1), 1.0).color(1.0F, 1.0F, 1.0F, f).next();
            worldrenderer.vertex(0.0, 0.0, zOffset).texture((1.0F + f1), 0.0).color(1.0F, 1.0F, 1.0F, f).next();
            worldrenderer.vertex(0.0, l, zOffset).texture((0.0F + f1), 0.0).color(1.0F, 1.0F, 1.0F, f).next();
        }

        tessellator.draw();
        GlStateManager.enableAlphaTest();
        GlStateManager.colorMask(true, true, true, true);
    }

    /**
     * Renders the skybox in the main menu
     */
    private void renderSkybox(float tickDelta) {
        client.getFramebuffer().unbind();
        GlStateManager.viewport(0, 0, 256, 256);
        drawPanorama(tickDelta);
        rotateAndBlurSkybox();
        rotateAndBlurSkybox();
        rotateAndBlurSkybox();
        rotateAndBlurSkybox();
        rotateAndBlurSkybox();
        rotateAndBlurSkybox();
        rotateAndBlurSkybox();
        client.getFramebuffer().bind(true);
        GlStateManager.viewport(0, 0, client.width, client.height);
        float f = width > height ? 120.0F / (float) width : 120.0F / (float) height;
        float f1 = (float) height * f / 256.0F;
        float f2 = (float) width * f / 256.0F;
        int i = width;
        int j = height;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder worldrenderer = tessellator.getBuffer();
        worldrenderer.begin(7, VertexFormats.POSITION_TEXTURE_COLOR);
        worldrenderer.vertex(0.0, j, zOffset).texture((0.5F - f1), (0.5F + f2)).color(1.0F, 1.0F, 1.0F, 1.0F).next();
        worldrenderer.vertex(i, j, zOffset).texture((0.5F - f1), (0.5F - f2)).color(1.0F, 1.0F, 1.0F, 1.0F).next();
        worldrenderer.vertex(i, 0.0, zOffset).texture((0.5F + f1), (0.5F - f2)).color(1.0F, 1.0F, 1.0F, 1.0F).next();
        worldrenderer.vertex(0.0, 0.0, zOffset).texture((0.5F + f1), (0.5F + f2)).color(1.0F, 1.0F, 1.0F, 1.0F).next();
        tessellator.draw();
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void render(int mouseX, int mouseY, float tickDelta) {
        GlStateManager.disableAlphaTest();
        renderSkybox(tickDelta);
        GlStateManager.enableAlphaTest();

        Abyss.getInstance().getSkia().render(() -> {

            Abyss.getInstance().getSkia().drawRect(0, 0, width, height, Abyss.getInstance().getSkia().paint(new Color(0, 0, 0, 50)));
        });
    }
}