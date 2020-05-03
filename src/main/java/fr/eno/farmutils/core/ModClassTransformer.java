package fr.eno.farmutils.core;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import fr.eno.farmutils.FarmingUtilities;
import net.minecraft.launchwrapper.IClassTransformer;

public class ModClassTransformer implements IClassTransformer
{
	@Override
	public byte[] transform(String name, String transformedName, byte[] classData)
	{
		if(name.equals("zv") || name.equals("net.minecraft.entity.passive.EntityCow"))
		{
			FarmingUtilities.LOGGER.info("About to patch : " + name);
	        return patchEntityCow(name, classData, name.equals("zv"));
		}
		
		return classData;
	}
	
	public byte[] patchEntityCow(String name, byte[] basicClass, boolean obf)
	{
		String targetMethodName = obf ? "func_184651_r" : "initEntityAI";
		
	    ClassNode classNode = new ClassNode();
	    ClassReader classReader = new ClassReader(basicClass);
	    classReader.accept(classNode, ClassReader.EXPAND_FRAMES);
	    MethodNode mnode = ASMHelper.findMethod(classNode, targetMethodName, "()V");
	    
	    InsnList toInject = new InsnList();
	    toInject.add(new VarInsnNode(Opcodes.ALOAD, 0));
	    toInject.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "fr/eno/farmutils/core/Patcher", "patchEntityCow", "(Lnet/minecraft/entity/passive/EntityCow;)V", false));
	    mnode.instructions.insertBefore(mnode.instructions.getFirst(), toInject);
	 
	    ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
	    classNode.accept(cw);
	    return cw.toByteArray();
	}

}
