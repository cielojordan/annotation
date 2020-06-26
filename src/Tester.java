import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import annotations.Instructions;
import annotations.MeaningOfLife;
import annotations.Song;

public class Tester {
	public static void main(String[] args) throws Exception {
		// Given the lab.Start.class
			
		System.out.println("\nSTART");
		Class<?> startClass = Class.forName("lab.Start");
		Object startInstance = startClass.newInstance();
		
		// scan all the fields
		Field[] start_flist = startClass.getDeclaredFields();
		for (Field f : start_flist) {
			if (f.getName().equals("meaningOfLife")) {
				Instructions meaningOfLifeInstructions = f.getAnnotation(Instructions.class);
				System.out.println("Meaning of Life\n" + meaningOfLifeInstructions.text() + "\n" + meaningOfLifeInstructions.hint() + "\n");
			} else if (f.getName().equals("lyrics")) {
				Instructions lyricsInstructions = f.getAnnotation(Instructions.class);
				System.out.println("Lyrics\n" + lyricsInstructions.text() + "\n" + lyricsInstructions.hint() + "\n");
			} else if (f.getName().equals("misc")) {
				Instructions miscInstructions = f.getAnnotation(Instructions.class);
				System.out.println("Misc\n" + miscInstructions.text() + "\n" + miscInstructions.hint() + "\n");
			}
		}
		
		Class<?>[] clist = { Class.forName("lab.Class1"), Class.forName("lab.Class2"), Class.forName("lab.Class3"), Class.forName("lab.Class4")};
		
		for (Class<?> c: clist) {
			System.out.println("\n" + c.getSimpleName());
			Constructor<?> constr = c.getDeclaredConstructor();
	        constr.setAccessible(true);
			Object instance = constr.newInstance();
			
			if (c.isAnnotationPresent(Song.class)) {
				System.out.println("@Song");
			}
			
			
			Method[] mlist = c.getDeclaredMethods();
			for (Method m : mlist) {
				m.setAccessible(true);
				System.out.println(m.getName() + ": " + m.invoke(instance));
				
			}
			
			Field[] flist = c.getDeclaredFields();
			for (Field f : flist) {
				System.out.println("Field(s): ");
				f.setAccessible(true);
				System.out.println(f.getName() + ":" + f.toString());
				
			}
			
			if (c.isAnnotationPresent(MeaningOfLife.class)) {
				System.out.println("@MeaningOfLife ");
				MeaningOfLife meaningOfLife = c.getAnnotation(MeaningOfLife.class);
				System.out.println("intrinsicValue:" + meaningOfLife.intrinsicValue());
				System.out.println("multiplier:" + meaningOfLife.multiplier());
			}
		}
		
		System.out.println("\nResults: ");
		// Set Meaning of Life
		Field meaningOfLife = startClass.getDeclaredField("meaningOfLife");
		meaningOfLife.setAccessible(true);
		meaningOfLife.set(startInstance, 42);
		
		// Enter song lyrics "You treat me like a rose,  You give me room to grow"
		Field lyrics = startClass.getDeclaredField("lyrics");
		lyrics.setAccessible(true);
		lyrics.set(startInstance, "You give me room to grow");
		
		Method checkIfDone = startClass.getDeclaredMethod("checkIfDone");
		checkIfDone.setAccessible(true);
		checkIfDone.invoke(startInstance);
		
	}
}
