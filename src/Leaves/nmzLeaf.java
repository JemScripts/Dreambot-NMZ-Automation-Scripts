package Leaves;

import Branches.nmzBranch;
import Manager.PlayerConfig;
import Manager.globalConfig;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.settings.PlayerSettings;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.methods.widget.Widgets;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.widgets.WidgetChild;

import java.util.Arrays;

import static Manager.PlayerConfig.hasPots;
import static Manager.PlayerConfig.potionEnd;
import static org.dreambot.api.methods.Animations.DEATH;
import static org.dreambot.api.methods.Calculations.random;

public class nmzLeaf extends Leaf {

    private final globalConfig config;

    private int randHP = Calculations.random(3,7);
    private int randAbsorp = Calculations.random(120, 730);
    private int getHP = Skills.getBoostedLevel(Skill.HITPOINTS);



    private int randomHP;
    private int randomAbsorp;

    private boolean randomHPSet = false;
    private boolean walkToSpot = false;
    private boolean empty = false;
    private boolean drank = false;

    private Timer timer = new Timer(200000);
    private Timer drink = new Timer(Calculations.random(300000, 500000));
    private Timer walk = new Timer(Calculations.random(800000, 1000000));

    public nmzLeaf(globalConfig config) {
        this.config = config;
    }

    @Override
    public boolean isValid() {
        return PlayerConfig.inNMZ();
    }


    @Override
    public int onLoop() {
        config.setStatus("Training in NMZ");


        GameObject exitPot = GameObjects.closest(26276);

        if(!walkToSpot) {
            if(exitPot != null) {
                Logger.log("Attempting to walk to spot...");
                Tile newTile = exitPot.getTile();
                Walking.walk(newTile.translate(-5, 14));
                walkToSpot = true;
                Logger.log("Spot: walked!");
            }else{
                Logger.log("There was an issue for some reason... Here's the issue" +exitPot.getTile().translate(2500,2500));
            }
        }

        if(timer.finished()){
            randomHPSet = false;
            Logger.log("Resetting timer...");
            timer.reset();
        }

        if(!randomHPSet){
            randomHP = getRandomHP();
            randomAbsorp = getRandomAbsorp();
            randomHPSet = true;
            Logger.log("Set random values for HP " +randomHP+ " and absorptions " +randomAbsorp);
        }

        int currentHP = Skills.getBoostedLevel(Skill.HITPOINTS);

        if(currentHP >= randomHP) {
            while (currentHP >= randomHP) {
                resetHP();

                currentHP = Skills.getBoostedLevel(Skill.HITPOINTS);

                Logger.log(currentHP + " " + randomHP + " ");
            }
        }

        if(!empty && Inventory.onlyContains("Locator orb"))
        {
            empty = true;
            Logger.log("Ran out of absorptions!");
        }

        if(Players.getLocal().getAnimation() == DEATH){
            Logger.log("Player died. Waiting to respawn...");
            Sleep.sleepUntil(() -> !Players.getLocal().isAnimating(), 7000);
            Sleep.sleepUntil(() -> !PlayerConfig.inNMZ(), 5000);
            Logger.log("Respawned but adding delay...");
            Sleep.sleep(7000);
            Logger.log("Respawned proper this time");
            walkToSpot = false;
            empty = false;
        }

        if(!empty) {
            if (PlayerSettings.getBitValue(PlayerConfig.ABSORPTION_BIT_VALUE) < randomAbsorp) {

                Logger.log("Absorbing...!");

                String[] potionEnd = {"(1)", "(2)", "(3)", "(4)"};

                for (String potion : potionEnd) {
                    if (Inventory.contains("Absorption " + potion)) {
                        Inventory.interact("Absorption " + potion, "Drink");
                        break;
                    }
                }
            }
        }

        if(!drank) {
            if (hasPots()) {
                for (String potion : potionEnd) {
                    if (Inventory.contains("Super strength" + potion)) {
                        Inventory.interact("Super strength" + potion, "Drink");
                        Logger.log("Drinking my first potion!");
                        drank = true;
                    }
                }
            }
        }

        if(drink.finished()) {
            if (hasPots()) {
                for (String potion : potionEnd) {
                    if (Inventory.contains("Super strength" + potion)) {
                        Inventory.interact("Super strength" +potion);
                        Logger.log("Resetting potion timer...");
                        drink.reset();
                    }
                }
            }
        }

        if(walk.finished()){
            Logger.log("Moving to new spot to avoid being stuck");
            Tile newSpot = Players.getLocal().getTile();
            Walking.walk(newSpot.translate(5, -5).getRandomized());
            walk.reset();
        }
        return 600;
    }

    private boolean hasAbsorps() {
        return Inventory.contains("Absorption (1)")
                || Inventory.contains("Absorption (2)")
                || Inventory.contains("Absorption (3)")
                || Inventory.contains("Absorption (4)");
    }

    private void handleNoAbsorptions() {
        Logger.log("No absorptions left! Checking if we should leave...");
        Sleep.sleepUntil(() -> !PlayerConfig.inNMZ(), 5000);
    }



    public int getRandomHP(){
        return Calculations.random(3, 7);
    }

    public int getRandomAbsorp() {
        return Calculations.random(120, 750);
    }


    public void resetHP() {
        if (Skills.getBoostedLevel(Skill.HITPOINTS) >= 2) {
            Inventory.interact("Locator orb", "Feel");
            Logger.log("Doing the most!");
            Sleep.sleep(200, 400);
        }
    }
}
