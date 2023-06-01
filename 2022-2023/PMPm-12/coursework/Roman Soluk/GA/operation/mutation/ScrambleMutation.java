package GA.operation.mutation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import GA.Chromosome;

public class ScrambleMutation implements Mutation{
    Random random = new Random();
    @Override
    public Chromosome mutate(Chromosome chromosome) {
        int mutationSize = random.nextInt(chromosome.getSize());
        int startIndex = random.nextInt(chromosome.getSize() - mutationSize);
        int endIndex = startIndex + mutationSize;
        List<Integer> mutationString = new ArrayList<>();
        for (int i = 0; i < chromosome.getSize(); i++) {
            mutationString.add(0);
        }
        for (int i = startIndex; i <= endIndex; i++) {
            mutationString.set(startIndex, chromosome.getGenes().get(i));
        }
        Collections.shuffle(mutationString);
        List<Integer> mutatedChromosome = new ArrayList<>();
        for (int i = 0; i < chromosome.getSize(); i++) {
            if (i>= startIndex && i<= endIndex){
                mutatedChromosome.add(mutationString.get(i));
            }else {
                mutatedChromosome.add(chromosome.getGenes().get(i));
            }
        }
        return new Chromosome(mutatedChromosome);
    }
}
