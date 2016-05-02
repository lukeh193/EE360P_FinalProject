import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.sun.javafx.scene.control.skin.ProgressIndicatorSkin;


public class TreeTester {
	
	public static FineTunedTree concurrentTree;
	
	
	private static boolean printSingleThreadTests = false;
	private static boolean printMultiThreadTests = false;
	
	static double avgJavaTreeInsert;
	static double avgStdTreeInsert;
	static double avgFineTuneInsert;
	
	static double avgJavaTreeGet;
	static double avgStdTreeGet;
	static double avgFineTuneTreeGet;
	
	static double avgJavaTreeRemove;
	static double avgStdTreeRemove;
	static double avgFineTuneTreeRemove;
	
	public static void main(String[] args) {
		
		ArrayList<Integer> elements = new ArrayList<Integer>();
		
		// Get type of test to run
		if(args[0].equals("single")) {
			printSingleThreadTests = true;
			elements.add(1000);
			elements.add(10000);
			elements.add(100000);
		} else if(args[0].equals("multi")) {
			printMultiThreadTests = true;
			elements.add(Integer.parseInt(args[1]));
		}
		
		// Single Thread Test
		ArrayList<ArrayList<Integer>> dataArrays = singleThreadTest(elements);
		
		// Multi Thread Test
		if(printMultiThreadTests)
			multiThreadTest(elements, dataArrays);
	}
	
	
	
	public static ArrayList<ArrayList<Integer>> singleThreadTest(ArrayList<Integer> elements) {
		
		ArrayList<ArrayList<Integer>> dataArrays = new ArrayList<ArrayList<Integer>>();
		
		System.out.println("Starting Singe-Thread Test ...\n");
		
		for(int e = 0; e < elements.size(); e++) {
			int numElements = elements.get(e);
		
				
			// Build array list of 100 elements to be inserted so both 
			// trees will use same data, in same order
			List<Integer> data = new ArrayList<Integer>();
			for(int i = 0; i < numElements; i++) {
				data.add(i);
			}
			Collections.shuffle(data);
			dataArrays.add((ArrayList<Integer>) data);
			
			// Testing Insert
			int numIterations = 100;
			
			double javaInsertTreeTime = 0;
			double standardInsertTreeTime = 0;
			double concurrentInsertTreeTime = 0;
			
			double javaGetTreeTime = 0;
			double standardGetTreeTime = 0;
			double concurrentGetTreeTime = 0;
			
			double javaRemoveTreeTime = 0;
			double standardRemoveTreeTime = 0;
			double concurrentRemoveTreeTime = 0;
			for(int j = 0; j < numIterations; j++) {
				
				// Insert Tests
				/*---------------------------------------------------------*/
				
				// Insert elements into Java's TreeSet
				TreeSet<Integer> t = new TreeSet<Integer>();
				double start = System.nanoTime();
				for(int i = 0; i < numElements; i++) {
					t.add(data.get(i));
				}
				double end = System.nanoTime();
				javaInsertTreeTime += end - start;
			
				// Insert elements in to Standart Treee
				Tree tree = new Tree();
				start = System.nanoTime();
				for(int i = 0; i < numElements; i++) {
					tree.insert(data.get(i));
				}
				end = System.nanoTime();
				standardInsertTreeTime += end - start;
				
				// Insert elements into FineTunedTree
				FineTunedTree tree2 = new FineTunedTree();
				start = System.nanoTime();
				for(int i = 0; i < numElements; i++) {
					tree2.insert(data.get(i));
				}
				end = System.nanoTime();
				concurrentInsertTreeTime += end - start;
				
				// Get Tests
				/*-------------------------------------------------------*/
				
				// Java's TreeSet
				start = System.nanoTime();
				for(int i = 0; i < numElements; i++) {
					t.contains(i);
				}
				end = System.nanoTime();
				javaGetTreeTime += end - start;
				
				// Our Standard Tree
				start = System.nanoTime();
				for(int i = 0; i < numElements; i++) {
					tree.get(i);
				}
				end = System.nanoTime();
				standardGetTreeTime += end - start;
				
				// Our FineTunedTree
				start = System.nanoTime();
				for(int i = 0; i < numElements; i++) {
					tree2.get(i);
				}
				end = System.nanoTime();
				concurrentGetTreeTime += end - start;
				
				// Remove Tests
				/*-------------------------------------------------------*/
				
				// Java's TreeSet
				start = System.nanoTime();
				for(int i = 0; i < numElements; i++) {
					t.remove(i);
				}
				end = System.nanoTime();
				javaRemoveTreeTime += end - start;
				
				// Our Standard Tree
				start = System.nanoTime();
				for(int i = 0; i < numElements; i++) {
					tree.remove(i);
				}
				end = System.nanoTime();
				standardRemoveTreeTime += end - start;
				
				// Our FineTunedTree
				start = System.nanoTime();
				for(int i = 0; i < numElements; i++) {
					tree2.remove(i);
					//tree2.inOrderTraversal();
				}
				end = System.nanoTime();
				concurrentRemoveTreeTime += end - start;
	
			}
			
			avgJavaTreeInsert = javaInsertTreeTime/numIterations;
			avgStdTreeInsert = standardInsertTreeTime/numIterations;
			avgFineTuneInsert = concurrentInsertTreeTime/numIterations;
			
			avgJavaTreeGet = javaGetTreeTime/numIterations;
			avgStdTreeGet = standardGetTreeTime/numIterations;
			avgFineTuneTreeGet = concurrentGetTreeTime/numIterations;
			
			avgJavaTreeRemove = javaRemoveTreeTime/numIterations;
			avgStdTreeRemove = standardRemoveTreeTime/numIterations;
			avgFineTuneTreeRemove = concurrentRemoveTreeTime/numIterations;
			/*
			System.out.println("\nAverage Time for Insert of " + numElements + " elements :");
			System.out.println("\tJava' TreeSet\t\t: " + avgJavaTreeInsert + " ns");
			System.out.println("\tOur Standard Tree\t: " + avgStdTreeInsert + " ns");
			System.out.println("\tFine-Tuned Tree\t\t: " + avgFineTuneInsert + " ns");
			
			System.out.println("Average Time for Get of " + numElements + " elements :");
			System.out.println("\tJava's TreeSet\t\t: " + avgJavaTreeGet + " ns");
			System.out.println("\tOur Standard Treet: " + avgStdTreeGet + " ns");
			System.out.println("\tJava's TreeSet\t\t: " + avgFineTuneTreeGet + " ns");
			
			System.out.println("Average Time for Remove of " + numElements + " elements :");
			System.out.println("\tJava's TreeSet\t\t: " + avgJavaTreeRemove + " ns");
			System.out.println("\tOur Standard Treet: " + avgStdTreeRemove + " ns");
			System.out.println("\tJava's TreeSet\t\t: " + avgFineTuneTreeRemove + " ns");
			*/
			
			double standardToJavaInsert = avgStdTreeInsert/avgJavaTreeInsert;
			double concurrentToJavaInsert = avgFineTuneInsert/avgJavaTreeInsert;
			double concurrentToStandardInsert = avgFineTuneInsert/avgStdTreeInsert;
			
			double standardToJavaGet = avgStdTreeGet/avgJavaTreeGet;
			double concurrentToJavaGet = avgFineTuneTreeGet/avgJavaTreeGet;
			double concurrentToStandardGet = avgFineTuneTreeGet/avgStdTreeGet;
			
			double standardToJavaRemove = avgStdTreeRemove/avgJavaTreeRemove;
			double concurrentToJavaRemove = avgFineTuneTreeRemove/avgJavaTreeRemove;
			double concurrentToStandardRemove = avgFineTuneTreeRemove/avgStdTreeRemove;
						
			if(printSingleThreadTests) {
				System.out.println("\nComparison of Insert of " + numElements + " elements :");
				System.out.println("\tOur Standard Tree to Java's TreeSet\t: " + standardToJavaInsert);
				System.out.println("\tFine-TunedTree to Java's TreeSet\t: " + concurrentToJavaInsert);
				System.out.println("\tFine-TunedTree to Our Standard Tree\t: " + concurrentToStandardInsert);
				
				System.out.println("Comparison of Get of " + numElements + " elements :");
				System.out.println("\tOur Standard Tree to Java's TreeSet\t: " + standardToJavaGet);
				System.out.println("\tFine-TunedTree to Java's TreeSet\t: " + concurrentToJavaGet);
				System.out.println("\tFine-TunedTree to Our Standard Tree\t: " + concurrentToStandardGet);
				
				System.out.println("Comparison of Remove of " + numElements + " elements :");
				System.out.println("\tOur Standard Tree to Java's TreeSet\t: " + standardToJavaRemove);
				System.out.println("\tFine-TunedTree to Java's TreeSet\t: " + concurrentToJavaRemove);
				System.out.println("\tFine-TunedTree to Our Standard Tree\t: " + concurrentToStandardRemove);
			}			
			
		}
		
		return dataArrays;
	}
	
	
	public static void multiThreadTest(ArrayList<Integer> elements, ArrayList<ArrayList<Integer>> dataArray) {
		
		System.out.println("Starting multi-thread test\n");
		
		int numIterations = 100;
		ExecutorService executor;
		
		for(int i = 0; i < elements.size(); i++) {
			int numElements = elements.get(i);
			
			ArrayList<Integer> data = dataArray.get(i);			
			
			double totalInsert = 0;
			double totalGet = 0;
			double totalRemove = 0;
			
			for(int iter = 0; iter < numIterations; iter++) {
				
				concurrentTree = new FineTunedTree();
			
				// Insert Test
				executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
				double start = System.nanoTime();
				for(int j = 0; j < numElements; j++) {
					Thread t = new Thread(new TreeInsertThread(data.get(j)));
					executor.execute(t);
				}
				executor.shutdown();
				try {
					executor.awaitTermination(100, TimeUnit.SECONDS);
				} catch (Exception e) {
					e.printStackTrace();
				}
				double end = System.nanoTime();
				totalInsert += end - start;
				
				// Get Test
				executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
				start = System.nanoTime();
				for(int j = 0; j < numElements; j++) {
					Thread t = new Thread(new TreeGetThread(data.get(j)));
					executor.execute(t);
				}
				executor.shutdown();
				try {
					executor.awaitTermination(100, TimeUnit.SECONDS);
				} catch (Exception e) {
					e.printStackTrace();
				}
				end = System.nanoTime();
				totalGet += end - start;
				
				
				// Remove Test
				executor = Executors.newFixedThreadPool(1);
				start = System.nanoTime();
				for(int j = 0; j < numElements; j++) {
					Thread t = new Thread(new TreeRemoveThread(data.get(j)));
					executor.execute(t);
				}
				executor.shutdown();
				try {
					executor.awaitTermination(100, TimeUnit.SECONDS);
				} catch (Exception e) {
					e.printStackTrace();
				}
				end = System.nanoTime();
				totalRemove += end - start;	
				
			}
			
			System.out.println("Done with multi-thread tests, calculating statistics ...");
			
			double avgInsertTime = totalInsert/numIterations;
			double avgGetTime = totalGet/numIterations;
			double avgRemoveTime = totalRemove/numIterations;
			
			if(printMultiThreadTests) {
				System.out.println("\nAverage Time for Insert of " + numElements + " elements :\n");
				System.out.println("\tJava' TreeSet\t\t: " + avgJavaTreeInsert + " ns");
				System.out.println("\tOur Standard Tree\t: " + avgStdTreeInsert + " ns\n");
				System.out.println("\tFine-Tuned Tree\t\t: " + avgFineTuneInsert + " ns");
				System.out.println("\tMulti-threads\t\t: " + avgInsertTime + " ns");
				
				System.out.println("\nAverage Time for Get of " + numElements + " elements :\n");
				System.out.println("\tJava's TreeSet\t\t: " + avgJavaTreeGet + " ns");
				System.out.println("\tOur Standard Tree\t: " + avgStdTreeGet + " ns\n");
				System.out.println("\tFine-Tuned Tree\t\t: " + avgFineTuneTreeGet + " ns");
				System.out.println("\tMulti-threads\t\t: " + avgGetTime + " ns");
				
				System.out.println("\nAverage Time for Remove of " + numElements + " elements :\n");
				System.out.println("\tJava's TreeSet\t\t: " + avgJavaTreeRemove + " ns");
				System.out.println("\tOur Standard Tree\t: " + avgStdTreeRemove + " ns\n");
				System.out.println("\tFine-Tuned Tree\t\t: " + avgFineTuneTreeRemove + " ns");
				System.out.println("\tMulti-threads\t\t: " + avgRemoveTime + " ns");
			}
		}
		
	}
	

}
