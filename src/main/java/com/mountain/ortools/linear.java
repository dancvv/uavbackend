package com.mountain.ortools;

import com.google.ortools.Loader;
import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPObjective;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/linear")
public class linear {
    @GetMapping("/solve")
    public String solve(){
        Loader.loadNativeLibraries();
        MPSolver solver=MPSolver.createSolver("GLOP");
        MPVariable x=solver.makeNumVar(0.0,1.0,"x");
        MPVariable y=solver.makeNumVar(0.0,1.0,"y");
        ArrayList<String> strings = new ArrayList<>();
        strings.add("Number of variables="+solver.numVariables());
        strings.add("Number of variables="+solver.numVariables());
        System.out.println("Number of variables="+solver.numVariables());
//        约束条件
        MPConstraint ct=solver.makeConstraint(0.0,2.0,"ct");
        ct.setCoefficient(x,1);
        ct.setCoefficient(y,1);
        System.out.println("number of constraints= "+solver.numConstraints());
        MPObjective objective = solver.objective();
        objective.setCoefficient(x,3);
        objective.setCoefficient(y,1);
        objective.setMaximization();
        solver.solve();
        System.out.println("Solution:");
        strings.add("Objective value="+objective.value());
        System.out.println("Objective value="+objective.value());
        return strings.toString();
    }

}
