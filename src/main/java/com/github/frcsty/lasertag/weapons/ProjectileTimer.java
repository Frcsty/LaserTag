package com.github.frcsty.lasertag.weapons;

import com.github.frcsty.lasertag.LaserTag;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class ProjectileTimer
{

    private final LaserTag         plugin;
    private final List<Projectile> projectiles;
    private final Runnable runnable = new Runnable()
    {
        @Override
        public void run()
        {
            for (Projectile projectile : projectiles)
            {
                final Location location = projectile.getProjectile().getLocation();
                final String weapon = projectile.getWeapon(plugin);

                if (weapon == null)
                {
                    return;
                }
                final World world = location.getWorld();

                switch (weapon)
                {
                    case "BLASTER":
                        world.spawnParticle(Particle.DRAGON_BREATH, location, 12);
                        world.spawnParticle(Particle.CLOUD, location, 5);
                        break;
                    case "HEAVY_BLASTER":
                        world.spawnParticle(Particle.EXPLOSION_NORMAL, location, 2);
                        world.spawnParticle(Particle.FLAME, location, 7);
                    case "RIFLE":
                        world.spawnParticle(Particle.FIREWORKS_SPARK, location, 15);
                        world.spawnParticle(Particle.END_ROD, location, 6);
                }
            }
        }
    };

    public ProjectileTimer(final LaserTag plugin)
    {
        this.plugin = plugin;
        this.projectiles = new ArrayList<>();
    }

    public void runTimer()
    {
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, runnable, 0, 1);
    }

    public void addProjectile(final Projectile projectile)
    {
        if (!projectiles.contains(projectile))
        {
            projectiles.add(projectile);
        }
    }

    public void removeProjectile(final Projectile projectile)
    {
        projectiles.remove(projectile);
    }

}

