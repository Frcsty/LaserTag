package com.github.frcsty.lasertag.listeners;

import com.github.frcsty.lasertag.LaserTag;
import com.github.frcsty.lasertag.game.GameManager;
import com.github.frcsty.lasertag.utility.Color;
import com.github.frcsty.lasertag.weapons.Projectile;
import com.github.frcsty.lasertag.weapons.ProjectileTimer;
import com.github.frcsty.lasertag.weapons.Weapons;
import me.mattstudios.mfgui.gui.components.ItemNBT;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class WeaponShootListener implements Listener
{

    private final LaserTag plugin;
    private       Projectile     projectile;
    private final ProjectileTimer timer;
    private boolean reloading = false;

    public WeaponShootListener(final LaserTag plugin)
    {
        this.plugin = plugin;
        this.timer = plugin.getProjectileTimer();
    }

    @EventHandler
    public void onWeaponsShoot(PlayerInteractEvent event)
    {
        final Player player = event.getPlayer();
        ItemStack item = event.getItem();
        final Action action = event.getAction();
        final String weapon = ItemNBT.getNBTTag(item, "weapon");

        if (weapon == null || item == null || item.getType() != Material.WOODEN_HOE)
        {
            return;
        }

        final int ammo = Integer.valueOf(ItemNBT.getNBTTag(item, "ammo"));
        int newAmmo = ammo;

        if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK)
        {
            final Weapons weapons = Weapons.valueOf(weapon.toUpperCase());
            if (ammo < weapons.getAmmo())
            {
                newAmmo = weapons.getAmmo();
            }
            reloading = true;
            new BukkitRunnable()
            {
                @Override
                public void run()
                {
                    player.sendActionBar(Color.colorize("&8-= &bReloading &8=-"));

                }
            }.runTaskTimerAsynchronously(plugin, 0, 20);
            reloading = false;
            item = ItemNBT.setNBTTag(item, "ammo", String.valueOf(newAmmo));
            player.getInventory().setItemInMainHand(item);
        }

        if (reloading)
        {
            return;
        }

        if (action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR)
        {
            if (newAmmo > 0)
            {
                newAmmo = ammo - 1;
                player.playSound(player.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 0.5f, 1f);
                item = ItemNBT.setNBTTag(item, "ammo", String.valueOf(newAmmo));
                player.getInventory().setItemInMainHand(item);

                projectile = new Projectile();
                projectile.launchProjectile(player, plugin, weapon);
                timer.addProjectile(projectile);
            }
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event)
    {
        if (!(event.getEntity() instanceof Snowball))
        {
            return;
        }
        if (event.getHitBlock() != null)
        {
            timer.removeProjectile(projectile);
            projectile.removeProjectile();
        }
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
