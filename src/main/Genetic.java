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
			fitness = (proximity * ops.size()) + ops.size();
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
		for (int i = 0; i < 3; i++) {
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
