package guru.haun.tcon.controls.util;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;

import java.util.List;

/**
 * Created by Aaron on 5/28/2016.
 */
public class SmelteryUtil {

    //This is a horrible hack, but I don't want to ASM into TinkerSmeltery
    public static void addValidSmelteryBlock(List<Block> newBlocks){
        ImmutableSet.Builder<Block> builder = ImmutableSet.builder();
        builder.addAll(TinkerSmeltery.validSmelteryBlocks);
        builder.addAll(newBlocks);
        TinkerSmeltery.validSmelteryBlocks = builder.build();
    }
}
