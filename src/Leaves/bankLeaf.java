package Leaves;

import Enum.GEAR;
import Manager.PlayerConfig;
import Manager.globalConfig;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.container.impl.equipment.Equipment;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.script.frameworks.treebranch.Leaf;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.items.Item;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static Manager.PlayerConfig.hasPots;
import static Manager.PlayerConfig.potionEnd;

public class bankLeaf extends Leaf {

private boolean isLogged = false;
private boolean isLogged2 = false;

private final globalConfig config;

    public bankLeaf(globalConfig config) {
        this.config = config;
    }


    public boolean isEquipped() {
        return Arrays.stream(GEAR.values()).allMatch(dharok -> Equipment.all().stream().anyMatch(item -> item != null && item.getName().startsWith(dharok.getItem())));
    }


    public boolean HasWithdrawn(){
        if(isEquipped()){
            return true;
        }

        return Arrays.stream(GEAR.values()).allMatch(dharok -> Inventory.all().stream().anyMatch(item -> item != null && item.getName() != null && item.getName().startsWith(dharok.getItem()))) && Inventory.contains("Locator Orb") && hasPots();
    }


    @Override
    public boolean isValid() {
       /* if(isEquipped()){
            return false;
        }*/
        if(PlayerConfig.inNMZ()){
            return false;
        }
        return !isEquipped() || !Inventory.contains("Locator Orb") || !hasPots();
    }

    @Override
    public int onLoop() {
    config.setStatus("Banking...");

        Logger.log("Items in inv: " +Inventory.all());

        if(!BankLocation.getNearest().getArea(5).contains(Players.getLocal().getTile())){
            Walking.shouldWalk(5);
            Walking.walk(BankLocation.getNearest());
            Sleep.sleepUntil(() -> BankLocation.getNearest().getArea(5).contains(Players.getLocal().getTile()), 5000);
        }
        if(BankLocation.getNearest().getArea(5).contains(Players.getLocal().getTile())){
            Bank.open();
            Sleep.sleepUntil(Bank::isOpen, 5000);
        }
        if(Bank.isOpen()) {
            if (!HasWithdrawn()) {
                Logger.log("Finding gear...");
                for (GEAR dharok : GEAR.values()) {
                    Logger.log(dharok.getItem());

                    Item foundItem = Bank.all().stream()
                            .filter(item -> item.getName().startsWith(dharok.getItem()))
                            .findFirst()
                            .orElse(null);

                    if (foundItem != null) {
                        Bank.withdraw(foundItem.getName(), 1);
                        Sleep.sleepUntil(() -> Inventory.contains(foundItem.getName()), 5000);

                    }
                    if (foundItem == null) {
                        Logger.log("Nothing found in bank!");
                        break;
                    }
                }
            }
            if (!Inventory.contains("Locator Orb")) {
                if (Bank.contains("Locator Orb")) {
                    Bank.withdraw("Locator Orb", 1);
                    Sleep.sleepUntil(() -> Inventory.contains("Locator Orb"), 5000);
                }
            }

            if (!hasPots()) {
                Logger.log("nothing found wait. potionswise i mean.");
                for (String potion : potionEnd) {
                   if (Bank.contains("Super strength"+potion)) {
                        int qnt = Bank.count("Super strength" + potion);
                        if (qnt > 4) {
                            Bank.withdraw("Super strength" + potion, 4);
                            Logger.log("Getting potions! Super strength" +potion);
                            Sleep.sleepUntil(PlayerConfig::hasPots, 5000);
                        } else {
                            Bank.withdraw("Super strength" + potion, qnt);
                            Logger.log("Getting potions! Super strength" +potion);
                            Sleep.sleepUntil(PlayerConfig::hasPots, 5000);
                        }
                    }else{
                        Logger.log("No potions found! Super strength" +potion);
                    }
                }
            }
        }

        if(Bank.isOpen()){
            Bank.close();
            Sleep.sleepUntil(() -> !Bank.isOpen(), 5000);
        }

        if (!Bank.isOpen() && HasWithdrawn()) {
            for (GEAR dharok : GEAR.values()) {


                Item wearItem = Inventory.all().stream()
                        .filter(item -> item != null && item.getName() != null && item.getName().startsWith(dharok.getItem()))
                        .findFirst()
                        .orElse(null);

                if (wearItem != null) {
                    String action = wearItem.getName().toLowerCase().contains("axe") ? "Wield" : "Wear";
                    Inventory.interact(wearItem.getName(), action);
                    Sleep.sleepUntil(() -> Equipment.contains(wearItem.getName()), 5000);
                } else if (wearItem == null) {
                    Logger.log("Not in inventory!");
                    break;
                }
            }

            if(Inventory.getItemInSlot(0) == null)
            {
                Inventory.drag("Locator Orb", 0);
                Sleep.sleep(100, 500);
            }


            if (hasPots())
            {

                //gets the potion inventory slots of my potions and puts them into a list
                List<Integer> potionSlots = Inventory.all().stream().filter(item -> item != null && item.getName() != null && item.getName().startsWith("Super")).map(Item::getSlot).toList();

                if(potionSlots.isEmpty())
                {
                    Logger.log("No potions found!");
                }

                //iterator for potions
                int potionIndex = 0;

                //slots that we want them to go into
                int[] Slots = {1, 2, 3, 4, 5};

                //runs through the slots
                    for (int slot : Slots)
                    {

                        if(potionIndex >= potionSlots.size())
                        {
                            break;
                        }

                        int potionSlot = potionSlots.get(potionIndex);

                        Inventory.swap(potionSlot, slot);
                        Logger.log("Moving " + potionSlot + " to " + slot);

                        potionIndex++;
                        Sleep.sleep(300, 600);
                    }
                }
            }

        return 600;
    }
}
