package hu.epikzsolthun;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Direction;
import org.lwjgl.glfw.GLFW;

import java.awt.*;

@Environment(EnvType.CLIENT)
public class ElytraSpeedMod implements ModInitializer {
    private static KeyBinding keyBinding;
    public boolean active = false;

    @Override
    public void onInitialize() {
        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.elytramod.toggleautofly", // The translation key of the keybinding's name
                InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_J, // The keycode of the key
                "category.elytramod" // The translation key of the keybinding's category.
        ));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(keyBinding.wasPressed()){
                active = !active;
            }
            if (active) {
                PlayerEntity player = client.player;
                if (player != null && player.isFallFlying()) {
                    // Increase elytra speed here
                    double yaw = Math.toRadians(player.getYaw());
                    double xMotion = -2 * Math.sin(yaw);
                    double zMotion = 2 * Math.cos(yaw);

                    // Set the player's motion
                    player.setVelocity(xMotion, player.getVelocity().y, zMotion);
                }
            }
        });
    }
}