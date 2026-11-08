package filesystem.visitors;

import filesystem.elements.DirectoryNode;
import filesystem.elements.Element;
import filesystem.elements.FileNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchVisitor implements Visitor {
    private final String nameFragment;
    private final long minimumSizeInBytes;
    private List<String> matchingPaths;
    private String currentPath;

    public SearchVisitor(String nameFragment, long minimumSizeInBytes) {
        this.nameFragment = nameFragment;
        this.minimumSizeInBytes = minimumSizeInBytes;
    }

    public List<String> findMatchingPaths(DirectoryNode root) {
        matchingPaths = new ArrayList<>();
        currentPath = "";
        root.accept(this);
        Collections.sort(matchingPaths);
        return matchingPaths;
    }

    @Override
    public void visit(FileNode file) {
        if (matchesBothConditions(file)) {
            matchingPaths.add(currentPath);
        }
    }

    @Override
    public void visit(DirectoryNode directory) {
        String directoryPath = currentPath;
        for (Element child : directory.getChildren()) {
            currentPath = joinPath(directoryPath, child.getName());
            child.accept(this);
        }
    }

    private boolean matchesBothConditions(FileNode file) {
        return file.getName().contains(nameFragment) && file.getSizeInBytes() >= minimumSizeInBytes;
    }

    private String joinPath(String directoryPath, String childName) {
        if (directoryPath.isEmpty()) {
            return childName;
        }
        return directoryPath + "/" + childName;
    }
}
