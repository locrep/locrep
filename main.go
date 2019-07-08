package main

import (
	"fmt"
	"log"
	"strings"

	"github.com/LocRep/LocRep/tree/master/maven/maven.go"
	"github.com/gin-gonic/gin"
)

func requestHandle(c *gin.Context) {
	var artif *maven.Artifact
	artif, err := maven.UrlResolve(c.Param("query")[1:])
	if err != nil {
		c.JSON(200, gin.H{"message": "ERROR!"})
	}

	var slashLastIndex = strings.LastIndex(c.Param("query"), "/")
	if slashLastIndex == -1 {
		c.JSON(200, gin.H{"message": "ERROR!"})
	}

	var savePath = "C:/Users/Kuzgun/Desktop/lrdow"
	var reqFile = c.Param("query")[slashLastIndex+1:]
	res, err := maven.FetchArtifact(artif, reqFile)
	log.Println(err)
	fmt.Println(fmt.Sprintf("Fetch result: %b", res))
	fmt.Println(fmt.Sprintf("%s/maven2/%s/%s/%s/%s", savePath, strings.ReplaceAll(artif.GroupID, ".", "/"), artif.ArtifactID, artif.Version, reqFile))
	c.File(fmt.Sprintf("%s/maven2/%s/%s/%s/%s", savePath, strings.ReplaceAll(artif.GroupID, ".", "/"), artif.ArtifactID, artif.Version, reqFile))
}

func main() {
	r := gin.Default()
	r.GET("/maven2/*query", requestHandle)
	r.Run() // listen and serve on 0.0.0.0:8080
}
