package GA.geneticAlgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import GA.Chromosome;
import GA.Function;
import GA.operation.crossover.Crossover;
import GA.operation.mutation.Mutation;
import GA.operation.selection.Selection;

public class StandardGeneticAlgorithm extends GeneticAlgorithm {
    private int generationCounter = 500;
    private int noChangeIter = 20;
    private static final Random random = new Random();
    private Selection selection;
    private Mutation mutation;
    private Crossover crossover;

    private List<Function> nonlinearSystem;
    protected final Random gene = new Random();

    public StandardGeneticAlgorithm(Selection selection, Mutation mutation, Crossover crossover, List<Function> nonlinearSystem) {
        super();
        this.selection = selection;
        this.mutation = mutation;
        this.crossover = crossover;
        this.nonlinearSystem = nonlinearSystem;
    }

    protected double systemGenerationMin(double a, double b, double precision, int population, double mutationProbability, double crossoverProbability) {
        Function function1 = a1 -> a1[0] + a1[1];
        Function function2 = a1 -> 2 * a1[0] - a1[1];

        List<Function> funcs = new ArrayList<>();
        funcs.add(function1);
        funcs.add(function2);
        int varNum = 4;
        double result = 0;

        int bitNumber = getBitNum(a, b, precision);
        List<Chromosome> generation = new ArrayList<>();
        List<Double> generationValue = new ArrayList<>(population);

        //what for?
        for (int i = 0; i < population; i++) {
            generation.add(new Chromosome(varNum * bitNumber));
        }

        for (int i = 0; i < population; i++) {
            if (getProbabilityCheck(mutationProbability)) {
                Chromosome chromosomeToMutate = generation.get(i);
                generation.remove(i);
                generation.add(mutation.mutate(chromosomeToMutate));
            }

            if (getProbabilityCheck(crossoverProbability)) {
                generation = crossover.crossover(generation, i, random.nextInt(population));
            }

            for (int j = 0; j < funcs.size(); j++) {
                result = 0;
                List<Chromosome> var = new ArrayList<>();
                for (int v = 0; v < varNum; v++) {
                    var.add(getVariable(v * bitNumber, bitNumber, generation.get(i).getGenes()));
                }
                result += Math.abs(funcs.get(j).apply(convertToDecimal(var.get(0), a, b), convertToDecimal(var.get(1), a, b)/*, ConvertToDecimal(var[2], a, b), ConvertToDecimal(var[3], a, b)*/));
                generationValue.add(result);

            }
        }
        return Collections.min(generationValue);
    }

    public double executeAlgorithm(double a, double b, double precision, int[] populations, double mutationProbability, double crossoverProbability) {
        List<Double> populationMin = new ArrayList<>();

        double generationCounter = 0;
        double noChangeIter = 0;

        double generationMin = 0;
        List<Double> generationMinimum = new ArrayList<>();
        while (generationCounter < this.generationCounter && noChangeIter < this.noChangeIter) {

            generationMin = systemGenerationMin(a, b, precision, populations[0], mutationProbability, crossoverProbability);

            if (generationMinimum.isEmpty()) {
                generationMinimum.add(generationMin);
            }

            if (generationMin > Collections.min(generationMinimum)) {
                generationMinimum.add(generationMin);
                noChangeIter++;
            } else {
                generationMinimum.add(generationMin);
                noChangeIter = 0;
            }
            generationCounter++;
        }

        populationMin.add(Collections.min(generationMinimum));
        return Collections.min(populationMin);
    }

    Chromosome getVariable(int startIndex, int length, List<Integer> list) {
        List<Integer> sublist = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            sublist.add(list.get(i + startIndex));
        }
        return new Chromosome(sublist);
    }
}