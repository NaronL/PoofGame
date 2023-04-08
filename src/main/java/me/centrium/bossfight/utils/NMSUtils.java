package me.centrium.bossfight.utils;

import lombok.experimental.UtilityClass;
import me.centrium.bossfight.utils.reflect.FieldAccessor;
import me.centrium.bossfight.utils.reflect.Reflection;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_16_R3.IChatFormatted;
import net.minecraft.server.v1_16_R3.NBTBase;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.util.Iterator;
import java.util.Map;

@UtilityClass
public class NMSUtils {
    private static final FieldAccessor<Map<String, NBTBase>> mapReflect = Reflection.fieldAccessor(Reflection.getField(NBTTagCompound.class, "map"));

    public ItemStack setTag(ItemStack item, String key, NBTBase value) {
        net.minecraft.server.v1_16_R3.ItemStack nms = CraftItemStack.asNMSCopy(item);
        NBTTagCompound nmsTag = nms.getTag();
        if (nmsTag == null) {
            nmsTag = new NBTTagCompound();
        }

        nmsTag.set(key, value);
        nms.setTag(nmsTag);
        return CraftItemStack.asBukkitCopy(nms);
    }

    public ItemStack setTags(ItemStack item, Map<String, NBTBase> tags) {
        net.minecraft.server.v1_16_R3.ItemStack nms = CraftItemStack.asNMSCopy(item);
        NBTTagCompound nmsTag = nms.getTag();
        if (nmsTag == null) {
            nmsTag = new NBTTagCompound();
        }

        tags.forEach((k, v) -> {
            setTag(item, k, v);
        });
        Iterator var4 = tags.keySet().iterator();

        while(var4.hasNext()) {
            String key = (String)var4.next();
            nmsTag.set(key, (NBTBase)tags.get(key));
        }

        return CraftItemStack.asBukkitCopy(nms);
    }

    public <T extends NBTBase> T getTag(ItemStack item, String key) {
        net.minecraft.server.v1_16_R3.ItemStack nms = CraftItemStack.asNMSCopy(item);
        NBTTagCompound nmsTag = nms.getTag();
        if (nmsTag == null) {
            nmsTag = new NBTTagCompound();
        }

        return nmsTag.get(key) == null ? null : (T) nmsTag.get(key);
    }

    public Map<String, NBTBase> getTags(ItemStack item) {
        net.minecraft.server.v1_16_R3.ItemStack nms = CraftItemStack.asNMSCopy(item);
        NBTTagCompound nmsTag = nms.getTag();
        if (nmsTag == null) {
            nmsTag = new NBTTagCompound();
        }

        return (Map)mapReflect.get(nmsTag);
    }

    public TextComponent toTextComponent(ItemStack item) {
        NBTTagCompound tag = CraftItemStack.asNMSCopy(item).save(new NBTTagCompound());
        TextComponent tc = new TextComponent("§7[" + getI18NDisplayName(item) + (item.getAmount() > 1 ? " §fx" + item.getAmount() : "") + "§7]");
        tc.setHoverEvent(new HoverEvent(Action.SHOW_ITEM, new BaseComponent[]{new TextComponent(tag.toString())}));
        return tc;
    }

    public IChatFormatted getI18NDisplayName(ItemStack item) {
        net.minecraft.server.v1_16_R3.ItemStack nms = CraftItemStack.asNMSCopy(item);
        return nms != null ? nms.getName() : null;
    }
}
