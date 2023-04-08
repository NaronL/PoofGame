package me.centrium.bossfight.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.centrium.bossfight.BossFight;
import me.centrium.bossfight.utils.ItemBuilder;
import me.centrium.bossfight.utils.NMSUtils;
import net.minecraft.server.v1_16_R3.ItemSign;
import net.minecraft.server.v1_16_R3.NBTTagString;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

@Getter
@AllArgsConstructor
public class ItemInfo {
    private String id;
    private String nextId;
    private String name;
    private String lore;
    private Material material;
    private int price;
    private int level;
    private Map<String, Integer> enchantment;

    public ItemStack getUsableItem(){
        ItemBuilder itemBuilder = ItemBuilder.newBuilder(material);

        if(this.name != null){
            itemBuilder.name(name);
        }

        if(lore != null){
            String[] loreString = lore.split("\\r?\\n");
            itemBuilder.addLore("");
            for(String s : loreString){
                itemBuilder.addLore(s);
            }
        }

        if(this.enchantment.size() > 0){
            this.enchantment.entrySet().stream()
                    .filter(enchantment -> enchantment.getValue() > 0)
                    .forEach(enchantment -> itemBuilder.addEnchantment(Enchantment.getByName(enchantment.getKey()), enchantment.getValue()));
        }

        itemBuilder.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        itemBuilder.unbreakable(true);
        return NMSUtils.setTag(itemBuilder.build(), "upgrading", NBTTagString.a(this.id));
    }

    public ItemStack getUpgradeItem(){
        ItemBuilder itemBuilder = ItemBuilder.newBuilder(this.getNextItem().getMaterial());
        ItemInfo itemInfo = getItem(this.nextId);

        if(this.getNextItem().getName() != null){
            itemBuilder.name(this.getNextItem().getName());
        }

        if(this.getNextItem().getLore() != null){
            String[] loreString = lore.split("\\r?\\n");
            itemBuilder.addLore("");
            for(String s : loreString){
                itemBuilder.addLore(s);
            }
        }

        if(this.getNextItem().getEnchantment().size() > 0){
            this.getNextItem().getEnchantment().entrySet().stream()
                    .filter(enchantment -> enchantment.getValue() > 0)
                    .forEach(enchantment -> itemBuilder.addEnchantment(Enchantment.getByName(enchantment.getKey()), enchantment.getValue()));
        }

        itemBuilder.unbreakable(true);
        return NMSUtils.setTag(itemBuilder.build(), "upgrading", NBTTagString.a(this.getNextItem().getId()));

    }

    public static ItemInfo getItem(String id){
        for(ItemInfo itemInfo : BossFight.getInstance().getItemManager().getCache().values()){
            if(itemInfo.getId().equalsIgnoreCase(id)){
                return itemInfo;
            }
        }
        return null;
    }

    public ItemInfo getItem(ItemStack itemStack) {
        NBTTagString tag = NMSUtils.getTag(itemStack, "upgrading");
        String tagString = tag != null ? tag.asString() : null;
        return getItem(tagString);
    }

    public boolean hasNext(){
        return this.nextId != null;
    }

    public ItemInfo getNextItem(){
        return BossFight.getInstance().getItemManager().getCache().getOrDefault(this.nextId, null);
    }

    public static boolean isCustomItem(ItemStack itemStack) {
        NBTTagString tag = NMSUtils.getTag(itemStack, "upgrading");
        String tagString = tag != null ? tag.toString() : null;
        try {
            return BossFight.getInstance().getItemManager().getCache().containsKey(tagString);
        } catch (NullPointerException exception) {
            exception.printStackTrace();
            return true;
        }
    }
}
