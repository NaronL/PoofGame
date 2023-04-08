package me.centrium.bossfight.user;

import com.google.common.util.concurrent.AtomicDouble;
import lombok.Builder;
import lombok.Data;
import me.centrium.bossfight.BossFight;
import me.centrium.bossfight.blocksprice.BlocksPrice;
import me.centrium.bossfight.faction.FactionInfo;
import me.centrium.bossfight.level.LevelInfo;
import me.centrium.bossfight.utils.ChatUtils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.luvas.rmcs.utils.Util;

import java.util.Arrays;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

@Data
@Builder
public class User {
    private Player player;
    private int level;
    private double balance;
    private double totalblocks;
    private double exp;
    private int ruby;
    private int kills;
    private int deaths;
    private FactionInfo faction;
    private ConcurrentMap<String, Integer> blocks_log;
    private ConcurrentMap<UserSettings, Integer> settings;

    public void levelUp(){
        LevelInfo levelInfo = BossFight.getInstance().getLevelManager().getNextLevel(player);

        this.takeBalance(levelInfo.getExp());
        this.level++;
        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F);
        ChatUtils.sendMessage(player, Util.wrapGold("Вы повысили свой уровень!"));
    }

    public void addTotalblocks(double totalblocks){
        this.totalblocks += totalblocks;
    }

    public void addKills(int kills){
        this.kills += kills;
    }

    public void addDeaths(int deaths){
        this.deaths += deaths;
    }

    public void addBalance(double balance){
        this.balance += balance;
    }

    public boolean hasBalance(double balance){
        return this.balance >= balance;
    }

    public void takeBalance(double balance){
        if(hasBalance(balance)) this.balance -= balance;
    }

    public int getSettings(UserSettings userSettings){
        return this.settings.getOrDefault(userSettings, 0);
    }

    public void setSettings(UserSettings userSettings, int count){
        this.settings.put(userSettings, count);
    }

    public void setFaction(FactionInfo faction){
        this.faction = faction;
    }

    public int getBlockCount(BlocksPrice blocksPrice) {
        return blocks_log.getOrDefault(blocksPrice.getId(), blocks_log.get(blocksPrice.getId()));
    }

    public void addBlockCount(BlocksPrice blocksPrice, int count) {
        if (blocks_log.containsKey(blocksPrice.getId())){
            blocks_log.put(blocksPrice.getId(), blocks_log.get(blocksPrice.getId()) + count);
        } else{
            blocks_log.put(blocksPrice.getId(), count);
        }
    }

    public void setBlockCount(BlocksPrice blocksPrice, int count) {
        this.blocks_log.put(blocksPrice.getId(), count);
    }

    //Переписать
    public int getProgress(){
        LevelInfo levelInfo = BossFight.getInstance().getLevelManager().getNextLevel(player);

        if(this.level >= BossFight.getInstance().getLevelManager().getMaxLevel()) return 100;

        AtomicDouble exp = new AtomicDouble();
        AtomicInteger block = new AtomicInteger();
        this.getBlocks_log().forEach((blockKey, blockValue) -> {
            levelInfo.getBlocks().forEach((levelKey, levelValue) -> {
                BlocksPrice blocksPrice = BossFight.getInstance().getBlocksPriceManager().getId(levelKey);
                block.set(this.getBlockCount(blocksPrice) * 50 / levelValue);
                exp.set(this.getExp() * 50 / levelInfo.getExp());
            });
        });

        return (int) Math.min(exp.get(), 50) + Math.min(block.get(), 50);
    }


    public void sellBlocks(){
        double totalPrice = 0.0D;
        int totalBlocks = 0;

        ItemStack[] itemStacks = player.getInventory().getContents();
        for(int l = itemStacks.length, i = 0; i < l; i++){
            ItemStack itemStack = itemStacks[i];
            if (itemStack != null && BossFight.getInstance().getBlocksPriceManager().getBlocksPrice(itemStack.getType()) != null) {
                BlocksPrice blocksPrice = BossFight.getInstance().getBlocksPriceManager().getBlocksPrice(itemStack.getType());
                totalPrice += blocksPrice.getPrice() * itemStack.getAmount();
                totalBlocks += itemStack.getAmount();
                player.getInventory().setItem(i, new ItemStack(Material.AIR));
            }
        }
        if(totalPrice > 0){
            this.addBalance(totalPrice);
            ChatUtils.sendMessage(player, Arrays.asList("&aВы продали блоки!", "&fПолучено монет: &a%s", "&fПродано блоков: &a%s"), totalPrice, totalBlocks);
        } else {
            ChatUtils.sendMessage(player, Arrays.asList("&cНе получилось продать!", "&fУ вас нет блоков которые можно продать!"));
        }
    }
}
