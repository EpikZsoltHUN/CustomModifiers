package hu.epikzsolthun.module;

import hu.epikzsolthun.ModConfig;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.Item;

public abstract class Module {
    public KeyBinding togglebinding;
    public boolean status;
    public String name;
    public Item icon;
    public ModConfig config;
    public void tick() {}
    public void Worldrender(WorldRenderContext context) {}
    public void HUDrender(DrawContext context, RenderTickCounter tickCounter) {}
    public void onEnable(){}
    public void onDisable(){}

    /**
     *  Use if you have variables that need to be updated based on config.
     *  <p>
     *  The config variable is updated by ModuleManager.
     */
    public void UpdateConfig(){}
    /**
    * @param keycode the GLFW keycode for toggling it.
    * @param id the identifier only using: lowercase, number and _
    * */
    public Module(int keycode, String id, Item itemIcon){
        togglebinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.custommodifiers." + id, // The translation key of the keybinding's name
                InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                keycode, // The keycode of the key
                "category.custommodifiers" // The translation key of the keybinding's category.
        ));
        name = id;
        icon = itemIcon;
    }

}
