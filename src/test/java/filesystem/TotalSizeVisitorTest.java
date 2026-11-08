package filesystem;

import filesystem.elements.DirectoryNode;
import filesystem.visitors.TotalSizeVisitor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TotalSizeVisitorTest {

    @Test
    void sizeIsSummedOverAllNestingLevels() {
        TotalSizeVisitor visitor = new TotalSizeVisitor();

        assertEquals(52400, visitor.calculateTotalSize(TestTree.build()));
    }

    @Test
    void emptyDirectoryHasZeroSize() {
        TotalSizeVisitor visitor = new TotalSizeVisitor();

        assertEquals(0, visitor.calculateTotalSize(new DirectoryNode("empty")));
    }

    @Test
    void repeatedRunsDoNotAddUp() {
        TotalSizeVisitor visitor = new TotalSizeVisitor();
        DirectoryNode project = TestTree.build();

        assertEquals(52400, visitor.calculateTotalSize(project));
        assertEquals(52400, visitor.calculateTotalSize(project));
        assertEquals(0, visitor.calculateTotalSize(new DirectoryNode("empty")));
    }
}
