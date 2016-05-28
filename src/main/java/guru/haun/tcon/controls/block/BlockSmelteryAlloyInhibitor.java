package guru.haun.tcon.controls.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by Aaron on 5/28/2016.
 */
public class BlockSmelteryAlloyInhibitor extends Block {

    @Override
    public TileEntity createTileEntity(World world, IBlockState iBlockState) {
        return super.createTileEntity(world, iBlockState);
    }

    public BlockSmelteryAlloyInhibitor(Material material, MapColor mapcolor) {
        super(material, mapcolor);
        this.setUnlocalizedName("alloyInhibitor");
        this.setRegistryName("alloyInhibitor");
    }
}
