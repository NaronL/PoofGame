package me.centrium.bossfight.database;

import lombok.Getter;
import sexy.kostya.mineos.sql.Connector;
import sexy.kostya.mineos.sql.ConnectorBuilder;

@Getter
public class DatabaseConnector {

    private final Connector connector;

    public DatabaseConnector(String host, String database, String user, String name, String password){
        connector = new ConnectorBuilder()
                .setHost(host)
                .setDatabase(database)
                .setUser(user)
                .setName(name)
                .setPassword(password)
                .setCharacterEncoding(ConnectorBuilder.CharacterEncoding.UTF8)
                .build(true);

        this.createTables();
    }

    public void createTables(){
        connector.query("CREATE TABLE IF NOT EXISTS bossfight_blocks (id TEXT, name TEXT, material TEXT, price double, level int)\n" +
                "        CREATE TABLE IF NOT EXISTS bossfight_mines (id TEXT, name TEXT, lore TEXT, material TEXT, level int, location TEXT, slot int)\n" +
                "        CREATE TABLE IF NOT EXISTS bossfight_level (id TEXT, exp double, blocks TEXT)\n" +
                "        CREATE TABLE IF NOT EXISTS bossfight_items (id TEXT, nextId TEXT, name TEXT, lore TEXT, material TEXT, price int, level int, enchantment TEXT)\n" +
                "        CREATE TABLE IF NOT EXISTS bossfight_faction (id TEXT, name TEXT, color TEXT, prefix TEXT, material int, location TEXT, slot int)\n" +
                "        CREATE TABLE IF NOT EXISTS bossfight_faction (CREATE TABLE IF NOT EXISTS bossfight_users (name TEXT, level int, balance double, totalblocks double, exp double, ruby int, kills int, deaths int, faction TEXT, block_log TEXT, settings TEXT))\n" +
                "        CREATE TABLE IF NOT EXISTS bossfight_events (id TEXT, name TEXT, effectType TEXT, delay int, amplifier int");
    }
}
