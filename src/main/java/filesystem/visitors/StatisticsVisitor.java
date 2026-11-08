package filesystem.visitors;

import filesystem.elements.DirectoryNode;
import filesystem.elements.Element;
import filesystem.elements.FileNode;

public class StatisticsVisitor implements Visitor {
    private int fileCount;
    private int directoryCount;
    private int maxDepth;
    private int currentDepth;

    public void collectStatistics(Element root) {
        fileCount = 0;
        directoryCount = 0;
        maxDepth = 0;
        currentDepth = 0;
        root.accept(this);
    }

    public int getFileCount() {
        return fileCount;
    }

    public int getDirectoryCount() {
        return directoryCount;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    @Override
    public void visit(FileNode file) {
        fileCount++;
        updateMaxDepth();
    }

    @Override
    public void visit(DirectoryNode directory) {
        directoryCount++;
        updateMaxDepth();
        currentDepth++;
        for (Element child : directory.getChildren()) {
            child.accept(this);
        }
        currentDepth--;
    }

    private void updateMaxDepth() {
        if (currentDepth > maxDepth) {
            maxDepth = currentDepth;
        }
    }
}
