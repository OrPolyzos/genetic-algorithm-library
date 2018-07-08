package com.algorithm.genetic.convex_hull.ch_ga.domain;

import com.algorithm.genetic.convex_hull.domain.Point;
import com.algorithm.genetic.convex_hull.utilities.CH_Utilities;
import com.algorithm.genetic.library.ga.domain.Chromosome;
import com.algorithm.genetic.library.ga.domain.Dna;
import com.algorithm.genetic.library.ga.domain.GeneticAlgorithm;
import com.algorithm.genetic.library.ga.domain.Population;
import com.algorithm.genetic.library.ga.techniques.CrossOverTechnique;
import com.algorithm.genetic.library.ga.techniques.FitnessTechnique;
import com.algorithm.genetic.library.ga.techniques.MutationTechnique;
import com.algorithm.genetic.library.ga.techniques.SelectionTechnique;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CH_GeneticAlgorithm extends GeneticAlgorithm<CH_Gene> {

    private List<Point> points;

    public CH_GeneticAlgorithm(List<Point> points, int populationCount, double mutationRate, FitnessTechnique<CH_Gene> ch_fitnessTechnique, SelectionTechnique<CH_Gene> selectionTechnique, CrossOverTechnique<CH_Gene> crossOverTechnique, Map<Integer, MutationTechnique<CH_Gene>> mutationTechniqueMap) {
        super(populationCount, mutationRate, ch_fitnessTechnique, selectionTechnique, crossOverTechnique, mutationTechniqueMap);
        this.points = points;
    }

    @Override
    public void findFittestChromosomeEver() {
        if (getPopulation().getFittestChromosome() == null) {
            System.out.println("here");
        }
        boolean betterFitness = getPopulation().getFittestChromosome().getFitness() > getBestFitnessEver();
        boolean equalFitness = false;
        boolean lessConvexHullPoints = false;
        if (!betterFitness) {
            equalFitness = getPopulation().getFittestChromosome().getFitness() == getBestFitnessEver();
            lessConvexHullPoints = getPopulation().getFittestChromosome().getDna().getGene().getConvexHull().size() < getFittestChromosomeEver().getDna().getGene().getConvexHull().size();
        }
        if (betterFitness || (equalFitness && lessConvexHullPoints)) {
            setFittestChromosomeEver(getPopulation().getFittestChromosome());
            setBestFitnessEver(getFittestChromosomeEver().getFitness());
        }
    }

    @Override
    public void initialGeneration() {
        List<Chromosome<CH_Gene>> chromosomes = new ArrayList<>();
        for (int i = 0; i < this.getPopulationCount(); i++) {
            List<Point> randomConvexHull = CH_Utilities.getRandomPoints(points, 3);
            CH_Gene gene = new CH_Gene(points, randomConvexHull);
            Dna<CH_Gene> ch_dna = new Dna<>(gene);
            Chromosome<CH_Gene> chromosome = new Chromosome<>(ch_dna, getFitnessTechnique());
            chromosomes.add(chromosome);
        }
        Population<CH_Gene> initialPopulation = new Population<>(chromosomes);
        this.setPopulation(initialPopulation);
    }

    public void eliteGeneration() {
        Dna<CH_Gene> bestDna = getFittestChromosomeEver().getDna();
        CH_Gene gene = bestDna.getGene();
        List<Chromosome<CH_Gene>> chromosomes = new ArrayList<>();
        for (int i = 0; i < this.getPopulationCount(); i++) {
            Dna<CH_Gene> ch_dna = new Dna<>(gene);
            Chromosome<CH_Gene> chromosome = new Chromosome<>(ch_dna, getFitnessTechnique());
            chromosomes.add(chromosome);
        }
        Population<CH_Gene> elitePopulation = new Population<>(chromosomes);
        this.setPopulation(elitePopulation);
    }

    @Override
    public String toString() {
        return "Generation: " + getGenerationsCounter() + "\n" + "Outside Points: " + getFittestChromosomeEver().getDna().getGene().getOutsidePoints().size() + "\n" + "Sick Joints: " + getFittestChromosomeEver().getDna().getGene().getSickJoints().size() + "\n" + "Intersections: " + getFittestChromosomeEver().getDna().getGene().getIntersectionPoints().size() + "\n" + "Convex points: " + getFittestChromosomeEver().getDna().getGene().getConvexHull().size() + "\n";
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }
}