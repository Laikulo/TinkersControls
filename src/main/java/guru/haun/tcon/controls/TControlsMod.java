package guru.haun.tcon.controls;

import guru.haun.tcon.controls.util.SmelteryUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import scala.actors.threadpool.Arrays;

/**
 * FML Mod class, contains initializers and such
 */
@Mod(modid = "tcontrols", useMetadata = true)
public class TControlsMod {

    @Mod.Instance("tcontrols")
    private static TControlsMod INSTANCE;

    public Block smelteryAlloyInhibitor;
    public ItemBlock smelteryAlloyInhibitorItem;

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent e) {

        //instantiate blocks
        smelteryAlloyInhibitor = new Block(Material.rock, MapColor.grayColor);
        smelteryAlloyInhibitorItem = new ItemBlock(smelteryAlloyInhibitor);
        smelteryAlloyInhibitor.setUnlocalizedName("alloyInhibitor");
        smelteryAlloyInhibitor.setRegistryName("alloyInhibitor");
        smelteryAlloyInhibitorItem.setRegistryName("alloyInhibitor");

        smelteryAlloyInhibitorItem.setCreativeTab(CreativeTabs.tabBlock);

        //register blocks
        GameRegistry.register(smelteryAlloyInhibitor);
        GameRegistry.register(smelteryAlloyInhibitorItem);

    }

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent e) {
        //Fudge blocks into Smeltery Valid list
        SmelteryUtil.addValidSmelteryBlock(
                Arrays.asList(new Block[]{
                        smelteryAlloyInhibitor
                })
        );
    }

    public static TControlsMod getInstance() {
        return INSTANCE;
    }

}
