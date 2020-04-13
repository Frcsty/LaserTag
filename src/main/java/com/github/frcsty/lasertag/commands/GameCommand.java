package com.github.frcsty.lasertag.commands;

import com.github.frcsty.lasertag.LaserTag;
import com.github.frcsty.lasertag.commands.menus.GameSelector;
import com.github.frcsty.lasertag.commands.menus.ParticipantsMenu;
import com.github.frcsty.lasertag.commands.menus.WeaponSelector;
import com.github.frcsty.lasertag.game.GameManager;
import com.github.frcsty.lasertag.utility.Color;
import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Default;
import me.mattstudios.mf.annotations.SubCommand;
import me.mattstudios.mf.base.CommandBase;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.logging.Level;

@Command("game")
public class GameCommand extends CommandBase
{

    private final LaserTag plugin;

    public GameCommand(final LaserTag plugin)
    {
        this.plugin = plugin;
    }

    @Default
    public void commandList(final CommandSender sender)
    {
        final ConfigurationSection commands = plugin.getConfig().getConfigurationSection("game");

        if (commands == null)
        {
            plugin.getLogger().log(Level.WARNING, "Configuration section 'game' does not exist!");
            return;
        }

        for (final String cmd : commands.getStringList("commands"))
        {
            sender.sendMessage(Color.colorize(cmd));
        }
    }

    @SubCommand("join")
    public void joinGame(final Player player)
    {
        new GameSelector(plugin, player).getGameSelector();
    }

    @SubCommand("participants")
    public void gameParticipants(final Player player, final int arena)
    {
        new ParticipantsMenu(plugin, player, arena).getParticipantsMenu();
    }

    @SubCommand("start")
    public void startGame(final Player player, final int arena)
    {
        final GameManager manager = plugin.getGameManager();

        if (manager.getArenaParticipants(arena).isEmpty())
        {
            player.sendMessage(Color.colorize("&8[&bLaserTag&8] &7Not enough players to start game!"));
            return;
        }
        manager.startGameTimer();
    }

    @SubCommand("weapons")
    public void selectWeapon(final Player player)
    {
        new WeaponSelector(plugin, player).getWeaponSelector();
    }

}