/**
 * This module provides a Java compiler plugin to compile new primitives and types 
 * that do not exist in the Java SE specification.
 * 
 * <p>
 * <strong>Warning:</strong> This module is under development, and its name, packages, classes, 
 * and exports are subject to frequent changes.
 * The annotation plugin is going to be removed in favor of the compiler plugin.
 * </p>
 *
 * @deprecated This module is under development, and its name, packages, classes, 
 * and exports are subject to frequent changes.
 */
@Deprecated(forRemoval = false)
module com.gamebuster19901.jtype.processor {
    /**
     * Exports the annotation processors related to JType processing.
     * 
     * Will be removed in favor of the compiler plugin.
     */
    exports com.gamebuster19901.jtype.annotation;

    /**
     * Exports the JType compiler plugin.
     */
    exports com.gamebuster19901.jtype.compiler;

    // Requires the Java and JDK compiler APIs
    requires jdk.compiler;
    requires java.compiler;
}