package main;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
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
			
			//System.out.println("Proximity: " + proximity);
			
			proximity = Math.abs(config.getEndVal() - proximity);
			fitness = 1 / (proximity + ops.size());
		}
		
		public void mutate() {
			Random random = new Random();
			if (random.nextFloat() <= (1.0 / 3.0) && ops.size() > 0) { //1 third chance
				//change last operation
				ops.remove(ops.size() - 1); //removes last op
				ops.add(config.getOperations().get(random.nextInt(config.getOperations().size()))); //adds random new operation to ops
				//essentially changes last operation
			} else if (random.nextBoolean() && ops.size() > 0) { //1 third chance
				//remove operation
				ops.remove(ops.size() - 1); //removes last op
			} else { //1 third chance
				//add operation
				ops.add(config.getOperations().get(random.nextInt(config.getOperations().size()))); //adds random new operation to ops
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
			//System.out.println("Size of local: " + orgSize + ", Size of mate: " + mateSize);
			
			int crossOrg1 = orgSize <= 1 ? 0 : randGen.nextInt(orgSize - 1);
			int crossOrg2 = orgSize - 1; //randGen.nextInt((orgSize - 1) - crossOrg1) + crossOrg1;
			
			int crossMate1 = mateSize <= 1 ? 0 : randGen.nextInt(mateSize - 1);
			int crossMate2 = mateSize - 1; //randGen.nextInt((mateSize - 1) - crossMate1) + crossMate1;

			//System.out.println("crossOrg1: " + crossOrg1 + ", crossOrg2: " + crossOrg2);
			//System.out.println("crossMate1: " + crossMate1 + ", crossMate2: " + crossMate2);
			
			// Retrieves the subsets of operations from the generated cross-overs and deletes them from children
			ArrayList<Operation> subsetOrg = new ArrayList<Operation>();
			ArrayList<Operation> subsetMate = new ArrayList<Operation>();
			
			for (int i = crossOrg1; i <= crossOrg2; ++i) {
				subsetOrg.add(this.ops.get(i));
				child1.ops.remove(crossOrg1);
			}
			
			for (int i = crossMate1; i <= crossMate2; ++i) {
				subsetMate.add(mate.ops.get(i));
				child2.ops.remove(crossMate1);
			}
			
			// Splice subsets into the children
			child1.ops.addAll(crossOrg1, subsetMate);
			child2.ops.addAll(crossMate1, subsetOrg);
			//System.out.println(child2.toString());
			
			// Mutate those children muahahahahahahaha
			child1.mutate();
			child2.mutate();
			//System.out.println(child2.toString());
			
			// Create an array to hold the children and return
			ArrayList<Organism> children = new ArrayList<Organism>();
			
			// Check if either of the children has a NaN fitness score
			if (!Double.isNaN(child1.fitness)) {
				children.add(child1);
			}
			
			if (!Double.isNaN(child2.fitness)) {
				children.add(child2);
			}
			
			//System.out.println("====");
			
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
	private ArrayList<Double> accNormFit;
	private Organism best = null;

	Genetic(Configuration config) {
		this.config = config;
		
		// Create a priority queue that sorts organisms by their fitness scores
		population = new ArrayList<Organism>();
		
		// Initialize our accumulated normal fitness array
		accNormFit = new ArrayList<Double>();
		
		// Create a seed population
		initPopulation();
	}
	
	public void run() {
		double startTime = (new Date()).getTime(); //used for timer
		double endTime = (new Date()).getTime();
		
		int generations = 1; //keep track of the number of generations
		
		Organism runningBest = null;
			
		while (best.proximity != 0 && (endTime - startTime) < (config.getTimeLimit()*1000)) {
System.out.println("loop");
System.out.println(endTime);
System.out.println(startTime);
			computeAccumulatedNormalFitness();
			ArrayList<Organism> newGen = new ArrayList<Organism>();
			ArrayList<Organism> oldGen = new ArrayList<Organism>();
			
			// Create a new generation of equal size as the original
			while (newGen.size() < population.size()) {
				Organism[] parents = getParents();
				
				// Adds two children
				newGen.addAll(parents[0].spawn(parents[1]));
			}
			
			// Sort the new generation
			newGen = sortPopulation(newGen);
			
			//culling
			population.clear(); //out goes the old generation
			
			//in comes the new generation (top half of the children)	
			for(int i = 0; i < newGen.size()/2; i++)
				population.add(newGen.get(i));
			
			
			elitism();
			
			/*
			for (Organism org : newGen) {
				System.out.println(org.toString());
			}
			*/
			
			population = newGen;
			best = population.get(0);
			
			//update tracking values;

			endTime = (new Date()).getTime();
			generations++;
						
			if(runningBest != null){
				if(best.proximity < runningBest.proximity)
					runningBest = best;
			}else{
				runningBest = best;
			}
		}
		
		
		
		int tempVal = (int)config.getBeginVal();
		for(Operation op: runningBest.ops){
			System.out.println(tempVal + " " + op.toString() + " = " + op.execute(tempVal));
			tempVal = (int)op.execute(tempVal);
		}
		System.out.println("Error: " + (Math.abs(config.getEndVal() - tempVal)));
		System.out.println("Size of organism: " + runningBest.ops.size());
		System.out.println("Search Required: " + (endTime-startTime)/1000);
		System.out.println("Generations: " + generations);
	}
	
	public ArrayList<Organism> sortPopulation(ArrayList<Organism> pop){
		pop.sort(new Comparator<Organism>() {
			@Override
			public int compare(Organism org1, Organism org2) {
				return (int)(org1.fitness - org2.fitness);
			}
		});
		
		return pop;
	}
	
	/**
	 * Adds a random population of size 1/4 population and inserts them in order
	 * 
	 * @return void
	 */
	public void elitism(){
		ArrayList<Organism> randomOrganisms = new ArrayList<Organism>();
		
		for(int i = 0; i < population.size()/4; i++){
			Organism org;
			do {
				org = new Organism(this.config, 5);
			} while(Double.isNaN(org.fitness));
			
			randomOrganisms.add(org);
		}
		
		//insert new organisms into correct spot
		int placementIndex = 0;
		for(int i = 0; i < randomOrganisms.size(); i++){
			while(population.get(placementIndex).fitness > randomOrganisms.get(i).fitness && placementIndex < population.size()-1){
				placementIndex++;
			}
			
			population.add(placementIndex, randomOrganisms.get(i));
		}
		
	}
	
	private void initPopulation() {
		// Create an even number of population if you don't want one lonely member of the original bunch to not
		// reproduce and die out which would be sad, poor little organism :(
		for (int i = 0; i < 5; i++) {
			// If the organism has an invalid operator sequence, ignore it and make a new one
			Organism org;
			do {
				org = new Organism(this.config, 5);
			} while(Double.isNaN(org.fitness));
			
			population.add(org);
		}
		
		// Sort the initial population by fitness
		population.sort(new Comparator<Organism>() {
			@Override
			public int compare(Organism org1, Organism org2) {
				if (org1.fitness < org2.fitness) {
					return 1;
				}
				else if (org1.fitness == org2.fitness) {
					return 0;
				} else {
					return -1;
				}
			}
		});
		
		best = population.get(0);
		
		/*
		for (Organism org : population) {
			System.out.println(org.toString());
			System.out.println(org.fitness);
		}
		*/
	}
	
	/**
	 * Takes the population and returns an array corresponding to each organism's accumulated normal fitness. 
	 * The population must be sorted in advance.
	 * 
	 * @return An ArrayList where each value corresponds to the ANF of the organism at that index in the queue
	 */
	private void computeAccumulatedNormalFitness() {
		// Reset our accumulated normal fitness array
		accNormFit.clear();
		
		// Compute normalization constant
		double normConst = 0;
		for (Organism org : population) {
			normConst += org.fitness;
		}
		
		// Compute ANF in a single loop since the pop's sorted
		double acc = 0;
		for (Organism org : population) {
			acc += (double)org.fitness / (double)normConst;
			accNormFit.add(acc);	
		}
	}
	
	/**
	 * Picks two random members of the population based on the distributions
	 * from the accumulated normal fitness
	 */
	private Organism[] getParents() {
		Organism[] parents = new Organism[2];
		Random randGen = new Random();
		
		int firstParentIndex = -1;
		for (int i = 0; i < 2; ++i) {
			Double selectionVal = randGen.nextDouble();
			for (int j = 0; j < accNormFit.size(); ++j) {
				if (accNormFit.get(j) > selectionVal && j != firstParentIndex) {
					parents[i] = population.get(j);
					break;
				}
			}
		}
		
		return parents;
	}
}
