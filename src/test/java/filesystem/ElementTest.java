package filesystem;

import filesystem.elements.DirectoryNode;
import filesystem.elements.Element;
import filesystem.elements.FileNode;
import filesystem.elements.TreeIntegrityException;
import filesystem.visitors.Visitor;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ElementTest {

    private static class RecordingVisitor implements Visitor {
        private final List<String> calls = new ArrayList<>();

        @Override
        public void visit(FileNode file) {
            calls.add("file " + file.getName());
        }

        @Override
        public void visit(DirectoryNode directory) {
            calls.add("directory " + directory.getName());
        }
    }

    @Test
    void eachElementCallsItsOwnVisitMethod() {
        RecordingVisitor visitor = new RecordingVisitor();
        Element file = new FileNode("a.txt", 10);
        Element directory = new DirectoryNode("docs");

        file.accept(visitor);
        directory.accept(visitor);

        assertEquals(List.of("file a.txt", "directory docs"), visitor.calls);
    }

    @Test
    void negativeFileSizeIsRejected() {
        assertThrows(TreeIntegrityException.class, () -> new FileNode("broken.txt", -1));
    }

    @Test
    void duplicateSiblingNameIsRejected() {
        DirectoryNode docs = new DirectoryNode("docs");
        docs.addFile(new FileNode("notes.txt", 100));

        assertThrows(TreeIntegrityException.class, () -> docs.addFile(new FileNode("notes.txt", 5)));
        assertThrows(TreeIntegrityException.class, () -> docs.addDirectory(new DirectoryNode("notes.txt")));
        assertEquals(1, docs.getChildren().size());
    }

    @Test
    void elementCannotHaveTwoParents() {
        DirectoryNode first = new DirectoryNode("first");
        DirectoryNode second = new DirectoryNode("second");
        FileNode file = new FileNode("a.txt", 10);
        first.addFile(file);

        assertThrows(TreeIntegrityException.class, () -> second.addFile(file));
        assertEquals(0, second.getChildren().size());
    }

    @Test
    void cyclesAreRejected() {
        DirectoryNode top = new DirectoryNode("top");
        DirectoryNode middle = new DirectoryNode("middle");
        DirectoryNode bottom = new DirectoryNode("bottom");
        top.addDirectory(middle);
        middle.addDirectory(bottom);

        assertThrows(TreeIntegrityException.class, () -> bottom.addDirectory(top));
        assertThrows(TreeIntegrityException.class, () -> top.addDirectory(top));
        assertEquals(0, bottom.getChildren().size());
    }

    @Test
    void childrenCannotBeChangedFromOutside() {
        DirectoryNode docs = new DirectoryNode("docs");

        assertThrows(UnsupportedOperationException.class, () -> docs.getChildren().add(new FileNode("a.txt", 1)));
    }

    @Test
    void validNestingIsAllowed() {
        DirectoryNode level1 = new DirectoryNode("level1");
        DirectoryNode level2 = new DirectoryNode("level2");
        DirectoryNode level3 = new DirectoryNode("level3");
        FileNode file = new FileNode("deep.txt", 42);

        level1.addDirectory(level2);
        level2.addDirectory(level3);
        level3.addFile(file);

        assertSame(level3, file.getParent());
        assertSame(level2, level3.getParent());
        assertSame(level1, level2.getParent());
    }
}
