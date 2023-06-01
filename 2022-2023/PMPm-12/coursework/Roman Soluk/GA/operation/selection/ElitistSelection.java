package GA.operation.selection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import GA.Chromosome;

/**
 * Forms population taking n% of the best
 * chromosomes from the previous population
 */
public class ElitistSelection implements Selection {

    private int percent;

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }


    @Override
    public List<List<Chromosome>> formPopulation(int chromosomeSize, int populationNumber, int populationSize) {
        List<List<Chromosome>> population = new ArrayList<>();
        for (int j = 0; j < populationNumber; j++) {
            List<Chromosome> generation = new ArrayList<>();
            for (int i = 0; i < populationSize; i++) {
                generation.add(new Chromosome(chromosomeSize));
            }
            population.add(generation);
        }
        return population;
    }

    /**
     * @param population
     * @return
     */
    @Override
    public List<List<Chromosome>> executeModification(List<List<Chromosome>> population, List<Double> fitnessValues, int index) {
        if (index == population.size() - 1) {
            return population;
        }
        List<Chromosome> generation = population.get(index);
        Map<Chromosome, Double> map = new HashMap<>();
        for (int i = 0; i < fitnessValues.size(); i++) {
            map.put(generation.get(i), fitnessValues.get(i));
        }
        Map<Chromosome, Double> sortedMap = sortByValues(map);
        Set<Chromosome> keys = sortedMap.keySet();
        Integer elNum = (int) Math.round(((generation.size() * percent) / 100.0));
        for (int i = 0; i < elNum; i++) {
            population.get(index + 1).set(i, (Chromosome) keys.toArray()[i]);
        }

        return population;
    }

    private static <K, V extends Comparable<? super V>> Map<K, V> sortByValues(Map<K, V> map) {
        List<Map.Entry<K, V>> entryList = new ArrayList<>(map.entrySet());

        // Sort the entryList based on values using a Comparator
        entryList.sort(Map.Entry.comparingByValue());

        // Create a new LinkedHashMap to preserve the order of the sorted entries
        Map<K, V> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : entryList) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }
}
