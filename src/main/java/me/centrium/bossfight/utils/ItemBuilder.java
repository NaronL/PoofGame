package me.centrium.bossfight.utils;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.luvas.rmcs.utils.Util;

import java.util.*;

@Getter
public class ItemBuilder {

    private final ItemStack itemStack;

    public static ItemBuilder newBuilder(Material material) {
        if (material == null) throw new NullPointerException("Material == null");

        return new ItemBuilder(new ItemStack(material));
    }

    public static ItemBuilder newBuilder(ItemStack itemStack) {
        if (itemStack == null) throw new NullPointerException("ItemStack == null");

        return new ItemBuilder(itemStack.clone());
    }

    public ItemBuilder unbreakable(boolean value){
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setUnbreakable(value);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder material(Material material){
        itemStack.setType(material);
        return this;
    }

    public ItemBuilder amount(int amount){
        this.itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder name(String name){
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(Util.f(name));
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder lore(String... lore){
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(Util.f(Arrays.asList(lore)));
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder lore(List<String> lore){
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(Util.f(lore));
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder addLore(String lore){
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> loreList = itemMeta.getLore();
        if(loreList == null) loreList = new ArrayList<String>();

        loreList.add(lore);
        return this.lore(lore);
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int level){
        if(enchantment == null) return this;

        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.addEnchant(enchantment, level, true);
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder removeEnchantment(final Enchantment enchantment) {
        if (enchantment == null) return this;

        ItemMeta itemMeta = this.itemStack.getItemMeta();
        if (!itemMeta.hasEnchant(enchantment) || itemMeta.hasConflictingEnchant(enchantment)) return this;

        itemMeta.removeEnchant(enchantment);
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder addItemFlag(ItemFlag itemFlag) {
        if (itemFlag == null) return this;

        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.addItemFlags(new ItemFlag[] { itemFlag });
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder addItemFlags(ItemFlag... itemFlags) {
        if (itemFlags == null) return this;

        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.addItemFlags(itemFlags);
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder removeItemFlag(ItemFlag itemFlag) {
        if (itemFlag == null) return this;

        ItemMeta itemMeta = this.itemStack.getItemMeta();
        if (!itemMeta.hasItemFlag(itemFlag)) return this;

        itemMeta.removeItemFlags(new ItemFlag[] { itemFlag });
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder addLore(String... lore) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        List<String> loreList = itemMeta.getLore();
        if (loreList == null) loreList = new ArrayList<String>();

        loreList.addAll(Util.f(Arrays.asList(lore)));
        return this.lore(loreList);
    }

    public ItemBuilder(ItemStack itemStack){
        this.itemStack = itemStack;
    }

    public ItemStack build(){
        return this.itemStack;
    }

}
