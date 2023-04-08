package me.centrium.bossfight.level;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.concurrent.ConcurrentHashMap;

@AllArgsConstructor
@Getter
public class LevelInfo {
    public double exp;
    public ConcurrentHashMap<String, Integer> blocks;
}
