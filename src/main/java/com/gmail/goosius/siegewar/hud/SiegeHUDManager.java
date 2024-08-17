package com.gmail.goosius.siegewar.hud;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.gmail.goosius.siegewar.SiegeWar;
import com.gmail.goosius.siegewar.objects.Siege;

import net.megavex.scoreboardlibrary.api.ScoreboardLibrary;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

public class SiegeHUDManager {

    static Map<Player, Siege> warHudUsers;

    public SiegeHUDManager() {
        warHudUsers = new HashMap<>();
    }

    public void toggleWarHud(Player player, Siege siege) {
        if (!warHudUsers.containsKey(player)) {
            warHudUsers.put(player, siege);
            SiegeWarHud.toggleOn(player, siege);
        } else if (warHudUsers.get(player) != siege) {
            warHudUsers.replace(player, siege);
            SiegeWarHud.updateInfo(player, siege);
        } else
            toggleOff(player);
    }

    public static void toggleOff(Player player) {
        warHudUsers.remove(player);
        if (player.isOnline()) {
            SiegeWarHud.playerSidebarMap.get(player).removePlayer(player);
            SiegeWarHud.playerSidebarMap.get(player).close();
            SiegeWarHud.playerSidebarMap.remove(player);
        }
    }

    public static void updateHUDs() {
        for (Entry<Player, Siege> entry : new ArrayList<>(warHudUsers.entrySet())) {
            if (entry.getKey().getScoreboard().getTeam("balance") == null) {
                warHudUsers.remove(entry.getKey());
                continue;
            } else
                SiegeWarHud.updateInfo(entry.getKey(), entry.getValue());
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (SiegeWarHud.playerSidebarMap.containsKey(player)) {
            warHudUsers.remove(player);
            SiegeWarHud.playerSidebarMap.get(player).removePlayer(player);
            SiegeWarHud.playerSidebarMap.get(player).close();
            SiegeWarHud.playerSidebarMap.remove(player);
        }
    }

    public static String checkLength(String string) {
        return string.length() > 32 ? string.substring(0, 32) + "..." : string;
    }
}
