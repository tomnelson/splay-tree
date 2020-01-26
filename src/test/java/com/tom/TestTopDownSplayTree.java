package com.tom;

import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestTopDownSplayTree {

  private static final Logger log = LoggerFactory.getLogger(TestTopDownSplayTree.class);

  TopDownSplayTree<String> tree;

  //  static class TaggedSegment<V> extends Segment<V> {
  //    final V tag;
  //
  //    static <V> TaggedSegment<V> of(PVertex p, QVertex q, V tag) {
  //      return new TaggedSegment<>(p, q, tag);
  //    }
  //
  //    protected TaggedSegment(PVertex p, QVertex q, V tag) {
  //      super(p, q);
  //      this.tag = tag;
  //    }
  //
  //    public String toString() {
  //      return tag.toString() + ":" + count();
  //    }
  //  }

  String[] values = new String[] {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
  ////  PVertex[] pVertices =
  ////      new PVertex[] {
  ////        PVertex.of(),
  ////        PVertex.of(),
  ////        PVertex.of(),
  ////        PVertex.of(),
  ////        PVertex.of(),
  ////        PVertex.of(),
  ////        PVertex.of(),
  ////        PVertex.of(),
  ////        PVertex.of(),
  ////        PVertex.of()
  ////      };
  ////  QVertex[] qVertices =
  ////      new QVertex[] {
  ////        QVertex.of(),
  ////        QVertex.of(),
  ////        QVertex.of(),
  ////        QVertex.of(),
  ////        QVertex.of(),
  ////        QVertex.of(),
  ////        QVertex.of(),
  ////        QVertex.of(),
  ////        QVertex.of(),
  ////        QVertex.of(),
  ////      };
  //  Segment[] nodes =
  //      new Segment[] {
  //        TaggedSegment.of(pVertices[0], qVertices[0], values[0]),
  //        TaggedSegment.of(pVertices[1], qVertices[1], values[1]),
  //        TaggedSegment.of(pVertices[2], qVertices[2], values[2]),
  //        TaggedSegment.of(pVertices[3], qVertices[3], values[3]),
  //        TaggedSegment.of(pVertices[4], qVertices[4], values[4]),
  //        TaggedSegment.of(pVertices[5], qVertices[5], values[5]),
  //        TaggedSegment.of(pVertices[6], qVertices[6], values[6]),
  //        TaggedSegment.of(pVertices[7], qVertices[7], values[7]),
  //        TaggedSegment.of(pVertices[8], qVertices[8], values[8]),
  //        TaggedSegment.of(pVertices[9], qVertices[9], values[9])
  //      };

  @Before
  public void setup() {
    tree = TopDownSplayTree.create();
    Arrays.stream(values).forEach(tree::insert);
  }

  //  private <V> TaggedSegment<V> makeSegment(V tag) {
  //    PVertex<V> pVertex = PVertex.of();
  //    QVertex<V> qVertex = QVertex.of();
  //    return TaggedSegment.of(pVertex, qVertex, tag);
  //  }

  @Test
  public void testSmallContainer() {
    // make some nodes
    TopDownSplayTree.Node<String> nodeA = new TopDownSplayTree.Node("A");
    TopDownSplayTree.Node<String> nodeB = new TopDownSplayTree.Node("B");
    TopDownSplayTree.Node<String> nodeC = new TopDownSplayTree.Node("C");
    TopDownSplayTree.Node<String> nodeD = new TopDownSplayTree.Node("D");
    TopDownSplayTree.Node<String> nodeE = new TopDownSplayTree.Node("E");
    TopDownSplayTree.Node<String> nodeF = new TopDownSplayTree.Node("F");
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
    //    TopDownSplayTreePrinter.print(tree);

    //    Assert.assertEquals(0, tree.pos(nodeA));
    //    Assert.assertEquals(1, tree.pos(nodeB));
    //    Assert.assertEquals(2, tree.pos(nodeC));
    //    Assert.assertEquals(3, tree.pos(nodeD));
    //    Assert.assertEquals(3, tree.pos(nodeD));
    //
    //    // show the pos for everything
    //    log.info("pos for {} is {}", nodeA, tree.pos(nodeA));
    //    log.info("pos for {} is {}", nodeB, tree.pos(nodeB));
    //    log.info("pos for {} is {}", nodeC, tree.pos(nodeC));
    //    log.info("pos for {} is {}", nodeD, tree.pos(nodeD));
    //    log.info("pos for {} is {}", nodeE, tree.pos(nodeE));
    //    log.info("pos for {} is {}", nodeF, tree.pos(nodeF));
    //
    //    tree.splay(nodeA);
    //    System.err.println(tree.printTree("Splayed A"));
    ////    TopDownSplayTreePrinter.print(tree);
    //
    //    Assert.assertEquals(0, tree.pos(nodeA));
    //    Assert.assertEquals(1, tree.pos(nodeB));
    //    Assert.assertEquals(2, tree.pos(nodeC));
    //    Assert.assertEquals(3, tree.pos(nodeD));
    //
    //    // show the pos for everything
    //    log.info("pos for {} is {}", nodeA, tree.pos(nodeA));
    //    log.info("pos for {} is {}", nodeB, tree.pos(nodeB));
    //    log.info("pos for {} is {}", nodeC, tree.pos(nodeC));
    //    log.info("pos for {} is {}", nodeD, tree.pos(nodeD));
    //
    //    Container2.Node<String> found = tree.find(0);
    //    Assert.assertEquals(nodeA, found);
    //    found = tree.find(2);
    //    Assert.assertEquals(nodeC, found);
    //    found = tree.find(3);
    //    Assert.assertEquals(nodeD, found);
    //    found = tree.find(1);
    //    Assert.assertEquals(nodeB, found);
    //
    //    // split the Container
    //    Pair<Container2<String>> pair = Container2.split(tree, nodeC);
    //    System.err.println(pair.first.printTree("First Tree after split on C"));
    ////    Container2Printer.print(pair.first);
    //    System.err.println(pair.second.printTree("Second Tree after split on C"));
    ////    Container2Printer.print(pair.second);
    //
    //    Container2<String> newContainer = tree.split(2); //Container.split(tree, 2);
    //    System.err.println(tree.printTree("Tree after split on 2"));
    //    System.err.println(newContainer.printTree("Split off part of tree that was split on 2"));
    ////    Container2Printer.print(tree);
    ////    Container2Printer.print(newContainer);
    //  }
    //
    ////  @Test
    ////  public void testSplayOnRightContainer() {
    ////    // make some segments
    ////    Segment<String> segmentA = makeSegment("A");
    ////    Segment<String> segmentB = makeSegment("B");
    ////    Segment<String> segmentC = makeSegment("C");
    ////    //    Segment<String> segmentD = makeSegment("D");
    ////    //    Segment<String> segmentE = makeSegment("E");
    ////    //    Segment<String> segmentF = makeSegment("F");
    ////    // make a Container and add all the segments
    ////    tree = Container.create();
    ////    tree.append(segmentA);
    ////    Container2Printer.print(tree);
    ////    tree.append(segmentB);
    ////    Container2Printer.print(tree);
    ////    tree.append(segmentC);
    ////    Container2Printer.print(tree);
    ////    //    tree.append(segmentD);
    ////    //    Container2Printer.print(tree);
    ////    //    tree.append(segmentE);
    ////    //    Container2Printer.print(tree);
    ////    //    tree.append(segmentF);
    ////    //    Container2Printer.print(tree);
    ////
    ////    tree.splay(segmentA);
    ////    Container2Printer.print(tree);
    ////
    ////    tree.splay(segmentC);
    ////    Container2Printer.print(tree);
    ////
    ////    //    tree.splay(segmentC);
    ////    //    Container2Printer.print(tree);
    ////    //    tree.splay(segmentB);
    ////    //    Container2Printer.print(tree);
    ////    //    tree.splay(segmentA);
    ////    //    Container2Printer.print(tree);
    ////    //
    ////    //    tree.splay(segmentC);
    ////    //    Container2Printer.print(tree);
    ////
    ////    //    Assert.assertEquals(0, tree.pos(segmentA));
    ////    //    Assert.assertEquals(1, tree.pos(segmentB));
    ////    //    Assert.assertEquals(2, tree.pos(segmentC));
    ////    //    Assert.assertEquals(3, tree.pos(segmentD));
    ////    //    Assert.assertEquals(3, tree.pos(segmentD));
    ////    //
    ////    //    // show the pos for everything
    ////    //    log.info("pos for {} is {}", segmentA, tree.pos(segmentA));
    ////    //    log.info("pos for {} is {}", segmentB, tree.pos(segmentB));
    ////    //    log.info("pos for {} is {}", segmentC, tree.pos(segmentC));
    ////    //    log.info("pos for {} is {}", segmentD, tree.pos(segmentD));
    ////    //    log.info("pos for {} is {}", segmentE, tree.pos(segmentE));
    ////    //    log.info("pos for {} is {}", segmentF, tree.pos(segmentF));
    ////    //
    ////    //    tree.splay(segmentA);
    ////    //    Container2Printer.print(tree);
    ////    //
    ////    //    Assert.assertEquals(0, tree.pos(segmentA));
    ////    //    Assert.assertEquals(1, tree.pos(segmentB));
    ////    //    Assert.assertEquals(2, tree.pos(segmentC));
    ////    //    Assert.assertEquals(3, tree.pos(segmentD));
    ////    //
    ////    //    // show the pos for everything
    ////    //    log.info("pos for {} is {}", segmentA, tree.pos(segmentA));
    ////    //    log.info("pos for {} is {}", segmentB, tree.pos(segmentB));
    ////    //    log.info("pos for {} is {}", segmentC, tree.pos(segmentC));
    ////    //    log.info("pos for {} is {}", segmentD, tree.pos(segmentD));
    ////    //
    ////    //    Segment<String> found = tree.find(0);
    ////    //    Assert.assertEquals(segmentA, found);
    ////    //    found = tree.find(2);
    ////    //    Assert.assertEquals(segmentC, found);
    ////    //    found = tree.find(3);
    ////    //    Assert.assertEquals(segmentD, found);
    ////    //    found = tree.find(1);
    ////    //    Assert.assertEquals(segmentB, found);
    ////    //
    ////    //    // split the Container
    ////    //    Pair<Container<String>> pair = Container.split(tree, segmentC);
    ////    //    Container2Printer.print(pair.first);
    ////    //    Container2Printer.print(pair.second);
    ////    //
    ////    //    Container<String> newContainer = tree.split(2);//Container.split(tree, 2);
    ////    //    Container2Printer.print(tree);
    ////    //    Container2Printer.print(newContainer);
    ////  }
    ////
    ////  @Test
    ////  public void testAppend() {
    ////    Container2Printer.print(tree);
    ////    tree.append(makeSegment("K"));
    ////    Container2Printer.print(tree);
    ////    tree.append(makeSegment("L"));
    ////    Container2Printer.print(tree);
    ////  }
    ////
    ////  @Test
    ////  public void testSizes() {
    ////    Container2Printer.print(tree);
    ////    Arrays.stream(nodes).forEach(n -> log.info("size of {} is {}", n, n.size()));
    ////    tree.splay(nodes[4]);
    ////    Container2Printer.print(tree);
    ////    Arrays.stream(nodes).forEach(n -> log.info("size of {} is {}", n, n.size()));
    ////  }
    ////
    ////  @Test
    ////  public void testPosition() {
    ////    Container2Printer.print(tree);
    ////    int pos = tree.pos(nodes[0]);
    ////    pos = tree.pos(nodes[1]);
    ////    pos = tree.pos(nodes[2]);
    ////    pos = tree.pos(nodes[3]);
    ////    pos = tree.pos(nodes[9]);
    ////
    ////    tree.splay(nodes[4]);
    ////    Container2Printer.print(tree);
    ////    pos = tree.pos(nodes[0]);
    ////    pos = tree.pos(nodes[1]);
    ////    pos = tree.pos(nodes[2]);
    ////    pos = tree.pos(nodes[3]);
    ////    pos = tree.pos(nodes[4]);
    ////    pos = tree.pos(nodes[5]);
    ////    pos = tree.pos(nodes[6]);
    ////    pos = tree.pos(nodes[7]);
    ////    pos = tree.pos(nodes[8]);
    ////    pos = tree.pos(nodes[9]);
    ////  }
    ////
    ////  @Test
    ////  public void testFind() {
    ////
    ////    tree.splay(nodes[4]);
    ////    Container2Printer.print(tree);
    ////    Segment<String> found = tree.find(0);
    ////    found = tree.find(1);
    ////    found = tree.find(2);
    ////    found = tree.find(3);
    ////    found = tree.find(4);
    ////    found = tree.find(5);
    ////    found = tree.find(6);
    ////    found = tree.find(7);
    ////    found = tree.find(8);
    ////    found = tree.find(9);
    ////  }
    ////
    ////  @Test
    ////  public void testSplitAndJoin() {
    ////    tree.splay(tree.find(5));
    ////    Container2Printer.print(tree);
    ////    Container<String> greater = tree.split(5);
    ////    Container2Printer.print(tree);
    ////    Container2Printer.print(greater);
    ////
    ////    tree.join(greater);
    ////    Container2Printer.print(tree);
    ////  }
    ////
    ////  @Test
    ////  public void testHeight() {
    ////    Container2Printer.print(tree);
    ////    log.info("tree height {}", tree.root.height());
    ////    log.info("tree height: {}", tree.height());
    ////    Assert.assertEquals(9, tree.height());
    ////    Assert.assertEquals(9, tree.root.height());
    ////    tree.splay(nodes[4]);
    ////    Container2Printer.print(tree);
    ////    log.info("tree height {}", tree.root.height());
    ////    log.info("tree height: {}", tree.height());
    ////    Assert.assertEquals(5, tree.height());
    ////    Assert.assertEquals(5, tree.root.height());
    ////  }
    ////
    ////  @Test
    ////  public void testSplay() {
    ////    Container2Printer.print(tree);
    ////    tree.splay(nodes[4]);
    ////    Container2Printer.print(tree);
    ////    Assert.assertEquals(nodes[4], tree.root);
    ////
    ////    tree.splay(nodes[3]);
    ////    Container2Printer.print(tree);
    ////    tree.splay(nodes[7]);
    ////
    ////    Pair<Container<String>> trees = Container.split(tree, nodes[3]);
    ////    Container2Printer.print(trees.first);
    ////    Container2Printer.print(trees.second);
    ////  }
  }
}
