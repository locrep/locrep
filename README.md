
# LocRep OpenSource Artifact Management Project for DevOps

**LocRep official website** (http://locrep.com/) 

**LocRep test website** (http://dev.locrep.com/)

**LocRep test Agile and DevOps WebSite** (https://dev.azure.com/LocRep/)



## SmartyPaenter code herents

SmartyPants converts ASCII punctuation characters into "smart" typographic punctuation HTML entities. For example:

|                |Scope                         |Status|
|----------------|------------------------------|-----------------------------|
|*			|`maven`            |      Coding	|
|*          |`nuget`            |           	|
|*          |`npm`				|				|




## UML diagrams


```mermaid
sequenceDiagram
Vecihi Hurkus -x Cahit Arf:Pack-0.0.1-SNAPSHOT.jar
Note right of Cahit Arf: Release Stage

Vecihi Hurkus-->Cahit Arf: Dev Stage.
Locrep -x Nichola Tesla : Pack-1.0.1.jar
```

And this will produce a flow chart:

```mermaid
graph LR

A[Locrep Virtual Repo Node 1] -- maven,npm,etc... --> B((Remote Repositories))
Clients--> F5 
F5 --> A
A --> C(Local Repositories)
B --> D{Proxy Server}
C --> D





```



```

