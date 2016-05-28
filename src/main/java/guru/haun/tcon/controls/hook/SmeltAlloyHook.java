package guru.haun.tcon.controls.hook;

import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.smeltery.tileentity.TileSmeltery;

import java.util.ArrayList;
import java.util.List;

/**
 * This is called by the transformed TileSmeltery, and is used in leu of the global alloy list for that smeltery
 */
public class SmeltAlloyHook {
    public static List getAlloys(TileSmeltery theSmeltery){
        if(true) {
            return TinkerRegistry.getAlloys();
        }else{
            return new ArrayList();
        }
    }
}
