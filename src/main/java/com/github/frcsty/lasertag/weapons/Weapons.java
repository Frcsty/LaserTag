package com.github.frcsty.lasertag.weapons;

import org.bukkit.Material;

public enum Weapons
{

    DH_17(8, "&bBlaster", Material.WOODEN_HOE, 1),
    E_11(12, "&bBlaster Rifle", Material.WOODEN_HOE, 2),
    A_280(14, "&bBlaster Rifle", Material.WOODEN_HOE, 3),
    DL_44(4, "&bHeavy Blaster", Material.WOODEN_HOE, 4),
    T_21(12, "&bSporting Blaster", Material.WOODEN_HOE, 5),

    EMPEROR_4(2, "&cBeam Tube", Material.WOODEN_HOE, 6),
    ION_BLASTER(2, "&cIonization Blaster", Material.WOODEN_HOE, 7),
    BOW_CASTER(1, "&cBow Caster", Material.WOODEN_HOE, 8),

    DX_2(6, "&6Disruptor Pistol", Material.WOODEN_HOE, 9),
    DXR_6(18, "&6Disruptor Rifle", Material.WOODEN_HOE, 10),
    SD_77(10, "&6Sonic Pistol", Material.WOODEN_HOE, 11),
    SG_82(3, "&6Sonic Rifle", Material.WOODEN_HOE, 12),

    C_22(10, "&5Flame Carbine", Material.WOODEN_HOE, 13),
    CR_24(10, "&5Flame Rifle", Material.WOODEN_HOE, 14),
    PLX_2M(1, "&5Missile Launcher", Material.WOODEN_HOE, 15);

    private final int      ammo;
    private final String   name;
    private final Material material;
    private final int customData;

    Weapons(final int ammo, final String name, final Material material, final int customData)
    {
        this.ammo = ammo;
        this.name = name;
        this.material = material;
        this.customData = customData;
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

    public int getCustomData() { return customData; }
}
