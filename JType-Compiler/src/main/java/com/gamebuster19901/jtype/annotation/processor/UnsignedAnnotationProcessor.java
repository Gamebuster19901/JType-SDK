package com.gamebuster19901.jtype.annotation.processor;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic.Kind;


@SupportedSourceVersion(SourceVersion.RELEASE_21)
@SupportedAnnotationTypes("com.gamebuster19901.jtype.annotation.Unsigned")
@Deprecated(forRemoval = true)
public class UnsignedAnnotationProcessor extends AbstractProcessor {
	
	@Override
	public synchronized void init(ProcessingEnvironment env) {
		super.init(env);
	}
	
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Context context;
        for (final TypeElement annotation : annotations) {
        	context = new Context(roundEnv, annotation);
            for (final Element element : roundEnv.getElementsAnnotatedWith(annotation)) {
            	context = new Context(roundEnv, annotation, element);
            	for(final AnnotationMirror mirror : element.getAnnotationMirrors()) {
            		context = new Context(roundEnv, annotation, element, mirror);
            		if(mirror.getAnnotationType().asElement().equals(annotation)) {
            			switch(element.getKind()) {
	        				case BINDING_VARIABLE:
	        				case FIELD:
	        				case LOCAL_VARIABLE:
	        				case PARAMETER:
	        				case RECORD_COMPONENT:
	        					handleVariableType(context);
	        					break;
	        					
	        				case METHOD:
	        					break;
	        	
	        				default:
	        					break;
	        				} 
            		}
            	}
            }
        }
        return true;
    }
	 

	private boolean handleVariableType(Context context) {
		TypeMirror type = context.element.asType();
		Element element = (VariableElement) context.element();
		TypeKind kind = type.getKind();
		switch(kind) {
		
			case INT:
				break;
			case LONG:
				break;
			case BYTE:
				break;
			case SHORT:
				break;
				
			case ARRAY:
				printError("@Unsigned not yet implemented for array types", context);
				break;
	

			case DECLARED:
				try {
					@SuppressWarnings("unused")
					PrimitiveType primitive = this.processingEnv.getTypeUtils().unboxedType(type);
					printError("@Unsigned not yet implemented for boxed (non-primitive) types", context);
				}
				catch(IllegalArgumentException e) {
					printError(type.toString() + " cannot be declared @Unsigned", context);
				}
				break;
				
				
			case BOOLEAN:
			case DOUBLE:
			case FLOAT:
				printError(type.toString() + " cannot be declared @Unsigned", context);
				break;
				
			case CHAR:
				printError("char is already unsigned", context);
				break;
				
			case ERROR:
				break;
				
			case EXECUTABLE:
				printError("This message should never appear.", context);
				break;
				
			case INTERSECTION:
				printError("@Unsigned not yet implemented for intersecting types", context);
				break;

			case MODULE:
				printError("module cannot be declared @Unsigned", context);
				break;
				
			case NULL:
				printError("The 'null' type cannot be declared @Unsigned", context);
				break;
				
			case PACKAGE:
				printError("package cannot be declared @Unsigned", context);
				break;
				
			case TYPEVAR:
				printError("@Unsigned not yet implemented for generic types", context);
				break;
				
			case UNION:
				printError("Union types cannot be declared @Unsigned", context);
				break;
				
			case VOID:
				printError("void cannot be declared @Unsigned", context);
				break;
				
			case WILDCARD:
				printError("wildcard types cannot be declared @Unsigned", context);
				break;
				
			case NONE:
			case OTHER:
			default:
				printError("this entity cannot be declared @Unsigned", element);
				break;
		
		}
		return true;
	}
	
	private void printError(String msg, Element e) {
		processingEnv.getMessager().printError(msg, e);
	}
	
	private void printWarning(String msg, Element e) {
		processingEnv.getMessager().printWarning(msg, e);
	}
	
	private void printNote(String msg, Element e) {
		processingEnv.getMessager().printNote(msg, e);
	}
	
	private void printError(String msg, Context context) {
		if (context.element().getKind() == ElementKind.TYPE_PARAMETER) { //eclipse doesn't like showing an error on TYPE_PARAMETER, so we must show it on the enclosing class
			Element enclosingElement = context.element().getEnclosingElement();
			if(enclosingElement != null) {
				context = new Context(context.environment, context.annotation, enclosingElement);
			}
		}
		
		
		if(context.mirror() != null) {
	    	printError(msg, context.element(), context.mirror());
		}
		else {
			printError(msg, context.element());
		}
	}
	
	private void printError(String msg, Element e, AnnotationMirror m) {
		processingEnv.getMessager().printMessage(Kind.ERROR, msg, e, m);
	}
	
	private void printWarning(String msg, Element e, AnnotationMirror m) {
		processingEnv.getMessager().printMessage(Kind.WARNING, msg, e);
	}
	
	private void printNote(String msg, Element e, AnnotationMirror m) {
		processingEnv.getMessager().printMessage(Kind.NOTE, msg, e);
	}
	
	private static record Context (RoundEnvironment environment, TypeElement annotation, Element element, AnnotationMirror mirror) {
		
		public Context(RoundEnvironment environment, TypeElement annotation, Element element) {
			this(environment, annotation, element, null);
		}
		
		public Context(RoundEnvironment environment, TypeElement annotation) {
			this(environment, annotation, null, null);
		}
		
		public Context ofElement() {
			return new Context(environment, annotation, element);
		}
		
	}
	
}
