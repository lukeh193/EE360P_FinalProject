import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class SkipListTester {

/*	public static void main(String[] args) {
		BasicSkipList basicList = new BasicSkipList();
		basicList.add(5);
		basicList.add(3);
		basicList.add(2);
		basicList.add(6);
		System.out.println(basicList.print());
		if(basicList.get(4) == null){
			System.out.println("4: This was not in it");
		}
		else{
			System.out.println(basicList.get(4).getValue());
		}
		
		if(basicList.get(6) == null){
			System.out.println("6: This was not in it");
		}
		else{
			System.out.println(basicList.get(6).getValue());
		}
		basicList.delete(2);
		System.out.println(basicList.print());

		
		FineTunedSkipList lockedList = new FineTunedSkipList();
		lockedList.add(5);
		lockedList.add(3);
		lockedList.add(2);
		lockedList.add(6);
		System.out.println(lockedList.print());
		lockedList.delete(6);
		System.out.println(lockedList.print());
		
		if(lockedList.get(4) == null){
			System.out.println("4: This was not in it");
		}
		else{
			System.out.println(lockedList.get(4).getValue());
		}
		
		if(lockedList.get(6) == null){
			System.out.println("6: This was not in it");
		}
		else{
			System.out.println(lockedList.get(6).getValue());
		}
		
	}
*/
	
	public static FineTunedSkipList concurrentSkipList;
	
	
	private static boolean printSingleThreadTests = false;
	private static boolean printMultiThreadTests = false;
	
	static double avgJavaSkipListInsert;
	static double avgStdSkipListInsert;
	static double avgFineTuneSkipListInsert;
	
	static double avgJavaSkipListGet;
	static double avgStdSkipListGet;
	static double avgFineTuneSkipListGet;
	
	static double avgJavaSkipListRemove;
	static double avgStdSkipListRemove;
	static double avgFineTuneSkipListRemove;
	
	public static void main(String[] args) {
		
		ArrayList<Integer> elements = new ArrayList<Integer>();
		
		// Get type of test to run
		if(args[0].equals("single")) {
			printSingleThreadTests = true;
			elements.add(10);
			elements.add(100);
			elements.add(1000);
			//elements.add(100000);
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
			
			double javaInsertSkipListTime = 0;
			double standardInsertSkipListTime = 0;
			double concurrentInsertSkipListTime = 0;
			
			double javaGetSkipListTime = 0;
			double standardGetSkipListTime = 0;
			double concurrentGetSkipListTime = 0;
			
			double javaRemoveSkipListTime = 0;
			double standardRemoveSkipListTime = 0;
			double concurrentRemoveSkipListTime = 0;
			for(int j = 0; j < numIterations; j++) {
				
				// Insert Tests
				/*---------------------------------------------------------*/
				
				// Insert elements into Java's TreeSet
				ConcurrentSkipListMap<Integer, String> t = new ConcurrentSkipListMap<Integer, String>();
				double start = System.nanoTime();
				for(int i = 0; i < numElements; i++) {
					t.putIfAbsent(data.get(i), "");
				}
				double end = System.nanoTime();
				javaInsertSkipListTime += end - start;
			
				// Insert elements in to Standard BasicSkipList
				BasicSkipList tree = new BasicSkipList();
				//System.out.println(tree.print());
				start = System.nanoTime();
				for(int i = 0; i < numElements; i++) {
					tree.add(data.get(i));
					//System.out.println(tree.print());
				}
				end = System.nanoTime();
				standardInsertSkipListTime += end - start;
				
				// Insert elements into FineTunedTree
				FineTunedSkipList tree2 = new FineTunedSkipList();
				start = System.nanoTime();
				for(int i = 0; i < numElements; i++) {
					tree2.add(data.get(i));
				}
				end = System.nanoTime();
				concurrentInsertSkipListTime += end - start;
				
				// Get Tests
				/*-------------------------------------------------------*/
				
				// Java's TreeSet
				start = System.nanoTime();
				for(int i = 0; i < numElements; i++) {
					t.get(i);
				}
				end = System.nanoTime();
				javaGetSkipListTime += end - start;
				
				// Our Standard Tree
				start = System.nanoTime();
				for(int i = 0; i < numElements; i++) {
					tree.get(i);
				}
				end = System.nanoTime();
				standardGetSkipListTime += end - start;
				
				// Our FineTunedTree
				start = System.nanoTime();
				for(int i = 0; i < numElements; i++) {
					tree2.get(i);
				}
				end = System.nanoTime();
				concurrentGetSkipListTime += end - start;
				
				// Remove Tests
				/*-------------------------------------------------------*/
				
				// Java's TreeSet
				start = System.nanoTime();
				for(int i = 0; i < numElements; i++) {
					t.remove(i);
				}
				end = System.nanoTime();
				javaRemoveSkipListTime += end - start;
				
				// Our Standard Tree
				start = System.nanoTime();
				for(int i = 0; i < numElements; i++) {
					tree.delete(i);
				}
				end = System.nanoTime();
				standardRemoveSkipListTime += end - start;
				
				// Our FineTunedTree
				start = System.nanoTime();
				for(int i = 0; i < numElements; i++) {
					tree2.delete(i);
					//tree2.inOrderTraversal();
				}
				end = System.nanoTime();
				concurrentRemoveSkipListTime += end - start;
	
			}
			
			avgJavaSkipListInsert = javaInsertSkipListTime/numIterations;
			avgStdSkipListInsert = standardInsertSkipListTime/numIterations;
			avgFineTuneSkipListInsert = concurrentInsertSkipListTime/numIterations;
			
			avgJavaSkipListGet = javaGetSkipListTime/numIterations;
			avgStdSkipListGet = standardGetSkipListTime/numIterations;
			avgFineTuneSkipListGet = concurrentGetSkipListTime/numIterations;
			
			avgJavaSkipListRemove = javaRemoveSkipListTime/numIterations;
			avgStdSkipListRemove = standardRemoveSkipListTime/numIterations;
			avgFineTuneSkipListRemove = concurrentRemoveSkipListTime/numIterations;
			
		/*	System.out.println("\nAverage Time for Insert of " + numElements + " elements :");
			System.out.println("\tJava' TreeSet\t\t: " + avgJavaSkipListInsert + " ns");
			System.out.println("\tOur Standard Tree\t: " + avgStdSkipListInsert + " ns");
			System.out.println("\tFine-Tuned Tree\t\t: " + avgFineTuneSkipListInsert + " ns");
			
			System.out.println("Average Time for Get of " + numElements + " elements :");
			System.out.println("\tJava's TreeSet\t\t: " + avgJavaSkipListGet + " ns");
			System.out.println("\tOur Standard Treet: " + avgStdSkipListGet + " ns");
			System.out.println("\tJava's TreeSet\t\t: " + avgFineTuneSkipListGet + " ns");
			
			System.out.println("Average Time for Remove of " + numElements + " elements :");
			System.out.println("\tJava's TreeSet\t\t: " + avgJavaSkipListRemove + " ns");
			System.out.println("\tOur Standard Treet: " + avgStdSkipListRemove + " ns");
			System.out.println("\tJava's TreeSet\t\t: " + avgFineTuneSkipListRemove + " ns");
			*/
			double standardToJavaInsert = avgStdSkipListInsert/avgJavaSkipListInsert;
			double concurrentToJavaInsert = avgFineTuneSkipListInsert/avgJavaSkipListInsert;
			double concurrentToStandardInsert = avgFineTuneSkipListInsert/avgStdSkipListInsert;
			
			double standardToJavaGet = avgStdSkipListGet/avgJavaSkipListGet;
			double concurrentToJavaGet = avgFineTuneSkipListGet/avgJavaSkipListGet;
			double concurrentToStandardGet = avgFineTuneSkipListGet/avgStdSkipListGet;
			
			double standardToJavaRemove = avgStdSkipListRemove/avgJavaSkipListRemove;
			double concurrentToJavaRemove = avgFineTuneSkipListRemove/avgJavaSkipListRemove;
			double concurrentToStandardRemove = avgFineTuneSkipListRemove/avgStdSkipListRemove;
						
			if(printSingleThreadTests) {
				System.out.println("\nComparison of Insert of " + numElements + " elements :");
				System.out.println("\tOur Standard Skip List to Java's ConcurrentSkipListMap\t: " + standardToJavaInsert);
				System.out.println("\tFine-TunedSkipList to Java's ConcurrentSkipListMap\t: " + concurrentToJavaInsert);
				System.out.println("\tFine-TunedSkipList to Our Standard Skip List\t: " + concurrentToStandardInsert);
				
				System.out.println("Comparison of Get of " + numElements + " elements :");
				System.out.println("\tOur Standard Skip List to Java's ConcurrentSkipListMap\t: " + standardToJavaGet);
				System.out.println("\tFine-TunedSkipList to Java's ConcurrentSkipListMap\t: " + concurrentToJavaGet);
				System.out.println("\tFine-TunedSkipList to Our Standard Skip List\t: " + concurrentToStandardGet);
				
				System.out.println("Comparison of Remove of " + numElements + " elements :");
				System.out.println("\tOur Standard Skip List to Java's ConcurrentSkipListMap\t: " + standardToJavaRemove);
				System.out.println("\tFine-TunedSkipList to Java's ConcurrentSkipListMap\t: " + concurrentToJavaRemove);
				System.out.println("\tFine-TunedSkipList to Our Standard Skip List\t: " + concurrentToStandardRemove);
			}			
			
		}
		
		return dataArrays;
	}
	
	
	public static void multiThreadTest(ArrayList<Integer> elements, ArrayList<ArrayList<Integer>> dataArray) {
		
		System.out.println("Starting multi-thread test\n");
		
		int numIterations = 1;
		ExecutorService executor;
		
		for(int i = 0; i < elements.size(); i++) {
			int numElements = elements.get(i);
			
			ArrayList<Integer> data = dataArray.get(i);			
			
			double totalInsert = 0;
			double totalGet = 0;
			long totalRemove = 0;
			
			for(int iter = 0; iter < numIterations; iter++) {
				
				concurrentSkipList = new FineTunedSkipList();
			
				// Insert Test
				//executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
				executor = Executors.newFixedThreadPool(1);
				double start = System.nanoTime();
				for(int j = 0; j < numElements; j++) {
					Thread t = new Thread(new SkipListAddThread(data.get(j)));
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
				//executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
				executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
				start = System.nanoTime();
				for(int j = 0; j < numElements; j++) {
					Thread t = new Thread(new SkipListGetThread(data.get(j)));
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
				//executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
				executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
				long start_long = System.nanoTime();
				for(int j = 0; j < numElements; j++) {
					Thread t = new Thread(new SkipListDeleteThread(data.get(j)));
					executor.execute(t);
				}
				executor.shutdown();
				try {
					executor.awaitTermination(100, TimeUnit.SECONDS);
				} catch (Exception e) {
					e.printStackTrace();
				}
				long end_long = System.nanoTime();
				totalRemove += end_long - start_long;	
			
			}
			
			System.out.println("Done with multi-thread tests, calculating statistics ...");
			
			double avgInsertTime = totalInsert/numIterations;
			double avgGetTime = totalGet/numIterations;
			double avgRemoveTime = (double) totalRemove/numIterations;
			
			if(printMultiThreadTests) {
				System.out.println("\nAverage Time for Insert of " + numElements + " elements :\n");
				System.out.println("\tJava' TreeSet\t\t: " + avgJavaSkipListInsert + " ns");
				System.out.println("\tOur Standard Tree\t: " + avgStdSkipListInsert + " ns\n");
				System.out.println("\tFine-Tuned Tree\t\t: " + avgFineTuneSkipListInsert + " ns");
				System.out.println("\tMulti-threads\t\t: " + avgInsertTime + " ns");
				
				System.out.println("\nAverage Time for Get of " + numElements + " elements :\n");
				System.out.println("\tJava's TreeSet\t\t: " + avgJavaSkipListGet + " ns");
				System.out.println("\tOur Standard Tree\t: " + avgStdSkipListGet + " ns\n");
				System.out.println("\tFine-Tuned Tree\t\t: " + avgFineTuneSkipListGet + " ns");
				System.out.println("\tMulti-threads\t\t: " + avgGetTime + " ns");
				
				System.out.println("\nAverage Time for Remove of " + numElements + " elements :\n");
				System.out.println("\tJava's TreeSet\t\t: " + avgJavaSkipListRemove + " ns");
				System.out.println("\tOur Standard Tree\t: " + avgStdSkipListRemove + " ns\n");
				System.out.println("\tFine-Tuned Tree\t\t: " + avgFineTuneSkipListRemove + " ns");
				System.out.println("\tMulti-threads\t\t: " + avgRemoveTime + " ns");
			}
		}
		
	}
}
