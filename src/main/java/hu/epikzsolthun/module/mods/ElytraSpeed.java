package hu.epikzsolthun.module.mods;

import hu.epikzsolthun.module.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import org.lwjgl.glfw.GLFW;

public class ElytraSpeed extends Module {
    public ElytraSpeed(){
        super(GLFW.GLFW_KEY_J, "elytrafly", Items.ELYTRA);
    }
    @Override
    public void tick() {
        PlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null && player.getPose() == EntityPose.GLIDING) {
            // Increase elytra speed here
            double yaw = Math.toRadians(player.getYaw());
            double xMotion = -2 * Math.sin(yaw);
            double zMotion = 2 * Math.cos(yaw);

            player.setVelocity(xMotion, player.getVelocity().y, zMotion);
        }
    }
}
