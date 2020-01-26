package com.tom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A splay tree for items that are not comparable. There is no 'insert' method, any new item is
 * appended to the left side of the SplayTree by first finding the 'max' (farthest right), splaying
 * to it, and adding the new Node as its right child
 *
 * @param <T>
 */
public class InsertionOrderSplayTreeWithSize<T> {

  private static final Logger log = LoggerFactory.getLogger(InsertionOrderSplayTreeWithSize.class);

  static <T> int nodeSize(Node<T> node) {
    return node == null ? 0 : node.size;
  }

  public static class Node<T> {
    T key;
    Node<T> parent;
    Node<T> left;
    Node<T> right;
    int size;

    public Node(T key) {
      this.key = key;
    }

    public int size() {
      return this.size;
    }

    int count() {
      int leftCount = left != null ? left.count() : 0;
      int rightCount = right != null ? right.count() : 0;
      int count = 1 + leftCount + rightCount;
      return count;
    }

    public void validate() {
      if (this == left) {
        throw new RuntimeException("this == left");
      }
      if (left != null && left == right) {
        throw new RuntimeException("children match");
      }
      if (this == this.parent) {
        throw new RuntimeException("node is its own parent");
      }
    }
  }

  Node<T> root;

  int size;

  public static <T> InsertionOrderSplayTreeWithSize<T> create() {
    return new InsertionOrderSplayTreeWithSize<>();
  }

  public static <T> InsertionOrderSplayTreeWithSize<T> create(Node<T> root) {
    return new InsertionOrderSplayTreeWithSize<>(root);
  }

  private InsertionOrderSplayTreeWithSize() {}

  private InsertionOrderSplayTreeWithSize(Node<T> root) {
    this.root = root;
  }

  void leftRotate(Node<T> x) {
    Node<T> y = x.right;
    if (y != null) {
      x.right = y.left;
      if (y.left != null) y.left.parent = x;
      y.parent = x.parent;
    }

    if (x.parent == null) root = y;
    else if (x == x.parent.left) x.parent.left = y;
    else x.parent.right = y;
    if (y != null) y.left = x;

    x.size = nodeSize(x.left) + nodeSize(x.right) + 1;

    x.parent = y;
  }

  void rightRotate(Node<T> x) {
    Node<T> y = x.left;
    if (y != null) {
      x.left = y.right;
      if (y.right != null) y.right.parent = x;
      y.parent = x.parent;
    }
    if (null == x.parent) root = y;
    else if (x == x.parent.left) x.parent.left = y;
    else x.parent.right = y;
    if (y != null) y.right = x;

    x.size = nodeSize(x.left) + nodeSize(x.right) + 1;

    x.parent = y;
  }

  public void splay(T element) {
    Node<T> node = find(element);
    if (node != null) {
      splay(node);
    }
  }

  public void splay(Node<T> x) {
    int rootSize = nodeSize(root);
    int leftSize = 0;
    int rightSize = 0;

    while (x.parent != null) {
      if (null == x.parent.parent) {
        if (x.parent.left == x) rightRotate(x.parent);
        else leftRotate(x.parent);
      } else if (x.parent.left == x && x.parent.parent.left == x.parent) {
        rightRotate(x.parent.parent);
        rightRotate(x.parent);
      } else if (x.parent.right == x && x.parent.parent.right == x.parent) {
        leftRotate(x.parent.parent);
        leftRotate(x.parent);
      } else if (x.parent.left == x && x.parent.parent.right == x.parent) {
        rightRotate(x.parent);
        leftRotate(x.parent);
      } else {
        leftRotate(x.parent);
        rightRotate(x.parent);
      }
    }

    leftSize += nodeSize(root.left); /* Now l_size and r_size are the sizes of */
    rightSize += nodeSize(root.right); /* the left and right trees we just built.*/
    root.size = leftSize + rightSize + 1;
  }

  static <T> Node<T> p(Node<T> node) {
    return node.parent;
  }

  static <T> int size(Node<T> node) {
    return node != null ? node.size() : 0;
  }

  static <T> Node<T> l(Node<T> node) {
    return node.left;
  }

  static <T> Node<T> r(Node<T> node) {
    return node.right;
  }

  public int pos(Node<T> node) {
    if (node == root) {
      return size(l(node));
    } else if (r(p(node)) == node) { // node is a right child
      return pos(p(node)) + size(l(node)) + 1;
    } else if (l(p(node)) == node) { // node is a left child
      return pos(p(node)) - size(r(node)) - 1;
    } else {
      return -1;
    }
  }

  void replace(Node<T> u, Node<T> v) {
    if (null == u.parent) root = v;
    else if (u == u.parent.left) u.parent.left = v;
    else u.parent.right = v;
    if (v != null) v.parent = u.parent;
  }

  Node<T> subtree_minimum(Node<T> u) {
    while (u.left != null) u = u.left;
    return u;
  }

  Node<T> subtree_maximum(Node<T> u) {
    while (u.right != null) u = u.right;
    return u;
  }

  public Node<T> max() {
    return subtree_maximum(root);
  }

  public Node<T> min() {
    return subtree_minimum(root);
  }

  public void append(T key) {
    Node<T> z = new Node<>(key);
    z.size = 1;
    if (root == null) {
      root = z;
      return;
    }
    Node<T> max = max();
    splay(max);

    System.err.println(printTree("Appending " + key));

    max.right = z;
    max.size += z.size;
    z.parent = max;
    System.err.println(printTree("Appended " + key));
    size++;
  }

  public static <T extends Comparable<T>> void join(
      Pair<InsertionOrderSplayTreeWithSize<T>> trees) {
    // find my largest item
    Node<T> largest = trees.first.max();
    trees.first.splay(largest);
    trees.first.root.right = trees.second.root;
  }

  public void join(InsertionOrderSplayTreeWithSize<T> joiner) {
    log.info("max:{}, joiner min: {}", max().key, joiner.min().key);
    Node<T> largest = max();
    splay(largest);
    root.right = joiner.root;
  }

  public InsertionOrderSplayTreeWithSize<T> split(T key) {
    // split off the right side of key
    Node<T> node = find(key);
    InsertionOrderSplayTreeWithSize<T> splitter =
        InsertionOrderSplayTreeWithSize.create(node.right);
    node.right = null;
    return splitter;
  }

  public Node<T> find(int k) {
    return find(root, k);
  }

  Node<T> find(Node<T> node, int k) {
    if (node == null) return null;
    int pos = pos(node);

    if (pos == k) {
      return node;
    }
    if (pos < k) {
      return find(node.right, k);
    } else {
      return find(node.left, k);
    }
  }

  public static <T extends Comparable<T>> Pair<InsertionOrderSplayTreeWithSize<T>> split(
      InsertionOrderSplayTreeWithSize<T> tree, T key) {
    // assume we find key
    Node<T> node = tree.find(key);
    return Pair.of(
        InsertionOrderSplayTreeWithSize.create(node.left),
        InsertionOrderSplayTreeWithSize.create(node.right));
  }

  public Node<T> find(Node<T> node) {
    return find(root, node);
  }

  private Node<T> find(Node<T> from, Node<T> node) {
    if (from == null) return null;
    if (from == node) return from;
    Node<T> found = find(from.left, node);
    if (found != null) {
      return found;
    } else {
      found = find(from.right, node);
      if (found != null) {
        return found;
      } else {
        return null;
      }
    }
  }

  public Node<T> find(T key) {
    return find(root, key);
  }

  private Node<T> find(Node<T> from, T node) {
    if (from == null) return null;
    if (from.key == node) return from;
    Node<T> found = find(from.left, node);
    if (found != null) {
      return found;
    } else {
      found = find(from.right, node);
      if (found != null) {
        return found;
      } else {
        return null;
      }
    }
  }

  public void erase(T key) {
    Node<T> z = find(key);
    if (null == z) return;

    splay(z);

    if (null == z.left) replace(z, z.right);
    else if (null == z.right) replace(z, z.left);
    else {
      Node<T> y = subtree_minimum(z.right);
      if (y.parent != z) {
        replace(y, y.right);
        y.right = z.right;
        y.right.parent = y;
      }
      replace(z, y);
      y.left = z.left;
      y.left.parent = y;
    }

    size--;
  }

  public int size() {
    return size;
  }

  public int height() {
    return height(root);
  }

  public static <T> int height(Node<T> node) {
    return node != null ? 1 + Math.max(height(node.left), height(node.right)) : 0;
  }

  static <T extends Comparable> boolean comp(T one, T two) {
    return one.compareTo(two) < 0;
  }

  public String printTree() {
    return printTree(root, 0);
  }

  public String printTree(Node<T> node, int d) {
    StringBuilder builder = new StringBuilder();
    int i;
    if (node == null) return "";
    builder.append(printTree(node.right, d + 1));
    for (i = 0; i < d; i++) builder.append("  ");
    builder.append(node.key + "(" + node.size + ")\n");
    builder.append(printTree(node.left, d + 1));
    return builder.toString();
  }

  public String printTree(String note) {
    return note + "\n" + printTree(root, 0);
  }

  public void validate() {
    // root parent is null
    if (root != null && root.parent != null) {
      throw new RuntimeException("root parent is not null");
    }
    root.validate();
    validateChild(root.left);
    validateChild(root.right);
  }

  private void validateChild(Node<T> node) {
    if (node != null) {
      node.validate();
      if (node.parent == null) {
        throw new RuntimeException("child has null parent");
      }
      if (node.size != node.count()) {
        throw new RuntimeException("size does not match count");
      }
      validateChild(node.left);
      validateChild(node.right);
    }
  }
}