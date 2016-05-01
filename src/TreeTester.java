import java.util.*;


public class TreeTester {

	public static void main(String[] args) {
		
		ArrayList<Integer> elements = new ArrayList<Integer>();
		elements.add(100);
		elements.add(1000);
		elements.add(10000);
		//elements.add(100000);
		
		// Single Thread Test
		singleThreadTest(elements);
		
	}
	
	
	
	public static void singleThreadTest(ArrayList<Integer> elements) {
		
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
			
			// Testing Insert
			int numIterations = 100;
			
			long javaInsertTreeTime = 0;
			long standardInsertTreeTime = 0;
			long concurrentInsertTreeTime = 0;
			
			long javaGetTreeTime = 0;
			long standardGetTreeTime = 0;
			long concurrentGetTreeTime = 0;
			
			long javaRemoveTreeTime = 0;
			long standardRemoveTreeTime = 0;
			long concurrentRemoveTreeTime = 0;
			for(int j = 0; j < numIterations; j++) {
				
				// Insert Tests
				/*---------------------------------------------------------*/
				
				// Insert elements into Java's TreeSet
				TreeSet<Integer> t = new TreeSet<Integer>();
				long start = System.nanoTime();
				for(int i = 0; i < numElements; i++) {
					t.add(data.get(i));
				}
				long end = System.nanoTime();
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
				}
				end = System.nanoTime();
				concurrentRemoveTreeTime += end - start;
				
				
			}
			
			float avgJavaTreeInsert = javaInsertTreeTime/numIterations;
			float avgStdTreeInsert = standardInsertTreeTime/numIterations;
			float avgFineTuneInsert = concurrentInsertTreeTime/numIterations;
			
			float avgJavaTreeGet = javaGetTreeTime/numIterations;
			float avgStdTreeGet = standardGetTreeTime/numIterations;
			float avgFineTuneTreeGet = concurrentGetTreeTime/numIterations;
			
			float avgJavaTreeRemove = javaRemoveTreeTime/numIterations;
			float avgStdTreeRemove = standardRemoveTreeTime/numIterations;
			float avgFineTuneTreeRemove = concurrentRemoveTreeTime/numIterations;
			
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
			
			float standardToJavaInsert = avgStdTreeInsert/avgJavaTreeInsert;
			float concurrentToJavaInsert = avgFineTuneInsert/avgJavaTreeInsert;
			float concurrentToStandardInsert = avgFineTuneInsert/avgStdTreeInsert;
			
			float standardToJavaGet = avgStdTreeGet/avgJavaTreeGet;
			float concurrentToJavaGet = avgFineTuneTreeGet/avgJavaTreeGet;
			float concurrentToStandardGet = avgFineTuneTreeGet/avgStdTreeGet;
			
			float standardToJavaRemove = avgStdTreeRemove/avgJavaTreeRemove;
			float concurrentToJavaRemove = avgFineTuneTreeRemove/avgJavaTreeRemove;
			float concurrentToStandardRemove = avgFineTuneTreeRemove/avgStdTreeRemove;
			
			System.out.println("Comparison of Insert of " + numElements + " elements :");
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

}
