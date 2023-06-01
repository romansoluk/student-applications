package GA.operation.crossover;

import java.util.List;
import java.util.Random;

import GA.Chromosome;

public interface Crossover {
    Random random = new Random();
    public List<Chromosome> crossover(List<Chromosome> chromosomes, int...chromosomeIndexes);
}
