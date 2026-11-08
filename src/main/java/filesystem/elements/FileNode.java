package filesystem.elements;

import filesystem.visitors.Visitor;

public class FileNode implements Element {
    private final String name;
    private final long sizeInBytes;
    private DirectoryNode parent;

    public FileNode(String name, long sizeInBytes) {
        if (sizeInBytes < 0) {
            throw new TreeIntegrityException("file " + name + " cannot have a negative size: " + sizeInBytes);
        }
        this.name = name;
        this.sizeInBytes = sizeInBytes;
    }

    @Override
    public String getName() {
        return name;
    }

    public long getSizeInBytes() {
        return sizeInBytes;
    }

    @Override
    public DirectoryNode getParent() {
        return parent;
    }

    void setParent(DirectoryNode parent) {
        this.parent = parent;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
