package filesystem;

import filesystem.elements.DirectoryNode;
import filesystem.elements.FileNode;

public class TestTree {
    public static DirectoryNode build() {
        DirectoryNode project = new DirectoryNode("project");
        DirectoryNode src = new DirectoryNode("src");
        DirectoryNode utils = new DirectoryNode("utils");
        DirectoryNode docs = new DirectoryNode("docs");

        project.addDirectory(src);
        project.addDirectory(docs);
        project.addFile(new FileNode("README.md", 300));
        project.addDirectory(new DirectoryNode("empty"));
        src.addFile(new FileNode("Main.java", 1200));
        src.addDirectory(utils);
        utils.addFile(new FileNode("Parser.java", 800));
        docs.addFile(new FileNode("report.pdf", 50000));
        docs.addFile(new FileNode("notes.txt", 100));
        return project;
    }
}
