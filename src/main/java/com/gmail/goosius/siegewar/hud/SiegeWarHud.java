package com.gmail.goosius.siegewar.hud;

import com.gmail.goosius.siegewar.SiegeWar;
import com.gmail.goosius.siegewar.enums.SiegeSide;
import com.gmail.goosius.siegewar.objects.Siege;
import com.gmail.goosius.siegewar.settings.SiegeWarSettings;
import com.palmergames.bukkit.towny.TownyEconomyHandler;
import com.palmergames.bukkit.towny.object.Translator;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.megavex.scoreboardlibrary.api.ScoreboardLibrary;
import net.megavex.scoreboardlibrary.api.sidebar.Sidebar;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class SiegeWarHud {
    public static final HashMap<Player, Sidebar> playerSidebarMap = new HashMap<>();
    private static final String[][] LEGACY_TO_MINIMESSAGE = {
            {"§0", "<black>"},
            {"§1", "<dark_blue>"},
            {"§2", "<dark_green>"},
            {"§3", "<dark_aqua>"},
            {"§4", "<dark_red>"},
            {"§5", "<dark_purple>"},
            {"§6", "<gold>"},
            {"§7", "<gray>"},
            {"§8", "<dark_gray>"},
            {"§9", "<blue>"},
            {"§a", "<green>"},
            {"§b", "<aqua>"},
            {"§c", "<red>"},
            {"§d", "<light_purple>"},
            {"§e", "<yellow>"},
            {"§f", "<white>"},
            {"§k", "<obfuscated>"},
            {"§l", "<bold>"},
            {"§m", "<strikethrough>"},
            {"§n", "<underlined>"},
            {"§o", "<italic>"},
            {"§r", "<reset>"},
            {"&0", "<black>"},
            {"&1", "<dark_blue>"},
            {"&2", "<dark_green>"},
            {"&3", "<dark_aqua>"},
            {"&4", "<dark_red>"},
            {"&5", "<dark_purple>"},
            {"&6", "<gold>"},
            {"&7", "<gray>"},
            {"&8", "<dark_gray>"},
            {"&9", "<blue>"},
            {"&a", "<green>"},
            {"&b", "<aqua>"},
            {"&c", "<red>"},
            {"&d", "<light_purple>"},
            {"&e", "<yellow>"},
            {"&f", "<white>"},
            {"&k", "<obfuscated>"},
            {"&l", "<bold>"},
            {"&m", "<strikethrough>"},
            {"&n", "<underlined>"},
            {"&o", "<italic>"},
            {"&r", "<reset>"}
    };

    public static void updateInfo(Player p, Siege siege) {
        ScoreboardLibrary scoreboardLibrary = SiegeWar.getScoreboardLibrary();
        Sidebar sidebar = playerSidebarMap.get(p);
        final Translator translator = Translator.locale(p);

        sidebar.title(MiniMessage.miniMessage().deserialize(SiegeHUDManager.checkLength("<gold>" + "<b>" + siege.getTown().getName()) + " " + translator.of("hud_title")));


        sidebar.line(0, MiniMessage.miniMessage().deserialize(convertLegacyToMiniMessage(translator.of("hud_siege_type") + siege.getSiegeType().getTranslatedName().forLocale(p))));
        sidebar.line(1, MiniMessage.miniMessage().deserialize(convertLegacyToMiniMessage(translator.of("hud_attackers") + siege.getAttackerNameForDisplay())));
        sidebar.line(2, MiniMessage.miniMessage().deserialize(convertLegacyToMiniMessage(translator.of("hud_defenders") + siege.getDefenderNameForDisplay())));
        sidebar.line(3, MiniMessage.miniMessage().deserialize(convertLegacyToMiniMessage(translator.of("hud_siege_balance") + siege.getSiegeBalance().toString())));
        sidebar.line(4, MiniMessage.miniMessage().deserialize(convertLegacyToMiniMessage(translator.of("hud_siege_progress") + siege.getNumBattleSessionsCompleted() + "/" + SiegeWarSettings.getSiegeDurationBattleSessions())));
        sidebar.line(5, MiniMessage.miniMessage().deserialize(convertLegacyToMiniMessage(translator.of("hud_warchest") + (TownyEconomyHandler.isActive() ? TownyEconomyHandler.getFormattedBalance(siege.getWarChestAmount()) : "-"))));
        sidebar.line(6, MiniMessage.miniMessage().deserialize(convertLegacyToMiniMessage(translator.of("hud_banner_control") + siege.getBannerControllingSide().getFormattedName().forLocale(p) + (siege.getBannerControllingSide() == SiegeSide.NOBODY ? "" : " (" + siege.getBannerControllingResidents().size() + ")"))));
        sidebar.line(7, MiniMessage.miniMessage().deserialize(convertLegacyToMiniMessage(translator.of("hud_battle_attacker_points") + siege.getFormattedAttackerBattlePoints())));
        sidebar.line(8, MiniMessage.miniMessage().deserialize(convertLegacyToMiniMessage(translator.of("hud_battle_defender_points") + siege.getFormattedDefenderBattlePoints())));
        sidebar.line(9, MiniMessage.miniMessage().deserialize(convertLegacyToMiniMessage(translator.of("hud_battle_time_remaining") + siege.getFormattedBattleTimeRemaining(translator))));

        if (!sidebar.players().contains(p)) {
            sidebar.addPlayer(p);
        }
//        Scoreboard board = p.getScoreboard();
//        if (board == null) {
//            toggleOn(p, siege);
//            return;
//        }
//        final Translator translator = Translator.locale(p);
//        board.getObjective("WAR_HUD_OBJ").setDisplayName(SiegeHUDManager.checkLength(ChatColor.GOLD + "§l" + siege.getTown().getName()) + " " + translator.of("hud_title"));
//        board.getTeam("siegeType").setSuffix(SiegeHUDManager.checkLength(siege.getSiegeType().getTranslatedName().forLocale(p)));
//        board.getTeam("attackers").setSuffix(SiegeHUDManager.checkLength(siege.getAttackerNameForDisplay()));
//        board.getTeam("defenders").setSuffix(SiegeHUDManager.checkLength(siege.getDefenderNameForDisplay()));
//        board.getTeam("balance").setSuffix(siege.getSiegeBalance().toString());
//        board.getTeam("siegeProgress").setSuffix(siege.getNumBattleSessionsCompleted() + "/" + SiegeWarSettings.getSiegeDurationBattleSessions());
//        board.getTeam("siegeStatus").setSuffix(siege.getStatus().getName());
//        if(TownyEconomyHandler.isActive()) {
//            board.getTeam("warchest").setSuffix(TownyEconomyHandler.getFormattedBalance(siege.getWarChestAmount()));
//        } else {
//            board.getTeam("warchest").setSuffix("-");
//        }
//        board.getTeam("bannerControl").setSuffix(
//            siege.getBannerControllingSide().getFormattedName().forLocale(p)
//            + (siege.getBannerControllingSide() == SiegeSide.NOBODY ? "" :  " (" + siege.getBannerControllingResidents().size() + ")"));
//        board.getTeam("btAttackerPoints").setSuffix(siege.getFormattedAttackerBattlePoints());
//        board.getTeam("btDefenderPoints").setSuffix(siege.getFormattedDefenderBattlePoints());
//        board.getTeam("btTimeRemaining").setSuffix(siege.getFormattedBattleTimeRemaining(translator));
    }

    public static void toggleOn(Player p, Siege siege) {
//        if (playerSidebarMap.containsKey(p)) {
//            playerSidebarMap.get(p).removePlayer(p);
//            playerSidebarMap.get(p).close();
//            playerSidebarMap.remove(p);
//            return;
//        }

        final Translator translator = Translator.locale(p);
        ScoreboardLibrary scoreboardLibrary = SiegeWar.getScoreboardLibrary();
        Sidebar sidebar = scoreboardLibrary.createSidebar();

        sidebar.title(MiniMessage.miniMessage().deserialize(convertLegacyToMiniMessage(translator.of("hud_title"))));
        sidebar.line(0, MiniMessage.miniMessage().deserialize(convertLegacyToMiniMessage(translator.of("hud_siege_type"))));
        sidebar.line(1, MiniMessage.miniMessage().deserialize(convertLegacyToMiniMessage(translator.of("hud_attackers"))));
        sidebar.line(2, MiniMessage.miniMessage().deserialize(convertLegacyToMiniMessage(translator.of("hud_defenders"))));
        sidebar.line(3, MiniMessage.miniMessage().deserialize(convertLegacyToMiniMessage(translator.of("hud_siege_balance"))));
        sidebar.line(4, MiniMessage.miniMessage().deserialize(convertLegacyToMiniMessage(translator.of("hud_siege_progress"))));
        sidebar.line(5, MiniMessage.miniMessage().deserialize(convertLegacyToMiniMessage(translator.of("hud_warchest"))));
        sidebar.line(6, MiniMessage.miniMessage().deserialize(convertLegacyToMiniMessage(translator.of("hud_banner_control"))));
        sidebar.line(7, MiniMessage.miniMessage().deserialize(convertLegacyToMiniMessage(translator.of("hud_battle_attacker_points"))));
        sidebar.line(8, MiniMessage.miniMessage().deserialize(convertLegacyToMiniMessage(translator.of("hud_battle_defender_points"))));
        sidebar.line(9, MiniMessage.miniMessage().deserialize(convertLegacyToMiniMessage(translator.of("hud_battle_time_remaining"))));
        //        sidebar.line(10, MiniMessage.miniMessage().deserialize(convertLegacyToMiniMessage(translator.of("hud_attackers"))));
//        sidebar.line(9, MiniMessage.miniMessage().deserialize(convertLegacyToMiniMessage(translator.of("hud_defenders"))));
//        sidebar.line(8, MiniMessage.miniMessage().deserialize(convertLegacyToMiniMessage(translator.of("hud_siege_type"))));
//        sidebar.line(7, MiniMessage.miniMessage().deserialize(convertLegacyToMiniMessage(translator.of("hud_warchest"))));
//        sidebar.line(6, MiniMessage.miniMessage().deserialize(convertLegacyToMiniMessage(translator.of("hud_siege_progress"))));
//        sidebar.line(5, MiniMessage.miniMessage().deserialize(convertLegacyToMiniMessage(translator.of("hud_siege_status"))));
//        sidebar.line(4, MiniMessage.miniMessage().deserialize(convertLegacyToMiniMessage(translator.of("hud_siege_balance"))));
//        sidebar.line(3, MiniMessage.miniMessage().deserialize(convertLegacyToMiniMessage(translator.of("hud_banner_control"))));
//        sidebar.line(2, MiniMessage.miniMessage().deserialize(convertLegacyToMiniMessage(translator.of("hud_battle_attacker_points"))));
//        sidebar.line(1, MiniMessage.miniMessage().deserialize(convertLegacyToMiniMessage(translator.of("hud_battle_defender_points"))));
//        sidebar.line(0, MiniMessage.miniMessage().deserialize(convertLegacyToMiniMessage(translator.of("hud_battle_time_remaining"))));

        sidebar.addPlayer(p);
        playerSidebarMap.put(p, sidebar);
        updateInfo(p, siege);

//        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
//        Objective objective = board.registerNewObjective("WAR_HUD_OBJ", "", translator.of("hud_title"));
//        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
//
//        Team siegeType = board.registerNewTeam("siegeType"),
//            attackers = board.registerNewTeam("attackers"),
//            defenders = board.registerNewTeam("defenders"),
//            balance = board.registerNewTeam("balance"),
//            siegeProgress = board.registerNewTeam("siegeProgress"),
//            siegeStatus = board.registerNewTeam("siegeStatus"),
//            warchest = board.registerNewTeam("warchest"),
//            bannerControl = board.registerNewTeam("bannerControl"),
//            battleAttackerScore = board.registerNewTeam("btAttackerPoints"),
//            battleDefenderScore = board.registerNewTeam("btDefenderPoints"),
//            battleTimeRemaining = board.registerNewTeam("btTimeRemaining");
//
//            String siegeType_entry = ChatColor.GRAY + translator.of("hud_siege_type"),
//            attackers_entry = ChatColor.GRAY + translator.of("hud_attackers"),
//            defenders_entry = ChatColor.GRAY + translator.of("hud_defenders"),
//            balance_entry = ChatColor.GRAY + translator.of("hud_siege_balance"),
//            siegeProgress_entry = ChatColor.GRAY + translator.of("hud_siege_progress"),
//            siegeStatus_entry = ChatColor.GRAY + translator.of("hud_siege_status"),
//            warchest_entry = ChatColor.GRAY + translator.of("hud_warchest"),
//            bannerControl_entry = ChatColor.GRAY + translator.of("hud_banner_control"),
//            battleAttackerScore_entry = ChatColor.GRAY + translator.of("hud_battle_attacker_points"),
//            battleDefenderScore_entry = ChatColor.GRAY + translator.of("hud_battle_defender_points"),
//            battleTimeRemaining_entry = ChatColor.GRAY + translator.of("hud_battle_time_remaining");
//
//        siegeType.addEntry(siegeType_entry);
//        attackers.addEntry(attackers_entry);
//        defenders.addEntry(defenders_entry);
//        balance.addEntry(balance_entry);
//        bannerControl.addEntry(bannerControl_entry);
//        siegeProgress.addEntry(siegeProgress_entry);
//        siegeStatus.addEntry(siegeStatus_entry);
//        warchest.addEntry(warchest_entry);
//        battleDefenderScore.addEntry(battleDefenderScore_entry);
//        battleAttackerScore.addEntry(battleAttackerScore_entry);
//        battleTimeRemaining.addEntry(battleTimeRemaining_entry);
//
//        int topScore = 10;
//
//        objective.getScore(attackers_entry).setScore(topScore--);
//        objective.getScore(defenders_entry).setScore(topScore--);
//        objective.getScore(siegeType_entry).setScore(topScore--);
//        objective.getScore(warchest_entry).setScore(topScore--);
//        objective.getScore(siegeProgress_entry).setScore(topScore--);
//        objective.getScore(siegeStatus_entry).setScore(topScore--);
//        objective.getScore(balance_entry).setScore(topScore--);
//        objective.getScore(bannerControl_entry).setScore(topScore--);
//        objective.getScore(battleAttackerScore_entry).setScore(topScore--);
//        objective.getScore(battleDefenderScore_entry).setScore(topScore--);
//        objective.getScore(battleTimeRemaining_entry).setScore(topScore--);
//
//        p.setScoreboard(board);
//        updateInfo(p, siege);
    }

    public static String convertLegacyToMiniMessage(String legacyText) {
        for (String[] mapping : LEGACY_TO_MINIMESSAGE) {
            legacyText = legacyText.replace(mapping[0], mapping[1]);
        }

        legacyText = legacyText.replaceAll("§.", "");

        return legacyText;
    }
}