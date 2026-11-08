package filesystem;

import filesystem.elements.DirectoryNode;
import filesystem.elements.FileNode;
import filesystem.elements.TreeIntegrityException;
import filesystem.visitors.SearchVisitor;
import filesystem.visitors.TotalSizeVisitor;

public class Main {
    public static void main(String[] args) {
        DirectoryNode university = new DirectoryNode("university");
        DirectoryNode sdp = new DirectoryNode("sdp");
        DirectoryNode assignment6 = new DirectoryNode("assignment6");
        DirectoryNode photos = new DirectoryNode("photos");
        DirectoryNode drafts = new DirectoryNode("drafts");

        university.addFile(new FileNode("schedule.pdf", 120000));
        university.addDirectory(sdp);
        university.addDirectory(photos);
        university.addDirectory(drafts);
        sdp.addFile(new FileNode("lecture9.pdf", 2400000));
        sdp.addFile(new FileNode("notes.txt", 900));
        sdp.addDirectory(assignment6);
        assignment6.addFile(new FileNode("Visitor.java", 450));
        assignment6.addFile(new FileNode("Main.java", 3100));
        assignment6.addFile(new FileNode("Scratch.java", 120));
        photos.addFile(new FileNode("group.jpg", 2800000));
        photos.addFile(new FileNode("campus.jpg", 3500000));

        printTitle("1. Total size");
        TotalSizeVisitor totalSizeVisitor = new TotalSizeVisitor();
        System.out.println("university: " + totalSizeVisitor.calculateTotalSize(university) + " bytes");
        System.out.println("sdp: " + totalSizeVisitor.calculateTotalSize(sdp) + " bytes");
        System.out.println("drafts: " + totalSizeVisitor.calculateTotalSize(drafts) + " bytes");

        String nameFragment = ".java";
        long minimumSizeInBytes = 400;
        printTitle("2. Search: name contains \"" + nameFragment + "\" and size >= " + minimumSizeInBytes + " bytes");
        SearchVisitor searchVisitor = new SearchVisitor(nameFragment, minimumSizeInBytes);
        System.out.println("from university: " + searchVisitor.findMatchingPaths(university));
        System.out.println("from sdp: " + searchVisitor.findMatchingPaths(sdp));
        System.out.println("from drafts: " + searchVisitor.findMatchingPaths(drafts));

        printTitle("3. Invalid changes");
        try {
            sdp.addFile(new FileNode("notes.txt", 10));
        } catch (TreeIntegrityException exception) {
            System.out.println("rejected: " + exception.getMessage());
        }
        try {
            assignment6.addDirectory(university);
        } catch (TreeIntegrityException exception) {
            System.out.println("rejected: " + exception.getMessage());
        }
    }

    private static void printTitle(String title) {
        System.out.println();
        System.out.println("=== " + title + " ===");
    }
}
