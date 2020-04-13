package com.github.frcsty.lasertag.weapons;

import org.bukkit.Material;

public enum Weapons
{

    BLASTER(8, "&bBlaster", Material.WOODEN_HOE),
    RIFLE(24, "&bBlaster Rifle", Material.WOODEN_HOE),
    HEAVY_BLASTER(4, "&bHeavy Blaster", Material.WOODEN_HOE);

    private final int      ammo;
    private final String   name;
    private final Material material;

    Weapons(final int ammo, final String name, final Material material)
    {
        this.ammo = ammo;
        this.name = name;
        this.material = material;
    }

    public String getName()
    {
        return name;
    }

    public int getAmmo()
    {
        return ammo;
    }

    public Material getMaterial()
    {
        return material;
    }

    public Weapons[] getValues()
    {
        return values();
    }
}
