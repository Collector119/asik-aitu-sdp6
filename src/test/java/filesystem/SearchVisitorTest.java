package filesystem;

import filesystem.elements.DirectoryNode;
import filesystem.elements.FileNode;
import filesystem.visitors.SearchVisitor;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SearchVisitorTest {

    @Test
    void bothConditionsMustMatch() {
        SearchVisitor visitor = new SearchVisitor(".java", 1000);

        assertEquals(List.of("src/Main.java"), visitor.findMatchingPaths(TestTree.build()));
    }

    @Test
    void minimumSizeIsInclusive() {
        SearchVisitor visitor = new SearchVisitor(".java", 1200);

        assertEquals(List.of("src/Main.java"), visitor.findMatchingPaths(TestTree.build()));
    }

    @Test
    void pathsAreRelativeToRootAndSorted() {
        SearchVisitor visitor = new SearchVisitor("", 0);

        List<String> expected = List.of(
                "README.md",
                "docs/notes.txt",
                "docs/report.pdf",
                "src/Main.java",
                "src/utils/Parser.java");
        assertEquals(expected, visitor.findMatchingPaths(TestTree.build()));
    }

    @Test
    void pathsStartFromTheChosenRoot() {
        DirectoryNode src = new DirectoryNode("src");
        DirectoryNode utils = new DirectoryNode("utils");
        src.addDirectory(utils);
        utils.addFile(new FileNode("Parser.java", 800));
        SearchVisitor visitor = new SearchVisitor(".java", 0);

        assertEquals(List.of("utils/Parser.java"), visitor.findMatchingPaths(src));
    }

    @Test
    void noMatchAndEmptyDirectoryGiveEmptyList() {
        SearchVisitor visitor = new SearchVisitor(".exe", 0);

        assertEquals(List.of(), visitor.findMatchingPaths(TestTree.build()));
        assertEquals(List.of(), visitor.findMatchingPaths(new DirectoryNode("empty")));
    }

    @Test
    void repeatedRunsDoNotKeepOldResults() {
        SearchVisitor visitor = new SearchVisitor(".java", 0);
        DirectoryNode project = TestTree.build();

        assertEquals(2, visitor.findMatchingPaths(project).size());
        assertEquals(2, visitor.findMatchingPaths(project).size());
        assertEquals(List.of(), visitor.findMatchingPaths(new DirectoryNode("empty")));
    }
}
