package hu.epikzsolthun;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = CustomModifiers.MOD_ID)
public class ModConfig implements ConfigData {
    public InnerStuff stuff = new InnerStuff();

    public static class InnerStuff {
        public float elytraflyspeed = 2.0f;
    }
}