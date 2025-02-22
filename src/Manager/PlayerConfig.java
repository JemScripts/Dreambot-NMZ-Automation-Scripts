package Manager;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.wrappers.map.Region;

import java.util.Arrays;

public class PlayerConfig {
    public static boolean dream = false;
    public static boolean coffer = true;
    public static boolean bossesSet = false;
    public static final int NMZ_DREAM_STATE = 3946;
    public static final int ABSORPTION_BIT_VALUE = 3956;
    public static final int ABSORPTION_TANK_BIT_VALUE = 3954;
    public static boolean suicide = false;
    public static boolean absorb = false;
    public static String[] potionEnd = {"(1)", "(2)", "(3)", "(4)"};
    public static boolean hasPots() {
        return Inventory.all().stream().anyMatch(item -> item != null && item.getName() != null && item.getName().startsWith("Super"));
    }
    public static boolean inNMZ()
    {
        int[] NMZ_MAP_REGION = {9033};

        // NMZ and the KBD lair uses the same region ID but NMZ uses planes 1-3 and KBD uses plane 0
        return Players.getLocal().getZ() > 0 && Arrays.equals(Region.getMapRegions(), NMZ_MAP_REGION);
    }
    public static boolean highHP = false;
    public static boolean pause = false;
    public static boolean drinkAbsorption = false;
}
