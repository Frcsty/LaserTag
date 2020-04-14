package com.github.frcsty.lasertag.commands;

import com.github.frcsty.lasertag.utility.Color;
import com.github.frcsty.lasertag.weapons.WeaponBuilder;
import com.github.frcsty.lasertag.weapons.Weapons;
import me.mattstudios.mf.annotations.Alias;
import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Completion;
import me.mattstudios.mf.annotations.SubCommand;
import me.mattstudios.mf.base.CommandBase;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@Command("laser")
@Alias("tag")
public class WeaponCommand extends CommandBase
{

    @SubCommand("give")
    public void giveCommand(final CommandSender sender, @Completion("#players") final Player target, final String object)
    {
        final Weapons weapon = Weapons.valueOf(object.toUpperCase());
        if (weapon == null)
        {
            sender.sendMessage(Color.colorize("&8[&bLaserTag&8] &7Specified weapon does not exist!"));
            return;
        }

        if (target == null || !target.isOnline())
        {
            sender.sendMessage(Color.colorize("&8[&bLaserTag&8] &7Specified user is either null or offline!"));
            return;
        }

        final Inventory inventory = target.getInventory();
        final String weaponName = weapon.getName();
        final ItemStack item = new WeaponBuilder(weapon, weapon.getMaterial(), weaponName, weapon.getAmmo(), weapon.getCustomData()).getWeapon();

        if (inventory.firstEmpty() == -1)
        {
            final Location location = target.getLocation();

            if (location.getWorld() != null)
            {
                location.getWorld().dropItem(location, item);
            }
        }
        else
        {
            inventory.addItem(item);
        }

        target.sendMessage(Color.colorize("&8[&bLaserTag&8] &7You have been given a &b{weapon}&7!".replace("{weapon}", weaponName)));
        sender.sendMessage(Color.colorize("&8[&bLaserTag&8] &7You have given &b{target-name} &7a &b{weapon}&7!".replace("{target-name}", target.getName()).replace("{weapon}", weaponName)));
    }

}
