package me.centrium.bossfight.configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import lombok.val;
import me.centrium.bossfight.BossFight;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.util.Consumer;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Setter @Getter
@Accessors(chain = true, fluent = true)
public class Configuration {
    private String fileName;
    private Consumer<FileConfiguration> action;
    public static ConcurrentMap<String, Configuration> FILE_CACHE = new ConcurrentHashMap<>();

    @SneakyThrows
    public FileConfiguration build(){
        val file = new File(BossFight.getInstance().getDataFolder(), fileName.concat(".yml"));
        if(!BossFight.getInstance().getDataFolder().exists()){
            BossFight.getInstance().getDataFolder().mkdirs();
        }

        val configuration = YamlConfiguration.loadConfiguration(file);
        if(!file.exists()){
            file.createNewFile();
        }

        action.accept(configuration);
        FILE_CACHE.put(fileName, this);
        return configuration;
    }
}
