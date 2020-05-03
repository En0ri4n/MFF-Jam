package fr.eno.farmutils.core;

import java.util.HashMap;
import java.util.Map;

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
		Map<String, String> clazz = new HashMap<>();
		
		preInit(clazz);
		
		if(clazz.keySet().contains(name))
		{
			FarmingUtilities.LOGGER.info("About to patch : " + name);
	        return patchEntitiesAnimal(name, classData, name.equals(clazz.get(name)));
		}
		
		return classData;
	}
	
	public byte[] patchEntitiesAnimal(String name, byte[] basicClass, boolean obf)
	{
		String targetMethodName = obf ? "func_184651_r" : "initEntityAI";
		
	    ClassNode classNode = new ClassNode();
	    ClassReader classReader = new ClassReader(basicClass);
	    classReader.accept(classNode, ClassReader.EXPAND_FRAMES);
	    MethodNode mnode = ASMHelper.findMethod(classNode, targetMethodName, "()V");
	    
	    InsnList toInject = new InsnList();
	    toInject.add(new VarInsnNode(Opcodes.ALOAD, 0));
	    toInject.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "fr/eno/farmutils/core/Patcher", "patchEntitiesAnimal", "(Lnet/minecraft/entity/passive/EntityAnimal;)V", false));
	    mnode.instructions.insertBefore(mnode.instructions.getFirst(), toInject);
	 
	    ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
	    classNode.accept(cw);
	    return cw.toByteArray();
	}
	
	private void preInit(Map<String, String> clazz)
	{
		clazz.put("net.minecraft.entity.passive.EntityCow", "zv");
		clazz.put("net.minecraft.entity.passive.EntityChicken", "zu");
		clazz.put("net.minecraft.entity.passive.EntityOcelot", "zz");
		clazz.put("net.minecraft.entity.passive.EntityParrot", "aaa");
		clazz.put("net.minecraft.entity.passive.EntityPig", "aab");
		clazz.put("net.minecraft.entity.passive.EntityRabbit", "aad");
		clazz.put("net.minecraft.entity.passive.EntityLlama", "aaq");
		clazz.put("net.minecraft.entity.passive.EntityWolf", "aak");
	}

}
