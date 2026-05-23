package com.example.bluescreen.mixin;

import com.example.bluescreen.BlueScreenMod;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Inject(method = "render", at = @At("TAIL"))
    private void onRender(float tickDelta, long startTime, boolean tick, CallbackInfo ci) {
        if (BlueScreenMod.isBlueScreenActive()) {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.getWindow() != null && client.currentScreen == null) {
                int width = client.getWindow().getScaledWidth();
                int height = client.getWindow().getScaledHeight();

                RenderSystem.disableDepthTest();
                RenderSystem.setShader(GameRenderer::getPositionColorProgram);

                Matrix4f matrix = new Matrix4f().ortho(0, width, height, 0, -1, 1);
                BufferBuilder buffer = Tessellator.getInstance().getBuffer();

                buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
                buffer.vertex(matrix, 0, height, 0).color(0, 0, 170, 255).next();
                buffer.vertex(matrix, width, height, 0).color(0, 0, 170, 255).next();
                buffer.vertex(matrix, width, 0, 0).color(0, 0, 170, 255).next();
                buffer.vertex(matrix, 0, 0, 0).color(0, 0, 170, 255).next();

                BufferRenderer.drawWithGlobalProgram(buffer.end());
                RenderSystem.enableDepthTest();
            }
        }
    }
}
