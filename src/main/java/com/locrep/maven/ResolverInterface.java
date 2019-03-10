package com.locrep.maven;

import com.locrep.maven.Artifact;
import java.util.List;
import org.w3c.dom.Document;

public interface ResolverInterface
{
    public List<Artifact> resolveArtifacts();
    public Document fetchArtifacts();
}