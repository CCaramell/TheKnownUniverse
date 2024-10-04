package com.caramell.theknownuniverse;

import com.caramell.theknownuniverse.item.ModItems;
import net.minecraftforge.fml.common.Mod;

@Mod(TheKnownUniverse.MOD_ID)
public class TheKnownUniverse {
    public static final String MOD_ID = "theknownuniverse";

    public TheKnownUniverse() {
        ModItems.register();
    }
}

