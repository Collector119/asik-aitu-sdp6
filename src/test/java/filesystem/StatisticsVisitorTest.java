package filesystem;

import filesystem.elements.DirectoryNode;
import filesystem.elements.FileNode;
import filesystem.visitors.StatisticsVisitor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StatisticsVisitorTest {

    @Test
    void countsAndDepthAreCorrectForNestedTree() {
        StatisticsVisitor visitor = new StatisticsVisitor();

        visitor.collectStatistics(TestTree.build());

        assertEquals(5, visitor.getFileCount());
        assertEquals(5, visitor.getDirectoryCount());
        assertEquals(3, visitor.getMaxDepth());
    }

    @Test
    void emptyDirectoryCountsItself() {
        StatisticsVisitor visitor = new StatisticsVisitor();

        visitor.collectStatistics(new DirectoryNode("empty"));

        assertEquals(0, visitor.getFileCount());
        assertEquals(1, visitor.getDirectoryCount());
        assertEquals(0, visitor.getMaxDepth());
    }

    @Test
    void singleFileHasDepthZero() {
        StatisticsVisitor visitor = new StatisticsVisitor();

        visitor.collectStatistics(new FileNode("a.txt", 10));

        assertEquals(1, visitor.getFileCount());
        assertEquals(0, visitor.getDirectoryCount());
        assertEquals(0, visitor.getMaxDepth());
    }

    @Test
    void repeatedRunsDoNotAddUp() {
        StatisticsVisitor visitor = new StatisticsVisitor();
        DirectoryNode project = TestTree.build();

        visitor.collectStatistics(project);
        visitor.collectStatistics(project);
        assertEquals(5, visitor.getFileCount());
        assertEquals(5, visitor.getDirectoryCount());

        visitor.collectStatistics(new DirectoryNode("empty"));
        assertEquals(0, visitor.getFileCount());
        assertEquals(1, visitor.getDirectoryCount());
        assertEquals(0, visitor.getMaxDepth());
    }
}
