package com.github.frcsty.lasertag.listeners;

import com.github.frcsty.lasertag.LaserTag;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerTagListener implements Listener
{

    private final LaserTag plugin;

    public PlayerTagListener(final LaserTag plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerTag(EntityDamageByEntityEvent event)
    {
        if (!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Player))
        {
            event.setCancelled(true);
            return;
        }

        final Player hitPlayer = (Player) event.getEntity();
        final Player damager = (Player) event.getDamager();

        if (!event.getCause().equals(EntityDamageEvent.DamageCause.PROJECTILE))
        {
            event.setCancelled(true);
            return;
        }

        System.out.println(hitPlayer.getName() + " has been shot by " + damager.getName());
    }

}
