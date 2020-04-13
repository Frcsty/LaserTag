package com.github.frcsty.lasertag.weapons;

import com.github.frcsty.lasertag.LaserTag;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.persistence.PersistentDataType;

public class Projectile
{

    private org.bukkit.entity.Projectile projectile;

    public void launchProjectile(final Player player, final LaserTag plugin, final String weapon)
    {
        projectile = player.launchProjectile(Snowball.class);
        projectile.getPersistentDataContainer().set(new NamespacedKey(plugin, "weapon"), PersistentDataType.STRING, weapon);
        projectile.setBounce(false);
        projectile.setShooter(player);
        projectile.setGravity(false);
        projectile.setVelocity(player.getEyeLocation().getDirection().multiply(3));
    }

    public void removeProjectile()
    {
        if (projectile != null)
        {
            projectile.remove();
        }
    }

    String getWeapon(final LaserTag plugin)
    {
        return projectile.getPersistentDataContainer().get(new NamespacedKey(plugin, "weapon"), PersistentDataType.STRING);
    }

    org.bukkit.entity.Projectile getProjectile()
    {
        return projectile;
    }

}
