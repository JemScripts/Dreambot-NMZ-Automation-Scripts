import Branches.nmzBranch;
import Branches.setupBranch;
import Manager.PlayerConfig;
import Manager.globalConfig;
import org.dreambot.api.Client;
import org.dreambot.api.methods.combat.Combat;
import org.dreambot.api.methods.combat.CombatStyle;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.frameworks.treebranch.TreeScript;
import org.dreambot.api.script.frameworks.treebranch.Root;
import org.dreambot.api.script.listener.ChatListener;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;


@ScriptManifest(category = Category.COMBAT, name = "NMZDharok", author = "Cem", version = 1.0)

public class Main extends TreeScript implements ChatListener {

    private final globalConfig config = new globalConfig();

    private static int currentdefXP;
    private static int startdefXP;
    private static int gaineddefXP;
    private static int startdefLvl;
    private static int currentdefLvl;
    private static int gaineddefLvl;

    private static int currentattXP;
    private static int startattXP;
    private static int gainedattXP;
    private static int startattLvl;
    private static int currentattLvl;
    private static int gainedattLvl;

    private static int currentstrXP;
    private static int startstrXP;
    private static int gainedstrXP;
    private static int startstrLvl;
    private static int currentstrLvl;
    private static int gainedstrLvl;

    private long timeBegan;
    private long timeRan;

    //private final Image logo = getImage("https://i.ibb.co/VpPMNtZY/Asset-2-0-5x.png");
    private final Image bg = getImage("https://i.ibb.co/m5z51gWv/Ottoman.png");

    @Override
    public void onStart(){
        timeBegan = System.currentTimeMillis();

        Root root = getRoot();
        root.addBranches(
                new setupBranch(config),
                new nmzBranch(config));
    }

    //START: Code generated using Enfilade's Easel
    /*private final Color color1 = new Color(129, 18, 19);
    private final Color color2 = new Color(129, 18, 19);
    private final Color color3 = new Color(174, 24, 25);
    private final Color color4 = new Color(174, 24, 25);
    private final Color color5 = new Color(253, 240, 213);
*/
    private final BasicStroke stroke1 = new BasicStroke(3);

    private final Font font1 = new Font("Arial", 1, 30);
    private final Font font2 = new Font("Arial", 1, 20);
    private final Font font3 = new Font("Arial", 1, 13);
    private final Font font4 = new Font("Arial", 1, 16);
    private final Font font5 = new Font("Arial", 1, 12);

    /*@Override
    public void onPaint(Graphics g1) {
        timeRan = System.currentTimeMillis() - timeBegan;

        currentdefXP = Skills.getExperience(Skill.DEFENCE);
        currentdefLvl = Skills.getRealLevel(Skill.DEFENCE);
        gaineddefXP = currentdefXP - startdefXP;
        gaineddefLvl = currentdefLvl - startdefLvl;

        currentattXP = Skills.getExperience(Skill.ATTACK);
        currentattLvl = Skills.getRealLevel(Skill.ATTACK);
        gainedattXP = currentattXP - startattXP;
        gainedattLvl = currentattLvl - startattLvl;

        currentstrXP = Skills.getExperience(Skill.STRENGTH);
        currentstrLvl = Skills.getRealLevel(Skill.STRENGTH);
        gainedstrXP = currentstrXP - startstrXP;
        gainedstrLvl = currentstrLvl - startstrLvl;


        Graphics2D g = (Graphics2D) g1;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if(initialised) {
            g.setFont(font1);
            g.setColor(color1);
            g.drawString("Ottoman", 9, 335);
            g.setColor(color2);
            g.fillRect(3, 339, 513, 139);
            g.setColor(color3);
            g.setStroke(stroke1);
            g.drawRect(3, 338, 513, 140);
            g.setColor(color3);
            g.drawString("NMZ v1.0", 133, 335);
            g.setFont(font2);
            g.setColor(color4);
            g.drawString("Current Status", 190, 371);
            g.drawString("Time Ran", 50, 371);
            g.setFont(font3);
            g.setColor(color5);
            g.drawString("STATUS_VALUE", 210, 390);
            g.setFont(font2);
            g.setColor(color4);
            g.drawString("Combat Style", 195, 413);
            g.setFont(font3);
            g.setColor(color5);
            g.drawString("" + Combat.getCombatStyle(), 211, 433);
            g.setFont(font4);
            g.setColor(color4);
            g.drawString("XP Gained", 385, 371);
            g.drawString("Levels Gained", 371, 413);
            g.setFont(font5);
            g.setColor(color5);
            if (Combat.getCombatStyle() == CombatStyle.DEFENCE) {
                g.drawString("" + gaineddefXP, 420, 390);
                g.drawString("" + gaineddefLvl, 420, 437);
            }
            if (Combat.getCombatStyle() == CombatStyle.ATTACK) {
                g.drawString("" + gainedattXP, 420, 390);
                g.drawString("" + gainedattLvl, 420, 437);
            }
            if (Combat.getCombatStyle() == CombatStyle.STRENGTH) {
                g.drawString("" + gainedstrXP, 420, 390);
                g.drawString("" + gainedstrLvl, 420, 437);
            }
            g.drawString(ft(timeRan), 70, 390);
            g.drawImage(logo, 50, 400, null);
        }
    }*/

    @Override
    public void onPaint(Graphics g1) {
        timeRan = System.currentTimeMillis() - timeBegan;

        currentdefXP = Skills.getExperience(Skill.DEFENCE);
        currentdefLvl = Skills.getRealLevel(Skill.DEFENCE);
        gaineddefXP = currentdefXP - startdefXP;
        gaineddefLvl = currentdefLvl - startdefLvl;

        currentattXP = Skills.getExperience(Skill.ATTACK);
        currentattLvl = Skills.getRealLevel(Skill.ATTACK);
        gainedattXP = currentattXP - startattXP;
        gainedattLvl = currentattLvl - startattLvl;

        currentstrXP = Skills.getExperience(Skill.STRENGTH);
        currentstrLvl = Skills.getRealLevel(Skill.STRENGTH);
        gainedstrXP = currentstrXP - startstrXP;
        gainedstrLvl = currentstrLvl - startstrLvl;


        Graphics2D g = (Graphics2D) g1;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if(initialised) {
           // WidgetChild chatBox = chatWidget.getChild(0);

            g.drawImage(bg, 1, 340, null);
            g.setColor(Color.WHITE);
            g.setFont(font4);
            g.drawString("Current Status:", 30, 400);
            g.drawString("Time Ran:", 30, 380);
            g.setFont(font3);
            if(config.getStatus() != null) {
                g.drawString(config.getStatus(), 150, 400);
            }else{
                g.drawString("Initialising...", 150, 400);
            }
            g.setFont(font4);
            g.drawString("Combat Style:", 30, 420);
            g.setFont(font3);
            g.drawString("" + Combat.getCombatStyle(), 140, 420);
            g.setFont(font4);
            g.drawString("XP Gained:", 30, 440);
            g.drawString("Levels Gained:", 30, 460);
            g.setFont(font3);
            if (Combat.getCombatStyle() == CombatStyle.DEFENCE) {
                g.drawString("" + gaineddefXP, 120, 440);
                g.drawString("" + gaineddefLvl, 150, 460);
            }
            if (Combat.getCombatStyle() == CombatStyle.ATTACK) {
                g.drawString("" + gainedattXP, 120, 440);
                g.drawString("" + gainedattLvl, 150, 460);
            }
            if (Combat.getCombatStyle() == CombatStyle.STRENGTH) {
                g.drawString("" + gainedstrXP, 120, 440);
                g.drawString("" + gainedstrLvl, 150, 460);
            }
            g.drawString(ft(timeRan), 120, 380);
        }
    }

    private Image getImage(String url){
        try{
            return ImageIO.read(new URL(url));
        }
        catch (IOException e){}
        return null;
    }

    private String ft(long duration)
    {
        String res = "";
        long days = TimeUnit.MILLISECONDS.toDays(duration);
        long hours = TimeUnit.MILLISECONDS.toHours(duration) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(duration));
        long minutes = TimeUnit.MILLISECONDS.toMinutes(duration) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(duration));
        long seconds = TimeUnit.MILLISECONDS.toSeconds(duration) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration));

        if(days == 0) {
            res = (hours + ":" + minutes + ":" + seconds);
        }else{
            res = (days + ":" + hours + ":" + minutes + ":" + seconds);
        }
        return res;

    }

    private boolean initialised = false;

    @Override
    public int onLoop(){
        if(!initialised) {
            if (Client.isLoggedIn()) {
                startdefXP = Skills.getExperience(Skill.DEFENCE);
                startdefLvl = Skills.getRealLevel(Skill.DEFENCE);
                startattXP = Skills.getExperience(Skill.ATTACK);
                startattLvl = Skills.getRealLevel(Skill.ATTACK);
                startstrXP = Skills.getExperience(Skill.STRENGTH);
                startstrLvl = Skills.getRealLevel(Skill.STRENGTH);
                initialised = true;
            }
            return 500;
        }
        return getRoot().onLoop();
    }
}
