package dev.abyss.client.skia.state

import org.lwjgl.opengl.*

/**
 * State encapsulates the OpenGL state information for backup and restoration.
 *
 * @author quantamyt
 *
 * This code is released under the Creative Commons Attribution 4.0 International License (CC BY 4.0).
 * You are free to share and adapt this code, provided appropriate credit is given to the original author.
 * For more details, visit: <a href="https://creativecommons.org/licenses/by/4.0/deed.en">Creative Commons</a>
 */

class State {

    private val pixelStores = HashMap<PixelStoreParameter, Int>()
    private var lastActiveTexture = 0
    private var lastProgram = 0
    private var lastSampler = 0
    private var lastVertexArray = 0
    private var lastArrayBuffer = 0

    private var lastBlendSrcRgb = 0
    private var lastBlendDstRgb = 0
    private var lastBlendSrcAlpha = 0
    private var lastBlendDstAlpha = 0
    private var lastBlendEquationRgb = 0
    private var lastBlendEquationAlpha = 0

    /**
     * Backs up the current OpenGL state.
     */
    fun backupCurrentState() {
        GL11.glPushClientAttrib(GL11.GL_ALL_CLIENT_ATTRIB_BITS)
        GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS)

        lastActiveTexture = GL11.glGetInteger(GL13.GL_ACTIVE_TEXTURE)
        lastProgram = GL11.glGetInteger(GL20.GL_CURRENT_PROGRAM)
        lastSampler = GL11.glGetInteger(GL33.GL_SAMPLER_BINDING)
        lastArrayBuffer = GL11.glGetInteger(GL15.GL_ARRAY_BUFFER_BINDING)
        lastVertexArray = GL11.glGetInteger(GL30.GL_VERTEX_ARRAY_BINDING)

        for (parameter in PixelStoreParameter.entries) {
            pixelStores[parameter] = GL11.glGetInteger(parameter.value)
        }

        lastBlendSrcRgb = GL11.glGetInteger(GL14.GL_BLEND_SRC_RGB)
        lastBlendDstRgb = GL11.glGetInteger(GL14.GL_BLEND_DST_RGB)
        lastBlendSrcAlpha = GL11.glGetInteger(GL14.GL_BLEND_SRC_ALPHA)
        lastBlendDstAlpha = GL11.glGetInteger(GL14.GL_BLEND_DST_ALPHA)
        lastBlendEquationRgb = GL11.glGetInteger(GL20.GL_BLEND_EQUATION_RGB)
        lastBlendEquationAlpha = GL11.glGetInteger(GL20.GL_BLEND_EQUATION_ALPHA)
    }

    /**
     * Restores the previous OpenGL state.
     */
    fun restorePreviousState() {
        GL11.glPopAttrib()
        GL11.glPopClientAttrib()

        GL20.glUseProgram(lastProgram)
        GL33.glBindSampler(0, lastSampler)
        GL13.glActiveTexture(lastActiveTexture)
        GL30.glBindVertexArray(lastVertexArray)
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, lastArrayBuffer)
        GL20.glBlendEquationSeparate(lastBlendEquationRgb, lastBlendEquationAlpha)
        GL14.glBlendFuncSeparate(lastBlendSrcRgb, lastBlendDstRgb, lastBlendSrcAlpha, lastBlendDstAlpha)

        for (parameter in PixelStoreParameter.entries) {
            GL11.glPixelStorei(parameter.value, pixelStores[parameter]!!)
        }
    }
}