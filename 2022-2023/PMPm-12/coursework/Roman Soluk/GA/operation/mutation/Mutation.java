package GA.operation.mutation;

import java.util.Random;

import GA.Chromosome;

public interface Mutation {
    //int getMutationBitsAmount();
    //void setMutationBitsAmount();
    Random random = new Random();
    Chromosome mutate(Chromosome chromosome);
}
