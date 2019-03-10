package com.locrep.maven;

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import com.locrep.maven.Artifact;


import java.util.ArrayList;
import java.util.List;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.*;

public class MavenResolver
{
    public MavenResolver()
    {
        super();
    }

    private static String baseUrl = "https://repo.maven.apache.org";

    private static String filePath = "./mavenArtifacts/";

    public void IterateDependencies(Artifact artif)
    {
        List<String> cache = new ArrayList<String>();
        try {
            if(cache.indexOf(artif.getArtifactId()+artif.getVersion()) == -1)
            {
                System.out.println(artif.getArtifactId()+artif.getVersion());
                cache.add(artif.getArtifactId()+artif.getVersion());
            }
            else
            {
                return;
            }
            String relurl = "/maven2/" + artif.getGroupIdPath() + "/" + artif.getArtifactId() + "/" + artif.getVersion() + "/" + artif.getArtifactId() + "-" + artif.getVersion() + ".pom";
            this.fetchFiles(artif);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            org.w3c.dom.Document doc = db.parse(new FileInputStream(filePath + relurl));
            NodeList nList = doc.getElementsByTagName("dependency");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    org.w3c.dom.Element eElement = (org.w3c.dom.Element) nNode;
                    Artifact tempArtifact = new Artifact();
                    tempArtifact.setGroupId(eElement.getElementsByTagName("groupId").item(0).getTextContent());
                    tempArtifact.setArtifactId(eElement.getElementsByTagName("artifactId").item(0).getTextContent());
                    tempArtifact.setVersion(eElement.getElementsByTagName("version").item(0).getTextContent());
                    this.IterateDependencies(tempArtifact);
                }
             }
        } catch (Exception e) {
           System.out.println("Exception:" + e + "Artifact: " + artif.getArtifactId()+artif.getVersion());
        }

    }

    public void fetchFile(Artifact artif, String reqfile)
    {
        try {
            new File(filePath + "/maven2/" + artif.getGroupIdPath() + "/" + artif.getArtifactId() + "/" + artif.getVersion() + "/").mkdirs();
            File[] files = new File(filePath + "/maven2/" + artif.getGroupIdPath() + "/" + artif.getArtifactId() + "/" + artif.getVersion()).listFiles();
            //If this pathname does not denote a directory, then listFiles() returns null. 
            for (File file : files) {
                if (file.isFile()) {
                    if(file.getName().equals(reqfile))
                    {
                        return;
                    }
                }
            }
            String relurl = "/maven2/" + artif.getGroupIdPath() + "/" + artif.getArtifactId() + "/" + artif.getVersion() + "/" + reqfile;
            Files.copy(new URL(baseUrl + relurl).openStream(), Paths.get(filePath + "/maven2/" + artif.getGroupIdPath() + "/" + artif.getArtifactId() + "/" + artif.getVersion() + "/" + reqfile));
        } catch (Exception e) {
            //TODO: handle exception
        }
    }

    public void fetchFiles(Artifact artif)
    {
        try {
            Document doc = Jsoup.connect(baseUrl + "/maven2/" + artif.getGroupIdPath() + "/" + artif.getArtifactId() + "/" + artif.getVersion() + "/").get();
            Elements links = doc.select("a[href]");
            for (Element link : links) {
                if(link.attr("href").length() > 3)
                {
                    try {
                                                new File(filePath + "/maven2/" + artif.getGroupIdPath() + "/" + artif.getArtifactId() + "/" + artif.getVersion() + "/").mkdirs();
                        File[] files = new File(filePath + "/maven2/" + artif.getGroupIdPath() + "/" + artif.getArtifactId() + "/" + artif.getVersion()).listFiles();
                        //If this pathname does not denote a directory, then listFiles() returns null. 
                        for (File file : files) {
                            if (file.isFile()) {
                                if(file.getName().equals(link.attr("href")))
                                {
                                    return;
                                }
                            }
                        }
                        Files.copy(new URL(link.attr("abs:href")).openStream(), Paths.get(filePath + "/maven2/" + artif.getGroupIdPath() + "/" + artif.getArtifactId() + "/" + artif.getVersion() + "/" + link.attr("href")));
                    } catch (Exception e) {
                        //TODO: handle exception
                    }

                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }
//#endregion


}