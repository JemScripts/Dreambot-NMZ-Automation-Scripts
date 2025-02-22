package Leaves;

import Manager.PlayerConfig;
import Manager.globalConfig;
import org.dreambot.api.methods.dialogues.Dialogues;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.settings.PlayerSettings;
import org.dreambot.api.methods.widget.Widgets;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.widgets.WidgetChild;

public class bossesLeaf extends Leaf {

    private final globalConfig config;

    public boolean isReady;

    public bossesLeaf(globalConfig config) {
        this.config = config;
    }

    @Override
    public boolean isValid() {
        if (PlayerSettings.getBitValue(PlayerConfig.NMZ_DREAM_STATE) > 60 && !PlayerConfig.inNMZ()) {
            return true;
        }
        return false;
    }

    @Override
    public int onLoop() {
        config.setStatus("Selecting bosses...");

        Sleep.sleepUntil(() -> GameObjects.closest(26291) != null, 5000);
        GameObject potion = GameObjects.closest(26291);

        if (!PlayerConfig.coffer) {
            GameObject coffer = GameObjects.closest("Dominic's coffer");
            if (coffer != null) {
                coffer.interact("Unlock");
                Sleep.sleepUntil(Dialogues::inDialogue, 5000);
                if (Dialogues.inDialogue()) {
                    Dialogues.continueDialogue();
                    PlayerConfig.coffer = true;
                    Sleep.sleep(500, 1500);
                }
            }
        }

        if (potion != null && PlayerConfig.coffer) {
            if (potion.interact("Drink")) {
                Sleep.sleep(500, 1500);

                if (Dialogues.inDialogue()) {
                    PlayerConfig.coffer = false;
                } else {
                    Sleep.sleepUntil(() -> {
                        WidgetChild validator = Widgets.get(129, 2);
                        return validator != null && validator.isVisible();
                        }, 5000);

                    Logger.log("The screen is up gang!");
                    Sleep.sleep(1000, 2500);

                    WidgetChild Menu = Widgets.get(129, 13);
                    if (Menu != null && !PlayerConfig.bossesSet) {
                        boolean boss1 = selectBoss(Menu, 63);
                        boolean boss2 = selectBoss(Menu, 72);
                        boolean boss3 = selectBoss(Menu, 84);
                        boolean boss4 = selectBoss(Menu, 90);
                        boolean boss5 = selectBoss(Menu, 120);

                        if (boss1 && boss2 && boss3 && boss4 && boss5) {
                            PlayerConfig.bossesSet = true;
                            Logger.log("Bosses have been set!");
                            Sleep.sleep(500, 1500);
                        }
                    }

                    if (PlayerConfig.bossesSet) {
                        Sleep.sleepUntil(() -> {
                            WidgetChild accept = Widgets.get(129, 6);
                            WidgetChild acceptButton = accept != null ? accept.getChild(9) : null;
                            return acceptButton != null && acceptButton.isVisible();
                        }, 5000);

                        WidgetChild accept = Widgets.get(129, 6);
                        WidgetChild acceptButton = accept != null ? accept.getChild(9) : null;

                        if(acceptButton != null) {
                            acceptButton.interact();
                            Logger.log("Accepted and entering NMZ!");
                            Sleep.sleepUntil(PlayerConfig::inNMZ, 5000);
                        }else{
                            Logger.log("Couldn't find the accept button!");
                        }
                    }
                }
            }
        }
        return 600;
    }

    private boolean selectBoss(WidgetChild menu, int childId) {
        if (menu.getChild(childId) != null) {
            if (menu.getChild(childId).getTextColor() != 16750623) {
                menu.getChild(childId).interact();
                Sleep.sleep(500, 1500);
                return true;
            } else {
                return true;
            }
        }
        return false;
    }
}
