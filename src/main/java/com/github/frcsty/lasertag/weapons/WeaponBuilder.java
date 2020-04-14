package com.github.frcsty.lasertag.weapons;

import com.github.frcsty.lasertag.utility.Color;
import me.mattstudios.mfgui.gui.components.ItemNBT;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class WeaponBuilder
{

    private Weapons      weapon;
    private Material     material;
    private String       name;
    private int          ammo;
    private List<String> lore;
    private int customData;

    public WeaponBuilder(final Weapons weapon, final Material material, final String name, final int ammo, final int customData)
    {
        this.weapon = weapon;
        this.material = material;
        this.name = name;
        this.ammo = ammo;
        this.customData = customData;
    }

    public WeaponBuilder(final Weapons weapon, final Material material, final String name, final int ammo, final List<String> lore, final int customData)
    {
        this.weapon = weapon;
        this.material = material;
        this.name = name;
        this.ammo = ammo;
        this.lore = lore;
        this.customData = customData;
    }

    public ItemStack getWeapon()
    {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        if (name != null)
        {
            meta.setDisplayName(Color.colorize(name));
        }

        if (lore != null)
        {
            meta.setLore(Color.colorize(lore));
        }

        meta.setCustomModelData(customData);

        item.setItemMeta(meta);
        item = ItemNBT.setNBTTag(item, "ammo", String.valueOf(ammo));
        item = ItemNBT.setNBTTag(item, "weapon", String.valueOf(weapon));
        return item;
    }

}
