package filesystem.visitors;

import filesystem.elements.DirectoryNode;
import filesystem.elements.FileNode;

public interface Visitor {
    void visit(FileNode file);

    void visit(DirectoryNode directory);
}
