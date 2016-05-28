package guru.haun.tcon.controls.transform;

import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fml.common.FMLLog;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;
import scala.tools.asm.tree.InvokeDynamicInsnNode;

/**
 * This transforms the smeltery controller logic to allow the alloy inhibitor/selector to work
 */
public class SmelteryTransformer implements IClassTransformer {
    @Override
    public byte[] transform(String className, String classNewName, byte[] bytecode) {
        if (className.equals("slimeknights.tconstruct.smeltery.tileentity.TileSmeltery")) {

            ClassNode cNode = new ClassNode();
            ClassReader cReader = new ClassReader(bytecode);

            cReader.accept(cNode, 0);

            //This is horrible, and should be cleaned up
            for(MethodNode method : cNode.methods){
                if(method.name.equals("alloyAlloys")){
                    try {
                        FMLLog.info("[TControls] Preparing to patch smeltery");
                        // We want to replace a call to TinkerRegistry.getAlloys() with a call to SmeltAlloyHook.getAlloys
                        AbstractInsnNode currentInsn = method.instructions.getFirst();
                        while (currentInsn.getOpcode() != Opcodes.INVOKESTATIC)
                            currentInsn = currentInsn.getNext();

                            InsnList payload = new InsnList();
                            payload.add(new VarInsnNode(Opcodes.ALOAD, 0));
                            payload.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
                                    "guru/haun/tcon/controls/hook/SmeltAlloyHook",
                                    "getAlloys",
                                    "(Lslimeknights/tconstruct/smeltery/tileentity/TileSmeltery;)Ljava/util/List;",
                                    false
                            ));

                            method.instructions.insert(currentInsn, payload);
                            method.instructions.remove(currentInsn);
                            FMLLog.info("[TControls] Smeltery patch done");
                            break;
                    }
                    catch(RuntimeException e){
                        FMLLog.severe("[TControls] Unable to patch smeltery class");
                        e.printStackTrace();
                        return bytecode;
                    }
                }
            }

            ClassWriter cWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
            cNode.accept(cWriter);
            return cWriter.toByteArray();

        } else {
            return bytecode;
        }
    }
}
