package hu.epikzsolthun;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ModmenuApiImpl implements ModMenuApi {
    ModConfig config;


    @Override
    public ConfigScreenFactory<Screen> getModConfigScreenFactory(){
        if(config == null) {construct();}

        return parent -> {
            ConfigBuilder builder = ConfigBuilder.create()
                    .setParentScreen(parent)
                    .setTitle(Text.translatable("title.custommodifiers.config"));

            ConfigCategory general = builder.getOrCreateCategory(Text.translatable("category.custommodifiers.general"));
            ConfigEntryBuilder entryBuilder = builder.entryBuilder();

            general.addEntry(entryBuilder.startFloatField(Text.translatable("option.custommodifiers.elytraflyspeed"), config.stuff.elytraflyspeed)
                    .setDefaultValue(2.0f) // Recommended: Used when user click "Reset"
                    .setTooltip(Text.translatable("tooltip.custommodifiers.elytraflyspeed")) // Optional: Shown when the user hover over this option
                    .setSaveConsumer(newValue -> {
                        config.stuff.elytraflyspeed = newValue;
                    }) // Recommended: Called when user save the config
                    .build()); // Builds the option entry for cloth config

            builder.setSavingRunnable(() -> {
                AutoConfig.getConfigHolder(ModConfig.class).setConfig(config);
                AutoConfig.getConfigHolder(ModConfig.class).save();

                CustomModifiers.moduleManager.UpdateConfig();
            });

            return builder.build();
        };
    }
    private void construct(){
        AutoConfig.register(ModConfig.class, GsonConfigSerializer::new);
        AutoConfig.getConfigHolder(ModConfig.class).load();
        config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
    }
}
