package Leaves;

import Branches.setupBranch;
import Manager.PlayerConfig;
import Manager.globalConfig;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.minigame.Minigame;
import org.dreambot.api.methods.minigame.MinigameTeleports;
import org.dreambot.api.methods.widget.Widgets;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.widgets.WidgetChild;

import static org.dreambot.api.utilities.Sleep.sleep;

public class goLeaf extends Leaf {
    WidgetChild groupingTab = Widgets.get(707,5);
    WidgetChild selectTeleport = Widgets.get(76, 11);
    WidgetChild teleportButton = Widgets.get(76, 30);
    WidgetChild NMZTeleport;

    private final globalConfig config;

    public boolean isThere;

    public goLeaf(globalConfig config) {
        this.config = config;
    }

    @Override
    public boolean isValid() {
        if(!setupBranch.nmz.contains(Players.getLocal().getTile()) && !PlayerConfig.inNMZ()){
            isThere = false;
            return true;
        }
        return false;
    }

    @Override
    public int onLoop() {
        config.setStatus("Going to Nightmare Zone");

            Logger.log("Going to Nightmare Zone");
            if(MinigameTeleports.teleport(Minigame.NIGHTMARE_ZONE)) {
                Logger.log("Going to NMZ!");
                Sleep.sleepUntil(() -> setupBranch.nmz.contains(Players.getLocal().getTile()), 15000);
                isThere = true;
            }else{
                Logger.log("Failed for some reason");
            }

        return 600;
    }
}
