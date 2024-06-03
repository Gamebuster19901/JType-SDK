package com.gamebuster19901.jtype.compiler;

import javax.tools.Diagnostic.Kind;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.Tree;
import com.sun.source.util.JavacTask;
import com.sun.source.util.Trees;

@FunctionalInterface
non-sealed interface Tasked extends Raisable {

	public JavacTask getTask();
	
	public default Trees getTrees() {
		return Trees.instance(getTask());
	}
	
	@Override
	public default void raise(Kind kind, CharSequence msg, Tree tree, CompilationUnitTree root) {
		getTrees().printMessage(kind, msg, tree, root);
	}
	
}
