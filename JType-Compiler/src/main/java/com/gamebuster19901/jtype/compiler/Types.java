package com.gamebuster19901.jtype.compiler;

import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.NoType;
import javax.lang.model.type.NullType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.WildcardType;

import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.model.JavacTypes;

@SuppressWarnings("exports")
public class Types extends com.sun.tools.javac.code.Types implements javax.lang.model.util.Types {
	
	private final JavacTypes javacTypes;
	
	public static Types instance(Context context) {
		/*
		 * Hackery to prevent an AssertionError
		 */
		com.sun.tools.javac.code.Types instance = context.get(typesKey);
        if (instance == null)
            instance = new Types(context);
        else {
        	if(!(instance instanceof Types)) {
        		AlterableContext newContext = new AlterableContext(context);
        		newContext.remove(typesKey);
        		instance = new Types(newContext);
        	}
        }
        return (Types)instance;
	}
	
	private Types(Context context) {
		super(context);
		this.javacTypes = JavacTypes.instance(context);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Element asElement(TypeMirror t) {
		return javacTypes.asElement(t);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isSameType(TypeMirror t1, TypeMirror t2) {
		return javacTypes.isSameType(t1, t2);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isSubtype(TypeMirror t1, TypeMirror t2) {
		return javacTypes.isSubtype(t1, t2);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isAssignable(TypeMirror t1, TypeMirror t2) {
		return javacTypes.isAssignable(t1, t2);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean contains(TypeMirror t1, TypeMirror t2) {
		return javacTypes.contains(t1, t2);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isSubsignature(ExecutableType m1, ExecutableType m2) {
		return javacTypes.isSubsignature(m1, m2);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<? extends TypeMirror> directSupertypes(TypeMirror t) {
		return javacTypes.directSupertypes(t);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TypeMirror erasure(TypeMirror t) {
		return javacTypes.erasure(t);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TypeElement boxedClass(PrimitiveType p) {
		return javacTypes.boxedClass(p);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PrimitiveType unboxedType(TypeMirror t) {
		return javacTypes.unboxedType(t);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TypeMirror capture(TypeMirror t) {
		return javacTypes.capture(t);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PrimitiveType getPrimitiveType(TypeKind kind) {
		return javacTypes.getPrimitiveType(kind);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public NullType getNullType() {
		return javacTypes.getNullType();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public NoType getNoType(TypeKind kind) {
		return javacTypes.getNoType(kind);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ArrayType getArrayType(TypeMirror componentType) {
		return javacTypes.getArrayType(componentType);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WildcardType getWildcardType(TypeMirror extendsBound, TypeMirror superBound) {
		return javacTypes.getWildcardType(extendsBound, superBound);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DeclaredType getDeclaredType(TypeElement typeElem, TypeMirror... typeArgs) {
		return javacTypes.getDeclaredType(typeElem, typeArgs);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DeclaredType getDeclaredType(DeclaredType containing, TypeElement typeElem, TypeMirror... typeArgs) {
		return javacTypes.getDeclaredType(containing, typeElem, typeArgs);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TypeMirror asMemberOf(DeclaredType containing, Element element) {
		return javacTypes.asMemberOf(containing, element);
	}

}
