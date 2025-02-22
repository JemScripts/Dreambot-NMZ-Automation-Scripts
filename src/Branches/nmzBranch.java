package Branches;

import Leaves.bossesLeaf;
import Leaves.dreamLeaf;
import Enum.GEAR;
import Leaves.nmzLeaf;
import Manager.PlayerConfig;
import Manager.globalConfig;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.equipment.Equipment;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.script.frameworks.treebranch.Branch;
import org.dreambot.api.utilities.Logger;

public class nmzBranch extends Branch{


    public static Area nmz = new Area(2586, 3125, 2623, 3086);

    private final globalConfig config;

    private boolean isLogged = false;

    public boolean FindGear(){
        boolean hasItOn = true;

        for (GEAR dharok : GEAR.values()) {
            if(dharok != null) {
                boolean Gear = Equipment.all().stream().anyMatch(item -> item != null && item.getName() != null & item.getName().startsWith(dharok.getItem()));

                if (!Gear) {
                    hasItOn = false;
                }
            }
        }
        if(hasItOn && !isLogged){
            Logger.log("You're already wearing what you need 2");
            isLogged = true;
        }
        return hasItOn;
    }

    public boolean hasAbsorp(){
        boolean hasAbsorbs = true;

        boolean Potion = Inventory.all().stream().anyMatch(item -> item != null && item.getName() != null && item.getName().startsWith("Absorption"));

        if(!Potion){
            hasAbsorbs = false;
        }
        return hasAbsorbs;
    }

    public nmzBranch(globalConfig config){
        this.config = config;
        addLeaves(
                new dreamLeaf(config),
                new bossesLeaf(config),
                new nmzLeaf(config)
        );
    }

    @Override
    public boolean isValid() {
        if(FindGear() && hasAbsorp() && nmz.contains(Players.getLocal().getTile()) && Inventory.contains("Locator orb")){
            return true;
        }
        if(PlayerConfig.inNMZ()){
            return true;
        }
        return false;
    }

    @Override
    public int onLoop(){
        return super.onLoop();
    }
}
