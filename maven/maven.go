package maven

import (
	"errors"
	"fmt"
	"io"
	"net/http"
	"os"
	"strings"
)

// TODO: Add comment to Artifact struct

type Artifact struct {
	GroupID    string
	ArtifactID string
	Version    string
}

func UrlResolve(param string) (*Artifact, error) {
	// TODO: Optimize the function (use variables for indexes, dont recalculate)
	var slashLastIndex = strings.LastIndex(param, "/")
	if slashLastIndex == -1 {
		return nil, errors.New("slashIndex failed")
	}
	var fileName = param[slashLastIndex+1:]
	var dashIndex = strings.LastIndex(fileName, "-")
	if dashIndex == -1 {
		return nil, errors.New("dashIndex failed")
	}
	var dotIndex = strings.LastIndex(fileName, ".")
	if dotIndex == -1 {
		return nil, errors.New("dotIndex failed")
	}
	res := new(Artifact)
	res.Version = fileName[dashIndex+1 : dotIndex]
	res.ArtifactID = fileName[:dashIndex]
	res.GroupID = strings.ReplaceAll(param[:strings.Index(param, res.ArtifactID)-1], "/", ".")
	return res, nil
}

func ListFiles(pth string) ([]string, error) {
	var files []string
	f, err := os.Open(pth)
	if err != nil {
		return nil, err
	}
	defer f.Close()
	fileInfo, err := f.Readdir(-1)
	if err != nil {
		return nil, err
	}
	for _, file := range fileInfo {
		files = append(files, file.Name())
	}
	return files, nil
}

func FindFile(pth string) (bool, error) {
	files, err := ListFiles(pth[:strings.LastIndex(pth, "/")])
	if err != nil {
		return false, err
	}
	for _, file := range files {
		if file == pth[strings.LastIndex(pth, "/")+1:] {
			return true, nil
		}
	}
	return false, nil
}

func FetchArtifact(inpArt *Artifact, reqFile string) (bool, error) {
	var savePath, apacheRepo = "C:/Users/Kuzgun/Desktop/lrdow", "https://repo.maven.apache.org"
	var filePath = fmt.Sprintf("%s/maven2/%s/%s/%s/%s", savePath, strings.ReplaceAll(inpArt.GroupID, ".", "/"), inpArt.ArtifactID, inpArt.Version, reqFile)
	os.MkdirAll(fmt.Sprintf("%s/maven2/%s/%s/%s/", savePath, strings.ReplaceAll(inpArt.GroupID, ".", "/"), inpArt.ArtifactID, inpArt.Version), 0755)
	res, err := FindFile(filePath)
	if err != nil {
		return false, err
	}
	if res {
		return true, nil
	}

	out, err := os.Create(filePath)
	if err != nil {
		return false, err
	}
	defer out.Close()

	resp, err := http.Get(fmt.Sprintf("%s/maven2/%s/%s/%s/%s", apacheRepo, strings.ReplaceAll(inpArt.GroupID, ".", "/"), inpArt.ArtifactID, inpArt.Version, reqFile))
	if err != nil {
		return false, err
	}
	defer resp.Body.Close()

	_, err = io.Copy(out, resp.Body)
	if err != nil {
		return false, err
	}

	return true, nil
}
