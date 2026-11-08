package filesystem.elements;

import filesystem.visitors.Visitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DirectoryNode implements Element {
    private final String name;
    private final List<Element> children = new ArrayList<>();
    private DirectoryNode parent;

    public DirectoryNode(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public DirectoryNode getParent() {
        return parent;
    }

    void setParent(DirectoryNode parent) {
        this.parent = parent;
    }

    public List<Element> getChildren() {
        return Collections.unmodifiableList(children);
    }

    public void addFile(FileNode file) {
        checkCanBeAdded(file);
        children.add(file);
        file.setParent(this);
    }

    public void addDirectory(DirectoryNode directory) {
        checkCanBeAdded(directory);
        children.add(directory);
        directory.setParent(this);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    private void checkCanBeAdded(Element child) {
        checkDoesNotCreateCycle(child);
        checkHasNoParent(child);
        checkNameIsFree(child.getName());
    }

    private void checkDoesNotCreateCycle(Element child) {
        for (DirectoryNode current = this; current != null; current = current.getParent()) {
            if (current == child) {
                throw new TreeIntegrityException("cannot add " + child.getName() + " to " + name + ": it would create a cycle");
            }
        }
    }

    private void checkHasNoParent(Element child) {
        if (child.getParent() != null) {
            throw new TreeIntegrityException("cannot add " + child.getName() + " to " + name
                    + ": it already belongs to " + child.getParent().getName());
        }
    }

    private void checkNameIsFree(String childName) {
        for (Element child : children) {
            if (child.getName().equals(childName)) {
                throw new TreeIntegrityException("cannot add " + childName + " to " + name + ": the name is already taken");
            }
        }
    }
}
