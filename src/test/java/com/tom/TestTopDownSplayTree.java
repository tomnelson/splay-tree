package com.tom;

import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestTopDownSplayTree {

  private static final Logger log = LoggerFactory.getLogger(TestTopDownSplayTree.class);

  TopDownSplayTree<String> tree;

  String[] values = new String[] {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};

  @Before
  public void setup() {
    tree = TopDownSplayTree.create();
    Arrays.stream(values).forEach(tree::insert);
  }

  @Test
  public void testSmallContainer() {
    // make a Container and add all the nodes
    tree = TopDownSplayTree.create();
    tree.insert("A");
    System.err.println(tree.printTree("Appended A"));
    //    TopDownSplayTreePrinter.print(tree);
    tree.insert("B");
    System.err.println(tree.printTree("Appended B"));
    //    TopDownSplayTreePrinter.print(tree);
    tree.insert("C");
    System.err.println(tree.printTree("Appended C"));
    //    TopDownSplayTreePrinter.print(tree);
    tree.insert("D");
    System.err.println(tree.printTree("Appended D"));
    //    TopDownSplayTreePrinter.print(tree);
    tree.insert("E");
    System.err.println(tree.printTree("Appended E"));
    //    TopDownSplayTreePrinter.print(tree);
    tree.insert("F");
    System.err.println(tree.printTree("Appended F"));
    //    TopDownSplayTreePrinter.print(tree);

    tree.splay("D");
    System.err.println(tree.printTree("Splayed D"));
    //    TopDownSplayTreePrinter.print(tree);
    tree.splay("C");
    System.err.println(tree.printTree("Splayed C"));
    //    TopDownSplayTreePrinter.print(tree);
    tree.splay("B");
    System.err.println(tree.printTree("Splayed B"));
    //    TopDownSplayTreePrinter.print(tree);
    tree.splay("A");
    System.err.println(tree.printTree("Splayed A"));
    //    TopDownSplayTreePrinter.print(tree);

    tree.splay("C");
    System.err.println(tree.printTree("Splayed C"));

    tree.validate();
  }
}
