package com.example.bluescreen;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class BlueScreenMod implements ClientModInitializer {

    private static boolean blueScreenActive = false;
    private static KeyBinding blueScreenKey;
    private static KeyBinding resetKey;

    @Override
    public void onInitializeClient() {
        blueScreenKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.bluescreen.activate",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_E,
            "category.bluescreen.general"
        ));

        resetKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.bluescreen.reset",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_0,
            "category.bluescreen.general"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (blueScreenKey.wasPressed()) {
                blueScreenActive = true;
            }

            if (resetKey.wasPressed()) {
                blueScreenActive = false;
            }
        });
    }

    public static boolean isBlueScreenActive() {
        return blueScreenActive;
    }
}
