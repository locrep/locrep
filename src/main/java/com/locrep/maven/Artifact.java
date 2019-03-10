package com.locrep.maven;

public class Artifact
{
    private String groupId;
    private String artifactId;
    private String version;

    public Artifact()
    {
        super();
    }

    public String getGroupId()
    {
        return this.groupId;
    }

    public void setGroupId(String inpGroupId)
    {
        this.groupId = inpGroupId;
    }

    public String getGroupIdPath()
    {
        return this.groupId.replace(".", "/");
    }

    public String getArtifactId()
    {
        return this.artifactId;
    }
    
    public void setArtifactId(String inpArtifactId)
    {
        this.artifactId = inpArtifactId;
    }

    public String getVersion()
    {
        return this.version;
    }

    public void setVersion(String inpVersion)
    {
        this.version = inpVersion;
    }

    @Override
    public String toString() { 
        return "Artifact{groupId:" + this.getGroupId() + " artifactId:" + this.getArtifactId() + " version:" + this.getVersion() + "}";
    } 

}