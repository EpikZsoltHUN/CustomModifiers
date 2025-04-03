package hu.epikzsolthun.module.mods;

import hu.epikzsolthun.module.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import org.lwjgl.glfw.GLFW;

public class Elytrafly extends Module {
    public float elytraflyspeed = 2;
    public Elytrafly(){
        super(GLFW.GLFW_KEY_KP_1, "elytrafly", Items.ELYTRA);
    }
    @Override
    public void tick() {
        PlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null && player.getPose() == EntityPose.GLIDING) {
            // Increase elytra speed here
            double yaw = Math.toRadians(player.getYaw());
            double xMotion = -elytraflyspeed * Math.sin(yaw);
            double zMotion = elytraflyspeed * Math.cos(yaw);

            player.setVelocity(xMotion, player.getVelocity().y, zMotion);
        }
    }
    @Override
    public void UpdateConfig(){
        elytraflyspeed = config.stuff.elytraflyspeed;
    }
}
