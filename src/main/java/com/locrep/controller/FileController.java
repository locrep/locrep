package com.locrep.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.*;


@RestController
@RequestMapping("/")
public class FileController {

    @ResponseBody
    @RequestMapping(value = "/maven2/**/{artifactId}/{version}/{artifactVersion}")
    public ArrayList<String> getPath(HttpServletRequest request, @PathVariable("artifactId") String artifactId,
            @PathVariable("version") String version, @PathVariable("artifactVersion") String artifactVersion) {
        String path = request.getRequestURI();
        int artifactIndex = path.indexOf(artifactId);
        String sub = path.substring(0, artifactIndex);
        String mvn = sub.substring((sub.lastIndexOf("2/") + 2));
        mvn = mvn.substring(0, mvn.length() - 1);

        String groupId = mvn.replace("/", ".");

        ArrayList<String> dependencyList = new ArrayList<String>();
        dependencyList.add(groupId);
        dependencyList.add(artifactId);
        dependencyList.add(version);
        return dependencyList;
    }
}
