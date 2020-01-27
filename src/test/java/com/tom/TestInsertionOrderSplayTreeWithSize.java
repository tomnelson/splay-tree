package com.tom;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;
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

    for (int i = 0; i < tree.size(); i++) {
      found = tree.find(i);
      System.err.println("found " + found.key + " at " + i);
    }

    tree.splay("C");
    System.err.println(tree.printTree("Splayed C"));

    for (int i = 0; i < tree.size(); i++) {
      found = tree.find(i);
      System.err.println("found " + found.key + " at " + i);
    }

    tree.validate();
  }

  @Test
  public void testSplitToPair() {
    tree = InsertionOrderSplayTreeWithSize.create();
    for (char c = 'A'; c <= 'Z'; c++) {
      tree.append("" + c);
    }
    System.err.println(tree.printTree());

    Pair<InsertionOrderSplayTreeWithSize<String>> pair =
        InsertionOrderSplayTreeWithSize.split(tree, "M");
    System.err.println(pair.first.printTree());

    pair.first.validate();

    System.err.println(pair.second.printTree());

    pair.second.validate();
  }

  @Test
  public void testSplit() {
    tree = InsertionOrderSplayTreeWithSize.create();
    for (char c = 'A'; c <= 'Z'; c++) {
      tree.append("" + c);
    }
    System.err.println(tree.printTree("starting tree"));

    InsertionOrderSplayTreeWithSize<String> newTree = tree.split("M");
    System.err.println(newTree.printTree("split off tree"));

    newTree.validate();

    System.err.println(tree.printTree("tree after split"));
    tree.validate();

    tree.join(newTree);
    System.err.println(tree.printTree("Joined Tree"));
    tree.validate();
  }

  /**
   * create a tree of 26 upper-case characters randomly split and join the tree at different
   * locations validate the split and re-joined tree
   */
  @Test
  public void testSplits() {

    tree = InsertionOrderSplayTreeWithSize.create();
    for (char c = 'A'; c <= 'Z'; c++) {
      tree.append("" + c);
    }
    System.err.println(tree.printTree());

    for (int splitPoint : shuffledInts(0, tree.size())) {
      InsertionOrderSplayTreeWithSize<String> splitter = tree.split(splitPoint);
      System.err.println("split at " + splitPoint);
      System.err.println("newTree size: " + splitter.size());
      System.err.println(splitter.printTree("split off tree"));

      splitter.validate();
      System.err.println("tree size: " + tree.size());
      System.err.println(tree.printTree("tree after split"));
      System.err.println(
          "__________________________________________________________________________");

      tree.validate();

      tree.join(splitter);
      System.err.println(tree.printTree("Joined Tree"));
      tree.validate();
    }
  }

  // Implementing Fisher–Yates shuffle
  static void shuffleArray(int[] ar) {
    Random rnd = ThreadLocalRandom.current();
    for (int i = ar.length - 1; i > 0; i--) {
      int index = rnd.nextInt(i + 1);
      // Simple swap
      int a = ar[index];
      ar[index] = ar[i];
      ar[i] = a;
    }
  }

  static int[] shuffledInts(int from, int to) {
    int[] array = IntStream.rangeClosed(from, to).toArray();
    shuffleArray(array);
    return array;
  }
}
