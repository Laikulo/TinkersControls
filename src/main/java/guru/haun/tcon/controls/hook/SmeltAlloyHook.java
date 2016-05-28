package guru.haun.tcon.controls.hook;

import guru.haun.tcon.controls.alloying.AlloyInhibitData;
import slimeknights.tconstruct.smeltery.tileentity.TileSmeltery;

import java.util.List;

/**
 *
 */
public class SmeltAlloyHook {

    //This is called by the transformed TileSmeltery, and is used in leu of the global alloy list for that smeltery
    public static List getAlloys(TileSmeltery theSmeltery){
        return AlloyInhibitData.get(theSmeltery).getAlloys();
    }

    //This is called when a smeltery becomes inactive, and (annoyingly) once every second when the smeltery is inactibe
    // I should write a better payload for this
    public static void removeSmeltery(TileSmeltery theSmeltery) {
        AlloyInhibitData.removeSmeltery(theSmeltery);
    }
}

