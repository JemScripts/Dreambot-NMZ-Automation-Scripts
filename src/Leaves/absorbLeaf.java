package Leaves;

import Manager.PlayerConfig;
import Manager.globalConfig;
import org.dreambot.api.input.Keyboard;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.dialogues.Dialogues;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.settings.PlayerSettings;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.methods.widget.Widgets;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.script.listener.ChatListener;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.widgets.WidgetChild;

import static org.dreambot.api.utilities.Sleep.sleep;

public class absorbLeaf extends Leaf implements ChatListener {

    private final globalConfig config;

    public absorbLeaf(globalConfig config) {
        this.config = config;
    }

    public boolean hasAbsorp()
    {
        return Inventory.all().stream().anyMatch(item -> item != null && item.getName() != null && item.getName().startsWith("Absorption"));

    }

    public boolean isFull()
    {
        return PlayerSettings.getBitValue(PlayerConfig.ABSORPTION_TANK_BIT_VALUE) > 25;
    }

    //newnmz
    Area nmz = new Area(2598, 3120, 2611, 3111);

    //28260
    WidgetChild rewScreen = Widgets.get(206, 1);

    @Override
    public boolean isValid()
    {
        return !hasAbsorp() && !PlayerConfig.inNMZ();
    }

    @Override
    public int onLoop() {
       config.setStatus("Getting absorption potions");

       GameObject absorbBarrel = GameObjects.closest("Absorption potion");
       GameObject rewChest = GameObjects.closest("Rewards chest");

       Logger.log("Getting absorption potions....");

        if (isFull() && !hasAbsorp()) {

        Logger.log("Getting absorbs from the tank!");

            if (!nmz.contains(Players.getLocal().getTile()))
            {
                Logger.log("Walking to the NMZ zone for absorptions...");
                Walking.shouldWalk(5);
                Walking.walk(nmz);
                Sleep.sleepUntil(() -> nmz.contains(Players.getLocal().getTile()), 5000);
            }

            if(absorbBarrel != null)
            {

                Logger.log("Absorption barrel exists! Huzzah!");

                if (!absorbBarrel.getSurroundingArea(5).contains(Players.getLocal().getTile()))
                {
                    Walking.shouldWalk(5);
                    Walking.walk(absorbBarrel);
                    Sleep.sleepUntil(() -> absorbBarrel.getSurroundingArea(5).contains(Players.getLocal().getTile()), 5000);
                }

                else if (absorbBarrel.getSurroundingArea(5).contains(Players.getLocal().getTile())) {

                    if (absorbBarrel.interact("Take")) {
                        Logger.log("Getting absorption potions!");
                        Sleep.sleepUntil(Dialogues::inDialogue, 2000);

                        if (Dialogues.inDialogue()) {
                            Logger.log("We made it baby");
                            Keyboard.type("10000", true);
                            Sleep.sleepUntil(Inventory::isEmpty, 2000);
                        }

                    }

                }
            }

            }

        if (!isFull() && !hasAbsorp()) {

            Logger.log("Buying absorptions...");

            if (!nmz.contains(Players.getLocal().getTile())) {
                Walking.shouldWalk(5);
                Walking.walk(nmz);
                Sleep.sleepUntil(() -> nmz.contains(Players.getLocal().getTile()), 5000);
            }

            else
            {
                    if (rewChest != null && rewChest.getSurroundingArea(10).contains(Players.getLocal().getTile()))
                    {
                        Walking.shouldWalk(5);
                        Walking.walk(rewChest);
                        Sleep.sleepUntil(() -> rewChest.getSurroundingArea(10).contains(Players.getLocal().getTile()), 5000);
                    }

                    if (rewChest != null && rewChest.interact("Search"))
                    {
                        Sleep.sleepUntil(() -> {
                            WidgetChild rewScreen = Widgets.get(206, 1);
                            return rewScreen != null && rewScreen.isVisible();
                        }, 5000);

                        Logger.log("I see the reward shop screen!");
                        WidgetChild benefits = Widgets.get(206, 2);
                        sleep(500);

                        if (benefits.getChild(4) != null && benefits.getChild(4).interact())
                        {
                            Sleep.sleepUntil(() -> benefits.getChild(4).isVisible(), 5000);
                            WidgetChild buyAbsorb = Widgets.get(206, 6);

                            if (buyAbsorb.getChild(9) != null && buyAbsorb.getChild(9).interact("Buy-X"))
                            {
                                Sleep.sleepUntil(Dialogues::inDialogue, 15000);

                                if (Dialogues.inDialogue())
                                {
                                    Keyboard.type(Calculations.random(75, 150), true);
                                    Sleep.sleep(1000, 2000);
                                    Logger.log("Bought what is needed from the Onion guy");
                                }
                            }
                        } else {
                            Logger.log("Fail");
                        }
                    }
                }
            }
        return 600;
    }
}
