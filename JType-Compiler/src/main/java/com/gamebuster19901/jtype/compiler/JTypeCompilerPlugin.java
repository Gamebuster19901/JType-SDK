package com.gamebuster19901.jtype.compiler;

import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReference;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Set;

import com.sun.tools.javac.api.BasicJavacTask;
import com.sun.tools.javac.util.Context;

import com.sun.source.util.JavacTask;
import com.sun.source.util.Plugin;

public class JTypeCompilerPlugin implements Plugin {

	@Override
	public String getName() {
		return "JType";
	}

    
	@Override
	@SuppressWarnings("exports")
    public void init(JavacTask task, String... args) {
        System.out.println("Plugin initialized in module: " + getClass().getModule());
        ClassLoader loader = getClass().getClassLoader();
        System.out.println("Class loader: " + loader);
        System.out.println("Module: " + getClass().getModule());
        System.out.println("Defined Packages: " + Arrays.toString(loader.getDefinedPackages()));
        if(loader instanceof URLClassLoader) {
        	ModuleFinder finder = ModuleFinder.of(toPaths(((URLClassLoader) loader).getURLs()));
        	Set<ModuleReference> moduleReferences = finder.findAll();
            for (ModuleReference moduleReference : moduleReferences) {
                ModuleDescriptor module = moduleReference.descriptor();

                System.out.println("Module Name: " + module.name());
                System.out.println("Module Version: " + module.version());
                System.out.println("Module Location: " + moduleReference.location());
                System.out.println("Module Packages: " + module.packages());
            }
        }
        Context context = ((BasicJavacTask)task).getContext();
        TaskDelegator delegator = new TaskDelegator(task);
        task.addTaskListener(delegator);
    }

	
    static Path[] toPaths(URL[] urls) {
        Path[] paths = new Path[urls.length];
        for (int i = 0; i < urls.length; i++) {
            paths[i] = Paths.get(urls[i].getFile());
        }
        return paths;
    }
}
