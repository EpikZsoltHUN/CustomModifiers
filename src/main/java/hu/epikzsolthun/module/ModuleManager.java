package hu.epikzsolthun.module;

import hu.epikzsolthun.CustomModifiers;
import hu.epikzsolthun.module.mods.Breachswap;
import hu.epikzsolthun.module.mods.ElytraSpeed;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class ModuleManager {
    private final Module[] modules;
    private static final Identifier MODULE_LIST = Identifier.of(CustomModifiers.MOD_ID, "module-list-layer");
    int iconCounter = 0;

    public ModuleManager() {
        modules = new Module[]{
                new ElytraSpeed(),
                new Breachswap()
        };
        ClientTickEvents.END_CLIENT_TICK.register(this::tick);
        WorldRenderEvents.END.register(this::Worldrender);
        HudLayerRegistrationCallback.EVENT.register(layeredDrawer -> layeredDrawer.attachLayerBefore(IdentifiedLayer.DEBUG, MODULE_LIST, this::HUDrender));
    }
    public ClientTickEvents.EndTick tick(MinecraftClient client){
        for (Module m : modules) {
            if(m.togglebinding.wasPressed()){
                m.status = !m.status;
            }
            if(m.status && MinecraftClient.getInstance().world != null){
                m.tick();
            }
        }
        return null;
    }
    public WorldRenderEvents.End Worldrender(WorldRenderContext context){
        for (Module m : modules) {
            if(m.status && MinecraftClient.getInstance().world != null){
                m.Worldrender(context);
            }
        }
        return null;
    }
    public void HUDrender(DrawContext context, RenderTickCounter tickCounter){
        for (Module m : modules) {
            if(m.status && MinecraftClient.getInstance().world != null){
                iconCounter++;
                renderIcon(m.icon, context);
                m.HUDrender(context, tickCounter);
            }
        }
        iconCounter = 0;
    }
    public Module getModule(String id){
        for (Module m : modules){
            if(m.name == id){
                return m;
            }
        }
        throw new IllegalArgumentException("Module with id '" + id + "' not found.");
    }
    public void renderIcon(Item icon, DrawContext context){
        MinecraftClient client = MinecraftClient.getInstance();

        if (client.player == null || client.world == null) return;

        int screenWidth = context.getScaledWindowWidth();
        int screenHeight = context.getScaledWindowHeight();

        ItemStack itemStack = new ItemStack(icon);

        context.drawItem(itemStack, screenWidth - 20, screenHeight-20-16*(iconCounter-1)); // top-right corner, going down with each icon.
    }

}
