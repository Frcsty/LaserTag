package com.github.frcsty.lasertag.weapons;

import com.github.frcsty.lasertag.utility.Color;
import me.mattstudios.mfgui.gui.components.ItemNBT;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class WeaponBuilder
{

    private Weapons  weapon;
    private Material material;
    private String   name;
    private int      ammo;

    public WeaponBuilder(final Weapons weapon, final Material material)
    {
        this.weapon = weapon;
        this.material = material;
    }

    public WeaponBuilder(final Weapons weapon, final Material material, final String name)
    {
        this.weapon = weapon;
        this.material = material;
        this.name = name;
    }

    public WeaponBuilder(final Weapons weapon, final Material material, final String name, final int ammo)
    {
        this.weapon = weapon;
        this.material = material;
        this.name = name;
        this.ammo = ammo;
    }

    public ItemStack getWeapon()
    {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        if (name != null)
        {
            meta.setDisplayName(Color.colorize(name));
        }

        item = ItemNBT.setNBTTag(item, "ammo", String.valueOf(ammo));
        item = ItemNBT.setNBTTag(item, "weapon", String.valueOf(weapon));
        return item;
    }

    public void setMaterial(final Material material)
    {
        this.material = material;
    }

    public void setName(final String name)
    {
        this.name = name;
    }

    public void setAmmo(final int ammo)
    {
        this.ammo = ammo;
    }

}
