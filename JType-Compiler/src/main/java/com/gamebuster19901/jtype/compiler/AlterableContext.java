package com.gamebuster19901.jtype.compiler;

import java.lang.reflect.Field;
import java.util.Map;

import com.sun.tools.javac.util.Context;

@SuppressWarnings("exports")
public class AlterableContext extends Context {
	
	public AlterableContext(Context context) {
		try {
			Field htf = Context.class.getDeclaredField("ht");
			htf.setAccessible(true);
			Map<Key<?>,Object> ht = (Map<Key<?>, Object>) htf.get(context);
			ht.forEach((key, obj) -> {
				this.ht.put(key, obj);
			});
		}
		catch(NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			throw new Error(e);
		}
	}
	
	public <T> Object remove(Key<T> key) {
		Object ret = ht.remove(key);
		return ret;
	}
	
}
