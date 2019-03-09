package com.locrep.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;

@RestController
@RequestMapping("/")
public class FileController {

    // @RequestMapping(value = "/sid", method = RequestMethod.GET,
    // produces = MediaType.IMAGE_JPEG_VALUE)
    //
    // public void getImage(HttpServletResponse response) throws IOException {
    //
    // var imgFile = new ClassPathResource("image/sid.jpg");
    //
    // response.setContentType(MediaType.IMAGE_JPEG_VALUE);
    // StreamUtils.copy(imgFile.getInputStream(), response.getOutputStream());
    // }

    // @RequestMapping(value = "/sid", method = RequestMethod.GET,
    // produces = MediaType.IMAGE_JPEG_VALUE)
    // public ResponseEntity<byte[]> getImage() throws IOException {
    //
    // ClassPathResource imgFile = new ClassPathResource("image/sid.jpg");
    // byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());
    //
    // return ResponseEntity
    // .ok()
    // .contentType(MediaType.IMAGE_JPEG)
    // .body(bytes);
    // }

    @RequestMapping(value = "/id")
    String getIdByValue() {
        System.out.println("ID is ");
        return "Get ID from query string of URL with value element";
    }

    @ResponseBody
    @RequestMapping(value = "/maven2/{groupId}/{artifactId}/{version}")
    public String getId(@PathVariable("groupId") String groupId, @PathVariable("artifactId") String artifactId,
            @PathVariable("version") String version) {

        return groupId + "." + artifactId + "." + version;
    }
}