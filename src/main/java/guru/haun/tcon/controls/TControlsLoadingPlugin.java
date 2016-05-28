package guru.haun.tcon.controls;

import guru.haun.tcon.controls.transform.SmelteryTransformer;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

/**
 * Created by Aaron on 5/28/2016.
 */
@IFMLLoadingPlugin.Name("Tinkers Controls Coremod")
@IFMLLoadingPlugin.MCVersion("1.9")
@IFMLLoadingPlugin.TransformerExclusions({
        "guru.haun.tcon.controls"
})
public class TControlsLoadingPlugin implements IFMLLoadingPlugin {
    @Override
    public String[] getASMTransformerClass() {
        return new String[]{
                SmelteryTransformer.class.getName()
        };
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {

    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
