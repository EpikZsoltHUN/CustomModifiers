package hu.epikzsolthun.mixin;

import hu.epikzsolthun.CustomModifiers;
import hu.epikzsolthun.module.mods.Breachswap;
import hu.epikzsolthun.module.Module;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public class EntityHitMixin {
    Breachswap breachswap = null;

    @Inject(at = @At("HEAD"), method = "doAttack")
    private void init(CallbackInfoReturnable<Boolean> cir) {
        if(breachswap == null){ construct(); }

        breachswap.beginHitEntity();
    }

    @Inject(at = @At("TAIL"), method = "doAttack")
    private void restore(CallbackInfoReturnable<Boolean> cir){
        breachswap.endHitEntity();
    }

    private void construct(){
        Module module = CustomModifiers.moduleManager.getModule("breachswap");
        if (module instanceof Breachswap) {
            breachswap = (Breachswap) module;  // Safe cast and method call
        }
    }
}