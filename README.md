# Assignment 6 — Visitor Pattern

Course: ShP-2216 Software Design Patterns (OP 6B06102)
Astana IT University, group SE-2530
Java 17, Maven, JUnit 5

## What the program does

An in-memory file system made of `FileNode` and `DirectoryNode`. Three visitors work over the
same tree:

- `TotalSizeVisitor` — the sum of all file sizes in a subtree
- `SearchVisitor` — file paths that contain a name fragment and have size >= a minimum
- `StatisticsVisitor` — number of files, number of directories and the maximum depth

## Folder structure

```
src/main/java/filesystem/
├── Main.java                      - demo with its own sample tree
├── elements/
│   ├── Element.java               - common interface of the nodes
│   ├── FileNode.java
│   ├── DirectoryNode.java
│   └── TreeIntegrityException.java
└── visitors/
    ├── Visitor.java
    ├── TotalSizeVisitor.java
    ├── SearchVisitor.java
    └── StatisticsVisitor.java
src/test/java/filesystem/
├── TestTree.java                  - the tree used by the tests
├── ElementTest.java
├── TotalSizeVisitorTest.java
├── SearchVisitorTest.java
└── StatisticsVisitorTest.java
```

## How to run

Run the tests from the project root:

```bash
mvn test
```

Run the demo:

```bash
mvn compile
java -cp target/classes filesystem.Main
```
