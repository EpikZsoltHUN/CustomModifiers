package hu.epikzsolthun.mixin;

import hu.epikzsolthun.CustomModifiers;
import hu.epikzsolthun.module.mods.Breachswap;
import hu.epikzsolthun.module.Module;
import hu.epikzsolthun.module.mods.Stuntslam;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public class EntityHitMixin {

    @Unique
    Breachswap breachswap = null;
    @Unique
    Stuntslam stuntslam = null;
    @Unique
    boolean isReady = false;

    @Inject(at = @At("HEAD"), method = "doAttack")
    private void init(CallbackInfoReturnable<Boolean> cir) {
        if(!isReady){ construct(); }

        stuntslam.beginHitEntity();
        breachswap.beginHitEntity();
    }

    @Inject(at = @At("TAIL"), method = "doAttack")
    private void restore(CallbackInfoReturnable<Boolean> cir){
        stuntslam.endHitEntity();
        breachswap.endHitEntity();
    }

    @Unique
    private void construct(){
        Module m1 = CustomModifiers.moduleManager.getModule("breachswap");
        if (m1 instanceof Breachswap) {
            breachswap = (Breachswap) m1;
        }

        Module m2 = CustomModifiers.moduleManager.getModule("stuntslam");
        if (m2 instanceof Stuntslam) {
            stuntslam = (Stuntslam) m2;
        }
        isReady = true;
    }
}