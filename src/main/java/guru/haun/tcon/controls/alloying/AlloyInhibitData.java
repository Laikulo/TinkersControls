package guru.haun.tcon.controls.alloying;

import com.google.common.collect.Maps;
import guru.haun.tcon.controls.TControlsMod;
import net.minecraft.util.math.BlockPos;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.smeltery.multiblock.MultiblockSmeltery;
import slimeknights.tconstruct.smeltery.tileentity.TileSmeltery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Aaron on 5/28/2016.
 */
public class AlloyInhibitData {

    private static Map<TileSmeltery, AlloyInhibitData> dataMap = new HashMap<TileSmeltery, AlloyInhibitData>();

    public static AlloyInhibitData get(TileSmeltery smeltery){
        if(dataMap.containsKey(smeltery))
            return dataMap.get(smeltery);
        else{
            AlloyInhibitData newData = new AlloyInhibitData();
            newData.owningTile = smeltery;
            newData.scan();
            dataMap.put(smeltery,newData);
            return newData;
        }
    }


    private TileSmeltery owningTile;

    private boolean hasScanned = false;
    private boolean hasInhibitor = false;

    public List getAlloys(){
        if(hasInhibitor){
            return new ArrayList();
        }else{
            return TinkerRegistry.getAlloys();
        }
    }

    private void scan() {
        if(hasScanned)
            return;
        hasScanned = true;
        hasInhibitor = false;
        for(BlockPos scanBlock : owningTile.info.blocks){
            if(owningTile.getWorld().getBlockState(scanBlock).getBlock().equals(
                    TControlsMod.getInstance().smelteryAlloyInhibitor
            )){
                hasInhibitor = true;
            }
        }
    }
}
