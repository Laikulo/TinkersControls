package guru.haun.tcon.controls.hook;

import guru.haun.tcon.controls.alloying.AlloyInhibitData;
import slimeknights.tconstruct.smeltery.tileentity.TileSmeltery;

import java.util.List;

/**
 * This is called by the transformed TileSmeltery, and is used in leu of the global alloy list for that smeltery
 */
public class SmeltAlloyHook {
    public static List getAlloys(TileSmeltery theSmeltery){
        return AlloyInhibitData.get(theSmeltery).getAlloys();
    }
}
