package GA.operation.crossover;

import java.util.ArrayList;
import java.util.List;

import GA.Chromosome;

public class MultiPointCrossover implements Crossover{

    @Override
    public List<Chromosome> crossover(List<Chromosome> chromosomes, int...chromosomeIndexes) {
        int startIndex = chromosomeIndexes[0];
        int endIndex = chromosomeIndexes[1];
        List<Integer> crossChromo1 = new ArrayList<>();
        List<Integer> crossChromo2 = new ArrayList<>();
        for (int i = 0; i < chromosomes.get(0).getSize(); i++) {
            if (i >= startIndex && i<=endIndex) {
                crossChromo1.add(chromosomes.get(chromosomeIndexes[0]).getGenes().get(i));
                crossChromo2.add(chromosomes.get(chromosomeIndexes[1]).getGenes().get(i));
            } else {
                crossChromo1.add(chromosomes.get(chromosomeIndexes[1]).getGenes().get(i));
                crossChromo2.add(chromosomes.get(chromosomeIndexes[0]).getGenes().get(i));
            }
        }
        chromosomes.set(chromosomeIndexes[0], new Chromosome(crossChromo1));
        chromosomes.set(chromosomeIndexes[1], new Chromosome(crossChromo2));
        return chromosomes;
    }
}
