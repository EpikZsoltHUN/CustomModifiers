package hu.epikzsolthun;

import hu.epikzsolthun.module.ModuleManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Environment(EnvType.CLIENT)
public class CustomModifiers implements ClientModInitializer {
    public static final String MOD_ID = "custommodifier";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static ModuleManager moduleManager;
    @Override
    public void onInitializeClient() {
        moduleManager = new ModuleManager();

    }

}