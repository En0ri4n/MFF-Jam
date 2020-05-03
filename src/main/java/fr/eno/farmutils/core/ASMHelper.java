package fr.eno.farmutils.core;

import java.util.Iterator;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

public final class ASMHelper
{
	public static MethodNode findMethod(ClassNode cnode, String name, String desc)
	{
		for (MethodNode m : cnode.methods)
		{
			if (name.equals(m.name) && desc.equals(m.desc))
				return m;
		}
		return null;
	}
	
	public static FieldNode findField(ClassNode cnode, String name, String desc)
	{
		for (FieldNode f : cnode.fields)
		{
			if (name.equals(f.name) && desc.equals(f.desc))
				return f;
		}
		return null;
	}

	public static AbstractInsnNode getFirstInstrWithOpcode(MethodNode mn, int opcode)
	{
		Iterator<AbstractInsnNode> ite = mn.instructions.iterator();
		while (ite.hasNext())
		{
			AbstractInsnNode n = ite.next();
			if (n.getOpcode() == opcode)
				return n;
		}
		return null;
	}
}