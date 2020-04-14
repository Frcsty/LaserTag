package com.github.frcsty.lasertag.weapons;

import com.github.frcsty.lasertag.LaserTag;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProjectileTimer
{

    private final LaserTag         plugin;
    private final List<Projectile> projectiles = new ArrayList<>();
    private final Runnable         runnable    = new Runnable()
    {
        @Override
        public void run()
        {
            Iterator<Projectile> iterator = projectiles.iterator();
            while (iterator.hasNext())
            {
                Projectile projectile = iterator.next();

                final Location location = projectile.getProjectile().getLocation();
                final Weapons weapon = projectile.getWeapon(plugin);

                if (weapon == null)
                {
                    return;
                }
                final World world = location.getWorld();

                switch (weapon)
                {
                    case DH_17:
                        world.spawnParticle(Particle.BLOCK_DUST, location, 12, 0, 0, 0, Material.WHITE_CONCRETE_POWDER.createBlockData());
                        break;
                    case E_11:
                    case DL_44:
                        world.spawnParticle(Particle.DRAGON_BREATH, location, 6, 0, 0, 0, 0);
                        world.spawnParticle(Particle.ENCHANTMENT_TABLE, location, 4, 0, 0, 0, 0);
                        break;
                    case T_21:
                        world.spawnParticle(Particle.DRAGON_BREATH, location, 12, 0, 0, 0, 0);
                        world.spawnParticle(Particle.CLOUD, location, 3, 0, 0, 0, 0);
                        break;
                    case EMPEROR_4:
                        world.spawnParticle(Particle.CLOUD, location, 5, 0, 0, 0, 0);
                        break;
                    case ION_BLASTER:
                        world.spawnParticle(Particle.FIREWORKS_SPARK, location, 2, 0, 0, 0, 0);
                        break;
                    case BOW_CASTER:
                        world.spawnParticle(Particle.EXPLOSION_NORMAL, location, 2, 0, 0, 0, 0);
                        break;
                    case DX_2:
                        world.spawnParticle(Particle.FIREWORKS_SPARK, location, 15, 0, 0, 0, 0);
                        world.spawnParticle(Particle.END_ROD, location, 6, 0, 0, 0, 0);
                        break;
                    case DXR_6:
                        world.spawnParticle(Particle.FIREWORKS_SPARK, location, 5, 0, 0, 0, 0);
                        world.spawnParticle(Particle.END_ROD, location, 4, 0, 0, 0, 0);
                        break;
                    case SD_77:
                    case SG_82:
                        world.spawnParticle(Particle.CLOUD, location, 10, 0, 0, 0, 0);
                        world.spawnParticle(Particle.FALLING_DUST, location, 3, 0, 0, 0, 0, Material.WHITE_CONCRETE_POWDER.createBlockData());
                        break;
                    case C_22:
                    case CR_24:
                        world.spawnParticle(Particle.DRAGON_BREATH, location, 4, 0, 0, 0, 0);
                        world.spawnParticle(Particle.FLAME, location, 6, 0, 0, 0, 0);
                        break;
                    case PLX_2M:
                        world.spawnParticle(Particle.EXPLOSION_LARGE, location, 2, 0, 0, 0, 0);
                }

                if (projectile.getProjectile().getTicksLived() > 50)
                {
                    iterator.remove();
                    removeProjectile(projectile);
                    projectile.removeProjectile();
                }
            }
        }
    };

    public ProjectileTimer(final LaserTag plugin)
    {
        this.plugin = plugin;
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

