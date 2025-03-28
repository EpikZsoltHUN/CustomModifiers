package hu.epikzsolthun.module.mods;

import hu.epikzsolthun.CustomModifiers;
import hu.epikzsolthun.module.Module;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.hit.EntityHitResult;
import org.lwjgl.glfw.GLFW;

import static net.minecraft.util.hit.HitResult.Type.ENTITY;

public class Breachswap extends Module {
    int setbackslot = -1;
    public Breachswap() {
        super(GLFW.GLFW_KEY_K, "breachswap", Items.MACE);
    }
    public void beginHitEntity(){
        if(!status){ return; }
        //Checks
        ClientPlayerEntity clientplayer = MinecraftClient.getInstance().player;

        if(MinecraftClient.getInstance().crosshairTarget.getType() != ENTITY){ return; }
        if(!clientplayer.getMainHandStack().isIn(ItemTags.SWORDS)){ return; }

        Entity e = ((EntityHitResult)(MinecraftClient.getInstance().crosshairTarget)).getEntity();
        if(! (e instanceof LivingEntity)){ return; }

        //functionality
        boolean hasarmor = false;
        for (ItemStack armorPiece : ((LivingEntity) e).getArmorItems()) {
            if (!armorPiece.isEmpty()) {
                hasarmor = true;
            }
        }
        if(!hasarmor){
            return;
        }
        for (int i = 0; i < 9; i++){
            if(clientplayer.getInventory().getStack(i).getItem() == Items.MACE){
                setbackslot = clientplayer.getInventory().selectedSlot;
                clientplayer.getInventory().setSelectedSlot(i);
                break;
            }
        }
    }
    public void endHitEntity(){
        if(!status){ return; }

        if(setbackslot != -1){
            CustomModifiers.LOGGER.info("Set back to slot: "+ setbackslot);
            MinecraftClient.getInstance().player.getInventory().setSelectedSlot(setbackslot);
            setbackslot = -1;
        }
    }
}
