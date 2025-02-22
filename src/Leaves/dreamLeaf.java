package Leaves;

import Manager.AntiBanUtil;
import Branches.nmzBranch;
import Manager.PlayerConfig;
import Manager.globalConfig;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.dialogues.Dialogues;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.settings.PlayerSettings;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.interactive.NPC;

import static Manager.AntiBanUtil.antiBan;

public class dreamLeaf extends Leaf {
    private final globalConfig config;

    public dreamLeaf(globalConfig config) {
        this.config = config;
    }

    private boolean hasAbsorbs(){
        return Inventory.all().stream().anyMatch(item -> item != null && item.getName() != null && item.getName().startsWith("Absorption"));
    }

    @Override
    public boolean isValid() {
        if(nmzBranch.nmz.contains(Players.getLocal().getTile()) && PlayerSettings.getBitValue(PlayerConfig.NMZ_DREAM_STATE) < 60 && !PlayerConfig.inNMZ() && hasAbsorbs()){
            return true;
        }
        return false;
    }

    @Override
    public int onLoop() {

        //antiBan();

        config.setStatus("Setting up dream...");

        NPC dom = NPCs.closest("Dominic Onion");

            if (dom != null && dom.interact("Dream")) {
                Sleep.sleepUntil(Dialogues::inDialogue, 5000);
                if (Dialogues.inDialogue()) {
                    Dialogues.typeOption(3);
                    Sleep.sleep(3000,5000);
                    if (Dialogues.typeOption(3)) {
                        Dialogues.continueDialogue();
                        Sleep.sleep(3000,5000);
                        if (Dialogues.continueDialogue()) {
                            Sleep.sleep(1000,2000);
                            Dialogues.typeOption(1);
                            Sleep.sleepUntil(() -> Dialogues.typeOption(1), 5000);
                            if (Dialogues.typeOption(1)) {
                                Dialogues.continueDialogue();
                                Sleep.sleepUntil(Dialogues::continueDialogue, 5000);
                                Sleep.sleep(1000,2000);
                            }
                        }
                    }
                }
            }
        return 600;
    }
}
