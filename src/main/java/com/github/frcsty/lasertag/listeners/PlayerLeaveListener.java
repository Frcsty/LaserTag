package com.github.frcsty.lasertag.listeners;

import com.github.frcsty.lasertag.LaserTag;
import com.github.frcsty.lasertag.game.GameManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeaveListener implements Listener
{
    private final LaserTag plugin;

    public PlayerLeaveListener(final LaserTag plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event)
    {
        final GameManager manager = plugin.getGameManager();
        final Player player = event.getPlayer();

        for (Integer arena : manager.getArenas().keySet())
        {
            if (manager.getArenaParticipants(arena).contains(player.getUniqueId()))
            {
                manager.removeArenaParticipant(arena, player.getUniqueId());
            }
        }
    }

}
