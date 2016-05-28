package guru.haun.tcon.controls.transform;

import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fml.common.FMLLog;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

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
                FMLLog.info("[TControls] Preparing to patch smeltery");
                if(method.name.equals("alloyAlloys")){
                    // We want to replace a call to TinkerRegistry.getAlloys() with a call to SmeltAlloyHook.getAlloys
                    // It's the first INVOKESTATIC in the method
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
                    FMLLog.info("[TControls] Smeltery alloy patch done");
                }else if(method.name.equals("checkSmelteryStructure")){

                    // We want to insert after updateSmelteryInfo(null);
                    // Which will be:
                    //   ACONST_NULL
                    //     INVOKEVIRTUAL slimeknights/tconstruct/smeltery/tileentity/TileSmeltery.updateSmelteryInfo (Lslimeknights/tconstruct/smeltery/multiblock/MultiblockDetection$MultiblockStructure;)V

                    //Scan for ACONST_NULL
                    AbstractInsnNode currentInsn = method.instructions.getFirst();
                    while((currentInsn = currentInsn.getNext()) != null) {
                        if(currentInsn.getOpcode() == Opcodes.ACONST_NULL){
                            currentInsn = currentInsn.getNext();
                            if(
                                    currentInsn.getOpcode() == Opcodes.INVOKEVIRTUAL
                                    && currentInsn instanceof MethodInsnNode
                                    && ((MethodInsnNode) currentInsn).owner.equals("slimeknights/tconstruct/smeltery/tileentity/TileSmeltery")
                                    && ((MethodInsnNode) currentInsn).name.equals("updateSmelteryInfo")
                                    && ((MethodInsnNode) currentInsn).desc.equals(
                                        "(Lslimeknights/tconstruct/smeltery/multiblock/MultiblockDetection$MultiblockStructure;)V"
                                    )
                            ){
                                //Build payload: SmeltAlloyHook.removeSmeltery(this)
                                InsnList payload = new InsnList();
                                payload.add(new VarInsnNode(Opcodes.ALOAD, 0)); // this
                                payload.add(new MethodInsnNode(
                                        Opcodes.INVOKESTATIC,
                                        "guru/haun/tcon/controls/hook/SmeltAlloyHook",
                                        "removeSmeltery",
                                        "(Lslimeknights/tconstruct/smeltery/tileentity/TileSmeltery;)V",
                                        false
                                ));

                                //Inject payload after target statement, then break
                                method.instructions.insert(currentInsn,payload);
                                FMLLog.info("[TControls] Smeltery deactivate patch done");
                                break; //Break from Instruction search loop
                            }
                        }

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
