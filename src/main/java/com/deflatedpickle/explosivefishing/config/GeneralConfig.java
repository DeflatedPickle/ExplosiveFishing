package com.deflatedpickle.explosivefishing.config;

import com.deflatedpickle.explosivefishing.Reference;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
@Config(modid = Reference.MOD_ID, name = Reference.CONFIG_GENERAL, category = Configuration.CATEGORY_GENERAL)
@Config.LangKey("config.explosivefishing.general")
public class GeneralConfig {
    @Config.Name("Loot Per Block")
    @Config.Comment("The amount of loot you can find per blocks.")
    @Config.LangKey("config.explosivefishing.lootPerBlock")
    public static int lootPerBlock = 2;

    @Config.Name("Minimum Loot")
    @Config.Comment("The minimum loot you can find.")
    @Config.LangKey("config.explosivefishing.minimumLoot")
    public static int minimumLoot = 2;

    public enum PoolEnum {
        GAMEPLAY_FISHING,
        GAMEPLAY_FISHING_JUNK,
        GAMEPLAY_FISHING_FISH,
        GAMEPLAY_FISHING_TREASURE
    }

    @Config.Name("Loot Pool")
    @Config.Comment("The loot pool to choose loot from.")
    @Config.LangKey("config.explosivefishing.lootPool")
    public static PoolEnum lootPool = PoolEnum.GAMEPLAY_FISHING;

    @SubscribeEvent
    public static void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(Reference.MOD_ID)) {
            ConfigManager.sync(Reference.MOD_ID, Config.Type.INSTANCE);
        }
    }
}
