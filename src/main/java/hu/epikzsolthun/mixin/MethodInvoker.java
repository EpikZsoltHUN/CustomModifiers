package hu.epikzsolthun.mixin;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

public interface MethodInvoker {
    @Mixin(MinecraftClient.class)
    public interface ClientInvoker{
        @Invoker("doAttack")
        public boolean callDoAttack();
    }
}
