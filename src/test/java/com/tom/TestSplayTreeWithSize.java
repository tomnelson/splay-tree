package com.tom;

import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestSplayTreeWithSize {

  private static final Logger log = LoggerFactory.getLogger(TestSplayTreeWithSize.class);

  SplayTreeWithSize<String> tree;

  String[] values = new String[] {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};

  @Before
  public void setup() {
    tree = SplayTreeWithSize.create();
    Arrays.stream(values).forEach(tree::insert);
  }

  @Test
  public void testSmallContainer() {
    // make some nodes
    SplayTreeWithSize.Node<String> nodeA = new SplayTreeWithSize.Node("A");
    SplayTreeWithSize.Node<String> nodeB = new SplayTreeWithSize.Node("B");
    SplayTreeWithSize.Node<String> nodeC = new SplayTreeWithSize.Node("C");
    SplayTreeWithSize.Node<String> nodeD = new SplayTreeWithSize.Node("D");
    SplayTreeWithSize.Node<String> nodeE = new SplayTreeWithSize.Node("E");
    SplayTreeWithSize.Node<String> nodeF = new SplayTreeWithSize.Node("F");
    // make a Container and add all the nodes
    tree = SplayTreeWithSize.create();
    tree.insert("A");
    System.err.println(tree.printTree("Appended A"));
    //    SplayTreeWithSizePrinter.print(tree);
    tree.insert("B");
    System.err.println(tree.printTree("Appended B"));
    //    SplayTreeWithSizePrinter.print(tree);
    tree.insert("C");
    System.err.println(tree.printTree("Appended C"));
    //    SplayTreeWithSizePrinter.print(tree);
    //    tree.insert("D");
    //    System.err.println(tree.printTree("Appended D"));
    //    SplayTreeWithSizePrinter.print(tree);
    tree.insert("E");
    System.err.println(tree.printTree("Appended E"));
    //    SplayTreeWithSizePrinter.print(tree);
    tree.insert("F");
    System.err.println(tree.printTree("Appended F"));
    //    SplayTreeWithSizePrinter.print(tree);

    tree.splay("D");
    System.err.println(tree.printTree("Splayed D"));
    //    SplayTreeWithSizePrinter.print(tree);
    tree.splay("C");
    System.err.println(tree.printTree("Splayed C"));
    //    SplayTreeWithSizePrinter.print(tree);
    tree.splay("B");
    System.err.println(tree.printTree("Splayed B"));
    //    SplayTreeWithSizePrinter.print(tree);
    tree.splay("A");
    System.err.println(tree.printTree("Splayed A"));
    //    SplayTreeWithSizePrinter.print(tree);

    tree.splay("C");
    System.err.println(tree.printTree("Splayed C"));
    //    SplayTreeWithSizePrinter.print(tree);

    tree.insert("D");
    System.err.println(tree.printTree("Inserted D"));

    tree.append("H");
    System.err.println(tree.printTree("Appended D"));
  }
}
