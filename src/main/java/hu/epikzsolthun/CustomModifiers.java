package hu.epikzsolthun;

import hu.epikzsolthun.module.ModuleManager;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomModifiers implements ModInitializer {
    public static final String MOD_ID = "custommodifier";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static ModuleManager moduleManager;
    @Override
    public void onInitialize() {
        moduleManager = new ModuleManager();
    }

}