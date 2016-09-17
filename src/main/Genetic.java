package main;

import java.util.ArrayList;
import java.util.Random;

public class Genetic {
	private class Organism {
		public Configuration config;
		public ArrayList<Operation> ops;
		public double proximity;
		public double fitness;
		
		Organism(Configuration config, int size) {
			this.config = config;
			
			ops = new ArrayList<Operation>();
			Random random = new Random();
			for (int i = 0; i < size; i++) {
				int index = random.nextInt(config.getOperations().size());
				ops.add(config.getOperations().get(index)); //adds random new operation to ops
			}
			
			calculateFitness();
		}
		
		Organism(Configuration config, ArrayList<Operation> ops) {
			this.config = config;
			
			this.ops = new ArrayList<Operation>();
			for (Operation op : ops) {
				this.ops.add(op);
			}
			
			calculateFitness();
		}
		
		public void calculateFitness() {
			proximity = config.getBeginVal();
			for (Operation op : ops) {
				proximity = op.execute(proximity);
			}
			proximity = Math.abs(config.getEndVal() - proximity);
			fitness = 1 / (proximity + ops.size());
		}
		
		public void mutate() {
			Random random = new Random();
			if (random.nextFloat() <= (1.0 / 3.0)) { //1 third chance
				//change last operation
				ops.remove(ops.size() - 1); //removes last op
				ops.add(config.getOperations().get(random.nextInt(config.getOperations().size()))); //adds random new operation to ops
				//essentially changes last operation
			} else if (random.nextBoolean()) { //1 third chance
				//add operation
				ops.add(config.getOperations().get(random.nextInt(config.getOperations().size()))); //adds random new operation to ops
			} else { //1 third chance
				//remove operation
				ops.remove(ops.size() - 1); //removes last op
			}
			
			calculateFitness();
		}
		
		public ArrayList<Organism> spawn(Organism mate) {
			// Creates the two new children as copies of the parents initially
			Organism child1 = new Organism(this.config, this.ops);
			Organism child2 = new Organism(mate.config, mate.ops);
			
			// Initializes the random number generator and retrieves organism sizes
			Random randGen = new Random();
			int orgSize = this.ops.size();
			int mateSize = mate.ops.size();
			
			// Generates cross-over points, the points are both inclusive e.g. 4 is the 5th item in the array
			int crossOrg1 = randGen.nextInt(orgSize - 1);
			int crossOrg2 = randGen.nextInt((orgSize - 1) - crossOrg1) + (orgSize - 2);
			
			int crossMate1 = randGen.nextInt(mateSize - 1);
			int crossMate2 = randGen.nextInt((mateSize - 1) - crossMate1) + (mateSize - 2);
			
			// Retrieves the subsets of operations from the generated cross-overs and deletes them from children
			ArrayList<Operation> subsetOrg = new ArrayList<Operation>();
			ArrayList<Operation> subsetMate = new ArrayList<Operation>();
			
			for (int i = crossOrg1; i <= crossOrg2; ++i) {
				subsetOrg.add(this.ops.get(i));
				child1.ops.remove(i);
			}
			
			for (int i = crossMate1; i <= crossMate2; ++i) {
				subsetMate.add(mate.ops.get(i));
				child2.ops.remove(i);
			}
			
			// Splice subsets into the children
			child1.ops.addAll(crossOrg1, subsetMate);
			child2.ops.addAll(crossMate1, subsetOrg);
			
			// Create an array to hold the children and return
			ArrayList<Organism> children = new ArrayList<Organism>();
			children.add(child1);
			children.add(child2);
			
			return children;
		}
		
		@Override
		public String toString() {
			double val = config.getBeginVal();
			String ret = Double.toString(val);
			for (Operation op : ops) {
				val = op.execute(val);
				ret += " " + op.toString();
			}
			ret += " = " + Double.toString(val);
			return ret;
		}
	}
	
	private Configuration config;
	private ArrayList<Organism> population;
	private Organism best = null;

	Genetic(Configuration config) {
		this.config = config;
		
		population = new ArrayList<Organism>();
		initPopulation(config);
	}
	
	public void run() {
		for (int i = 0; i < 5; i++) {
			System.out.println("\n[INFO] new generation");
			ArrayList<Organism> newGen = new ArrayList<Organism>();
			for (Organism org : population) {
				Organism newOrg = new Organism(org.config, org.ops);
				newOrg.mutate();
				newGen.add(newOrg);
				if (best == null || (newOrg.fitness > best.fitness)) {
					best = newOrg;
				}
			}
			for (Organism org : newGen) {
				population.add(org);
			}
			
			for (Organism org : population) {
				System.out.println(org.toString());
				System.out.println(org.fitness);
			}
		}
		
		System.out.println("\nBest organism: " + best.toString());
		System.out.println(best.fitness);
	}
	
	private void initPopulation(Configuration config) {
		for (int i = 0; i < 1; i++) {
			Organism org = new Organism(config, 5);
			population.add(org);
			if (best == null || (org.fitness > best.fitness)) {
				best = org;
			}
		}
		
		for (Organism org : population) {
			System.out.println(org.toString());
			System.out.println(org.fitness);
		}
	}
	
	private Organism selectParent() {
		return null;
	}
}
