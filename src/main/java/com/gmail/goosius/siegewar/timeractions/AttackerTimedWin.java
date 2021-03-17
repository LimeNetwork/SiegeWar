package com.gmail.goosius.siegewar.timeractions;

import com.gmail.goosius.siegewar.Messaging;
import com.gmail.goosius.siegewar.enums.SiegeStatus;
import com.gmail.goosius.siegewar.objects.Siege;
import com.gmail.goosius.siegewar.settings.Translation;

/**
 * This class is responsible for processing timed attacker wins
 * i.e. when the siege victory timer hits 0
 *
 * @author Goosius
 */
public class AttackerTimedWin {

    public static void attackerTimedWin(Siege siege) {
        AttackerWin.attackerWin(siege, SiegeStatus.ATTACKER_WIN);
        Messaging.sendGlobalMessage(getTimedAttackerWinMessage(siege));
    }

    private static String getTimedAttackerWinMessage(Siege siege) {
        String key = String.format("msg_%s_siege_timed_attacker_win", siege.getSiegeType().toString().toLowerCase());
        String message = "";
        switch (siege.getSiegeType()) {
            case CONQUEST:
            case LIBERATION:
                message = Translation.of(key,
                        siege.getTown().getFormattedName(),
                        siege.getAttacker().getFormattedName(),
                        siege.getDefender().getFormattedName());
                break;
            case REVOLT:
                message = Translation.of(key,
                        siege.getTown().getFormattedName(),
                        siege.getDefender().getFormattedName());
                break;
            case SUPPRESSION:
                message = Translation.of(key,
                        siege.getTown().getFormattedName(),
                        siege.getAttacker().getFormattedName());
                break;
        }
        message += Translation.of("msg_immediate_attacker_victory");
        return message;
    }
}
