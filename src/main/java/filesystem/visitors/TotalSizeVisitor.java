package filesystem.visitors;

import filesystem.elements.DirectoryNode;
import filesystem.elements.Element;
import filesystem.elements.FileNode;

public class TotalSizeVisitor implements Visitor {
    private long totalSize;

    public long calculateTotalSize(Element root) {
        totalSize = 0;
        root.accept(this);
        return totalSize;
    }

    @Override
    public void visit(FileNode file) {
        totalSize += file.getSizeInBytes();
    }

    @Override
    public void visit(DirectoryNode directory) {
        for (Element child : directory.getChildren()) {
            child.accept(this);
        }
    }
}
