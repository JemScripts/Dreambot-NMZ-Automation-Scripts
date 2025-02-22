package Branches;

import Leaves.absorbLeaf;
import Leaves.bankLeaf;
import Leaves.goLeaf;
import Enum.GEAR;
import Manager.PlayerConfig;
import Manager.globalConfig;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.equipment.Equipment;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.script.frameworks.treebranch.Branch;
import org.dreambot.api.utilities.Logger;

public class setupBranch extends Branch{

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
            Logger.log("You're already wearing what you need 1");
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


    public static Area nmz = new Area(2586, 3125, 2623, 3086);

    public setupBranch(globalConfig config){
        this.config = config;
        addLeaves(
                new goLeaf(config),
                new bankLeaf(config),
                new absorbLeaf(config)
        );
    }

    @Override
    public boolean isValid() {
        if(!Inventory.contains("Locator Orb") || !FindGear()){
            return true;
        }
        if(!PlayerConfig.inNMZ() && !nmz.contains(Players.getLocal().getTile())){
            return true;
        }
        if(!PlayerConfig.inNMZ() && !hasAbsorp()){
            return true;
        }
        if(PlayerConfig.inNMZ()){
            return false;
        }
        return false;
    }

    @Override
    public int onLoop(){
        return super.onLoop();
    }
}
