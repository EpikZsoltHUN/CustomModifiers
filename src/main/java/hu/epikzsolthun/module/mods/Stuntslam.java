package hu.epikzsolthun.module.mods;

import hu.epikzsolthun.mixin.MethodInvoker;
import hu.epikzsolthun.module.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.hit.EntityHitResult;
import org.lwjgl.glfw.GLFW;

import static net.minecraft.util.hit.HitResult.Type.ENTITY;

public class Stuntslam extends Module {
    int setbackslot = -1;
    int maceslot = -1;
    public Stuntslam() {
        super(GLFW.GLFW_KEY_KP_3, "stuntslam", Items.NETHERITE_AXE);
    }
    public void beginHitEntity(){
        if(!status){ return; }

        ClientPlayerEntity clientplayer = MinecraftClient.getInstance().player;
        if(MinecraftClient.getInstance().crosshairTarget.getType() != ENTITY){ return; }
        Entity e = ((EntityHitResult)MinecraftClient.getInstance().crosshairTarget).getEntity();

        if(!(e instanceof PlayerEntity)){ return; }
        PlayerEntity p = (PlayerEntity) e;

        if(!(p.isUsingItem() && p.getActiveItem().isOf(Items.SHIELD))){ return; }
        if(!clientplayer.getMainHandStack().isIn(ItemTags.AXES)){ return; }
        //if(!MaceItem.shouldDealAdditionalDamage(clientplayer)){ return; } #### For some reason the client now doesn't get the "falldistance" value so clientplayer.fallDistance is always 0.0, thus the check fails.
        for (int i = 0; i < 9; i++){
            if(clientplayer.getInventory().getStack(i).getItem() == Items.MACE){
                setbackslot = clientplayer.getInventory().selectedSlot;
                maceslot = i;
                break;
            }
        }
    }

    public void endHitEntity(){
        if(!status){ return; }

        if(maceslot != -1){
            MinecraftClient.getInstance().player.getInventory().setSelectedSlot(maceslot);
            maceslot = -1;

            ((MethodInvoker.ClientInvoker) MinecraftClient.getInstance()).callDoAttack();
        } else if (setbackslot != -1) {
            MinecraftClient.getInstance().player.getInventory().setSelectedSlot(setbackslot);
            setbackslot = -1;
        }
    }
}
