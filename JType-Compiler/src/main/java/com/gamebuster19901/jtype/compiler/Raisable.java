package com.gamebuster19901.jtype.compiler;

import com.sun.source.tree.Tree;

import javax.tools.Diagnostic.Kind;

import com.sun.source.tree.CompilationUnitTree;

sealed interface Raisable permits Tasked {

	public default void raiseNote(CharSequence msg, Tree tree, CompilationUnitTree root) {
		raise(Kind.NOTE, msg, tree, root);
	}
	
	public default void raiseWarning(CharSequence msg, Tree tree, CompilationUnitTree root) {
		raise(Kind.WARNING, msg, tree, root);
	}
	
	public default void raiseMandatoryWarning(CharSequence msg, Tree tree, CompilationUnitTree root) {
		raise(Kind.MANDATORY_WARNING, msg, tree, root);
	}
	
	public default void raiseError(CharSequence msg, Tree tree, CompilationUnitTree root) {
		raise(Kind.ERROR, msg, tree, root);
	}
	
	public default void raiseOther(CharSequence msg, Tree tree, CompilationUnitTree root) {
		raise(Kind.OTHER, msg, tree, root);
	}
	
	public default void raiseNote(Object msg, Tree tree, CompilationUnitTree root) {
		raise(Kind.NOTE, msg, tree, root);
	}
	
	public default void raiseWarning(Object msg, Tree tree, CompilationUnitTree root) {
		raise(Kind.WARNING, msg, tree, root);
	}
	
	public default void raiseMandatoryWarning(Object msg, Tree tree, CompilationUnitTree root) {
		raise(Kind.MANDATORY_WARNING, msg, tree, root);
	}
	
	public default void raiseError(Object msg, Tree tree, CompilationUnitTree root) {
		raise(Kind.ERROR, msg, tree, root);
	}
	
	public default void raiseOther(Object msg, Tree tree, CompilationUnitTree root) {
		raise(Kind.OTHER, msg, tree, root);
	}
	
	public default void raise(Kind kind, Object msg, Tree tree, CompilationUnitTree root) {
		raise(kind, msg.toString(), tree, root);
	}
	
	public void raise(Kind kind, CharSequence msg, Tree tree, CompilationUnitTree root);
}
