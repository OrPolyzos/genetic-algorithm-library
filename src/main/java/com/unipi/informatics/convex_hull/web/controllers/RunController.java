package com.unipi.informatics.convex_hull.web.controllers;

import com.unipi.informatics.convex_hull.CH_Problem;
import com.unipi.informatics.convex_hull.ch_ga.domain.CH_Gene;
import com.unipi.informatics.convex_hull.converters.GeneticAlgorithmConverter;
import com.unipi.informatics.convex_hull.dao.GeneticAlgorithmDao;
import com.unipi.informatics.convex_hull.services.GeneticAlgorithmService;
import com.unipi.informatics.ga.domain.GeneticAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RunController {

    @Autowired
    private GeneticAlgorithmService geneticAlgorithmService;

    @RequestMapping(value = "/run", method = RequestMethod.GET)
    public GeneticAlgorithmDao run(@RequestParam int width, @RequestParam int height, @RequestParam double mutationRate, @RequestParam int populationCount, @RequestParam int pointsCount) throws Exception {
        CH_Problem CH_problem = new CH_Problem(width, height, mutationRate, populationCount, pointsCount);
        GeneticAlgorithm<CH_Gene> geneticAlgorithm = CH_problem.solve();
        return GeneticAlgorithmConverter.convertToGeneticAlgorithmDAO(geneticAlgorithm);
    }

}
