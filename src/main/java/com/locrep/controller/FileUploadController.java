package com.locrep.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.locrep.maven.Artifact;
import com.locrep.maven.MavenResolver;
import com.locrep.storage.StorageFileNotFoundException;
import com.locrep.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/")
    public String listUploadedFiles(Model model) throws IOException {

        model.addAttribute("files", storageService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                        "serveFile", path.getFileName().toString()).build().toString())
                .collect(Collectors.toList()));

        return "uploadForm";
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        storageService.store(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

                Artifact artif = new Artifact();
                artif.setGroupId(groupId);
                artif.setArtifactId(artifactId);
                artif.setVersion(version);

                System.out.println(groupId);
                System.out.println(artifactId);
                System.out.println(version);

                MavenResolver mrsv = new MavenResolver();

                mrsv.fetchFile(artif, artifactVersion);

                Resource file = storageService.loadAsResource("C:/Users/Berkay/Desktop/maven2/" + artif.getGroupIdPath() + "/" + artif.getArtifactId() + "/" + artif.getVersion() + "/" + artifactVersion);
                return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
 
                // ArrayList<String> dependencyList=new ArrayList<String>();
                // dependencyList.add(groupId);
                // dependencyList.add(artifactId);
                // dependencyList.add(version);
                // return dependencyList;
    }

    @GetMapping("/getArtifactsXml")
    @ResponseBody
    public String listArtifacts() {
        String sonuc = "bos";
        try {
            MavenResolver rsv = new MavenResolver();
            Artifact testif = new Artifact();
            testif.setGroupId("github.com.taconaut");
            testif.setArtifactId("jtmdb");
            testif.setVersion("1.9.17");
            rsv.IterateDependencies(testif);
            return sonuc;
        } catch (Exception e) {
            //TODO: handle exception
        }
        // model.addAttribute("files", storageService.loadAll().map(
        //         path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
        //                 "serveFile", path.getFileName().toString()).build().toString())
        //         .collect(Collectors.toList()));

        return sonuc;
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        storageService.store(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}