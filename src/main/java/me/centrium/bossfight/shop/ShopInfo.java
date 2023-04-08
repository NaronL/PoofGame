package me.centrium.bossfight.shop;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ShopInfo {
    private String id;
    private String item;
    private int level;
    private int price;
    private int amount;
    private int slot;
}
