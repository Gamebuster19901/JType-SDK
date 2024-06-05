package com.gamebuster19901.jtype.compiler;

import java.lang.annotation.Annotation;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.JCTree.JCCompilationUnit;
import com.sun.tools.javac.tree.TreeScanner;
import com.sun.tools.javac.tree.JCTree.JCVariableDecl;
import com.sun.tools.javac.tree.JCTree.JCAnnotation;
import com.sun.tools.javac.tree.JCTree.JCArrayTypeTree;
import com.sun.tools.javac.api.BasicJavacTask;
import com.sun.tools.javac.util.Context;
import com.sun.source.util.JavacTask;
import com.sun.source.util.TaskEvent;
import com.sun.source.util.TaskListener;
import com.sun.source.util.Trees;

import com.gamebuster19901.jtype.annotation.Unsigned;

class TaskDelegator implements TaskListener, Tasked {

	private final JavacTask task;
	private final Types types;
	private final Trees trees;
	private final Context context;
	
	TaskDelegator(JavacTask task) {
		this.task = task;
		this.trees = Trees.instance(task);
		this.context = ((BasicJavacTask)task).getContext();
		this.types = Types.instance(context);
	}
	
	@Override
	public void started(TaskEvent e) {} //NO-OP
	
	@Override
	public void finished(TaskEvent e) {
		if(e.getKind() == TaskEvent.Kind.ANALYZE) {
			JCTree.JCCompilationUnit compilationUnit = (JCCompilationUnit) e.getCompilationUnit();
			
			compilationUnit.accept(new TreeVisitor(compilationUnit));
		}
	}

	@Override
	public JavacTask getTask() {
		return task;
	}
	
	@Override
	public Trees getTrees() {
		return trees;
	}
	
	public boolean isAnnotation(JCAnnotation annotation, Class<? extends Annotation> type) {
		return types.isSameType(annotation.type, getTypeMirror(type));
	}

	public boolean isType(JCVariableDecl variable, Class<?> type) {
		return types.isSameType(variable.type, getTypeMirror(type));
	}
	
	private TypeMirror getTypeMirror(Class<?> type) {
		if(type.isArray()) {
			return types.getArrayType(getTypeMirror(type.getComponentType()));
		}
		if(type.isPrimitive()) {
			if (type.equals(byte.class)) {
				return types.getPrimitiveType(TypeKind.BYTE);
			}
			else if (type.equals(short.class)) {
				return types.getPrimitiveType(TypeKind.SHORT);
			}
			else if (type.equals(int.class)) {
				return types.getPrimitiveType(TypeKind.INT);
			}
			else if (type.equals(long.class)) {
				return types.getPrimitiveType(TypeKind.LONG);
			}
			else if (type.equals(float.class)) {
				return types.getPrimitiveType(TypeKind.FLOAT);
			}
			else if (type.equals(double.class)) {
				return types.getPrimitiveType(TypeKind.DOUBLE);
			}
			else if (type.equals(char.class)) {
				return types.getPrimitiveType(TypeKind.CHAR);
			}
			else if (type.equals(boolean.class)) {
				return types.getPrimitiveType(TypeKind.BOOLEAN);
			}
			else if(type.equals(void.class)) {
				return types.getNoType(TypeKind.VOID);
			}
			else {
				throw new AssertionError(type);
			}
		}
		else {
			TypeElement e = task.getElements().getTypeElement(type.getCanonicalName());
			if(e != null) {
				return task.getElements().getTypeElement(type.getCanonicalName()).asType();
			}
			else {
				throw new IllegalArgumentException(type.getName());
			}
		}
	}
	
	public boolean isArray(JCVariableDecl variable) {
		return variable.vartype instanceof JCArrayTypeTree;
	}
	
	private class TreeVisitor extends TreeScanner {
		
		private final JCCompilationUnit compilationUnit;
		
		public TreeVisitor(JCCompilationUnit compilationUnit) {
			this.compilationUnit = compilationUnit;
		}
		
		@Override
		public void visitVarDef(JCVariableDecl tree) {
			for(JCAnnotation annotation : tree.mods.annotations) {
				if(isAnnotation(annotation, Unsigned.class)) {
					
					if(!tree.type.isPrimitive()) {
						computeNonPrimitiveError(tree);
					}
					else {
						computePrimitive(tree);
					}
				}
			}
			super.visitVarDef(tree);
		}
		
		void computeNonPrimitiveError(JCVariableDecl tree) {
			if(isArray(tree)) {
				raiseError("@Unsigned not yet implemented on array types", tree.vartype, compilationUnit);
				return;
			}
			if(types.unboxedTypeOrType(tree.type).isPrimitive()) {

				switch(types.unboxedType(tree.type).getKind()) {
					case VOID:
					case DOUBLE:
					case FLOAT:
					case BOOLEAN:
						raiseError(tree.type + " cannot be unsigned", tree.vartype, compilationUnit);
						break;
					case CHAR:
						raiseError(tree.type + " is already unsigned", tree.vartype, compilationUnit);
						break;
					case BYTE:
					case INT:
					case LONG:
					case SHORT:
						raiseError("@Unsigned not yet implemented for boxed types", tree.vartype, compilationUnit);
						break;
					default:
						throw new AssertionError();
				
				}
			}
			
			else {
				raiseError(tree.type + " cannot be unsigned", tree.vartype, compilationUnit);
			}
		}
		
		void computePrimitive(JCVariableDecl tree) {
			switch(tree.type.toString()) {
				
			}
		}
	}
	
}
