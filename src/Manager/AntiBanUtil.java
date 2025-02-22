package Manager;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.input.mouse.MouseSettings;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.utilities.Timer;

import java.util.Random;

public class AntiBanUtil {

    private static final Random random = new Random();

    public static void moveCamera(){
        if (random.nextInt(100) < 30){
            int yaw = random.nextInt(360);
            int pitch = random.nextInt(100);

            Logger.log("[ANTIBAN] Moving camera: yaw "+ yaw + " pitch " + pitch);
            MouseSettings.setSpeed(random.nextInt(3) + 5);
        }
    }

    private static void afk(){
        if(random.nextInt(100) < 90){
            Timer timer = new Timer(Calculations.random(100000, 10000000));
            Logger.log("On a break!" +timer);
            Sleep.sleepUntil(timer::finished, 5000);
        }
    }

    public static void antiBan(){
        int action = random.nextInt(2);
        switch (action) {
            case 0:
                moveCamera();
                break;
            case 1:
                afk();
                break;
        }
    }

}
