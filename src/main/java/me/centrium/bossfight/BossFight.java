package me.centrium.bossfight;

import lombok.Getter;
import me.centrium.bossfight.commands.*;
import me.centrium.bossfight.database.DatabaseConnector;
import me.centrium.bossfight.faction.FactionManager;
import me.centrium.bossfight.item.ItemManager;
import me.centrium.bossfight.level.LevelManager;
import me.centrium.bossfight.listener.CancelledListener;
import me.centrium.bossfight.listener.GlobalListener;
import me.centrium.bossfight.listener.PlayerLoadListener;
import me.centrium.bossfight.menu.ShopMenu;
import me.centrium.bossfight.menu.UpgradeMenu;
import me.centrium.bossfight.mine.MinesManager;
import me.centrium.bossfight.randomevent.RandomEventManager;
import me.centrium.bossfight.scoreboard.ScoreboardManager;
import me.centrium.bossfight.shop.ShopInfo;
import me.centrium.bossfight.shop.ShopManager;
import me.centrium.bossfight.upgrade.UpgradeManager;
import me.centrium.bossfight.user.UserManager;
import me.centrium.bossfight.blocksprice.BlocksPriceManager;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import sexy.kostya.mineos.MultiUtils;

import java.util.Arrays;

@Getter
public class BossFight extends JavaPlugin {

    private static @Getter BossFight instance;
    private DatabaseConnector databaseConnector;
    private UserManager userManager;
    private BlocksPriceManager blocksPriceManager;
    private MinesManager minesManager;
    private FactionManager factionManager;
    private LevelManager levelManager;
    private RandomEventManager randomEventManager;
    private ItemManager itemManager;
    private ShopManager shopManager;
    private UpgradeManager upgradeManager;

    @Override
    public void onEnable() {
        instance = this;
        MultiUtils.initGamesConnector("localhost", 3306, "root", "", "games");
        databaseConnector = new DatabaseConnector("localhost", "bossfight", "root", "root", "");
        userManager = new UserManager();
        blocksPriceManager = new BlocksPriceManager();
        minesManager = new MinesManager();
        factionManager = new FactionManager();
        levelManager = new LevelManager();
        randomEventManager = new RandomEventManager();
        itemManager = new ItemManager();
        shopManager = new ShopManager();
        upgradeManager = new UpgradeManager();

        new MineCommand();
        new SellBlocksCommand();
        new TestCommand();
        new FactionCommand();
        new SendMoneyCommand();
        new TrashCommand();
        new GiftCommand();
        new LevelCommand();
        new ItemsCommand();

        registerListeners(new GlobalListener(), new CancelledListener(), new PlayerLoadListener());

        ScoreboardManager.load();
        randomEventManager.start();

    }

    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(userManager::unload);
        databaseConnector.getConnector().close();
    }

    private void registerListeners(Listener... listener){
        Arrays.asList(listener).forEach(listeners -> Bukkit.getPluginManager().registerEvents(listeners, this));
    }
}
