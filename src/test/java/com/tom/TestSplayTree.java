package com.tom;

import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestSplayTree {

  private static final Logger log = LoggerFactory.getLogger(TestSplayTree.class);

  SplayTree<String> tree;

  String[] values = new String[] {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};

  @Before
  public void setup() {
    tree = SplayTree.create();
    Arrays.stream(values).forEach(tree::insert);
  }

  @Test
  public void testSmallContainer() {
    // make some nodes
    SplayTree.Node<String> nodeA = new SplayTree.Node("A");
    SplayTree.Node<String> nodeB = new SplayTree.Node("B");
    SplayTree.Node<String> nodeC = new SplayTree.Node("C");
    SplayTree.Node<String> nodeD = new SplayTree.Node("D");
    SplayTree.Node<String> nodeE = new SplayTree.Node("E");
    SplayTree.Node<String> nodeF = new SplayTree.Node("F");
    // make a Container and add all the nodes
    tree = SplayTree.create();
    tree.insert("A");
    System.err.println(tree.printTree("Appended A"));
    //    SplayTreePrinter.print(tree);
    tree.insert("B");
    System.err.println(tree.printTree("Appended B"));
    //    SplayTreePrinter.print(tree);
    tree.insert("C");
    System.err.println(tree.printTree("Appended C"));
    //    SplayTreePrinter.print(tree);
    tree.insert("D");
    System.err.println(tree.printTree("Appended D"));
    //    SplayTreePrinter.print(tree);
    tree.insert("E");
    System.err.println(tree.printTree("Appended E"));
    //    SplayTreePrinter.print(tree);
    tree.insert("F");
    System.err.println(tree.printTree("Appended F"));
    //    SplayTreePrinter.print(tree);

    tree.splay("D");
    System.err.println(tree.printTree("Splayed D"));
    //    SplayTreePrinter.print(tree);
    tree.splay("C");
    System.err.println(tree.printTree("Splayed C"));
    //    SplayTreePrinter.print(tree);
    tree.splay("B");
    System.err.println(tree.printTree("Splayed B"));
    //    SplayTreePrinter.print(tree);
    tree.splay("A");
    System.err.println(tree.printTree("Splayed A"));
    //    SplayTreePrinter.print(tree);

    tree.splay("C");
    System.err.println(tree.printTree("Splayed C"));
    //    SplayTreePrinter.print(tree);
  }
}
