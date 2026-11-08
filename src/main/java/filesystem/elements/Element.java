package filesystem.elements;

import filesystem.visitors.Visitor;

public interface Element {
    String getName();

    DirectoryNode getParent();

    void accept(Visitor visitor);
}
