package com.tom;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestInsertionOrderSplayTreeWithSize {

  private static final Logger log =
      LoggerFactory.getLogger(TestInsertionOrderSplayTreeWithSize.class);

  InsertionOrderSplayTreeWithSize<String> tree;

  String[] values = new String[] {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};

  @Before
  public void setup() {
    //    tree = InsertionOrderSplayTreeWithSize.create();
    //    Arrays.stream(values).forEach(tree::append);
  }

  @Test
  public void testSmallContainer() {
    // make a Container and add all the nodes
    tree = InsertionOrderSplayTreeWithSize.create();
    tree.append("A");
    System.err.println(tree.printTree("Appended A"));
    //    InsertionOrderSplayTreeWithSizePrinter.print(tree);
    tree.append("B");
    System.err.println(tree.printTree("Appended B"));
    //    InsertionOrderSplayTreeWithSizePrinter.print(tree);
    tree.append("C");
    System.err.println(tree.printTree("Appended C"));
    //    InsertionOrderSplayTreeWithSizePrinter.print(tree);
    //    tree.append("D");
    //    System.err.println(tree.printTree("Appended D"));
    //    InsertionOrderSplayTreeWithSizePrinter.print(tree);
    tree.append("E");
    System.err.println(tree.printTree("Appended E"));
    //    InsertionOrderSplayTreeWithSizePrinter.print(tree);
    tree.append("F");
    System.err.println(tree.printTree("Appended F"));
    //    InsertionOrderSplayTreeWithSizePrinter.print(tree);

    tree.splay("D");
    System.err.println(tree.printTree("Splayed D"));
    //    InsertionOrderSplayTreeWithSizePrinter.print(tree);
    tree.splay("C");
    System.err.println(tree.printTree("Splayed C"));
    //    InsertionOrderSplayTreeWithSizePrinter.print(tree);
    tree.splay("B");
    System.err.println(tree.printTree("Splayed B"));
    //    InsertionOrderSplayTreeWithSizePrinter.print(tree);
    tree.splay("A");
    System.err.println(tree.printTree("Splayed A"));
    //    InsertionOrderSplayTreeWithSizePrinter.print(tree);

    tree.splay("C");
    System.err.println(tree.printTree("Splayed C"));
    //    InsertionOrderSplayTreeWithSizePrinter.print(tree);

    tree.append("D");
    System.err.println(tree.printTree("Appended D"));

    tree.append("H");
    //    tree.append("H");
    System.err.println(tree.printTree("Appended H"));

    tree.append("G");
    //    tree.append("H");
    System.err.println(tree.printTree("Appended G"));

    InsertionOrderSplayTreeWithSize.Node<String> found = tree.find(3);
    System.err.println(tree.printTree("found " + found.key + " at " + 3));

    found = tree.find(5);
    System.err.println(tree.printTree("found " + found.key + " at " + 5));

    for (int i = 0; i < tree.size; i++) {
      found = tree.find(i);
      System.err.println("found " + found.key + " at " + i);
    }

    tree.splay("C");
    System.err.println(tree.printTree("Splayed C"));

    for (int i = 0; i < tree.size; i++) {
      found = tree.find(i);
      System.err.println("found " + found.key + " at " + i);
    }

    tree.validate();
  }
}
