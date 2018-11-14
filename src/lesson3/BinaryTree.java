package lesson3;

import kotlin.NotImplementedError;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

// Attention: comparable supported but comparator is not
@SuppressWarnings("WeakerAccess")
public class BinaryTree<T extends Comparable<T>> extends AbstractSet<T> implements CheckableSortedSet<T> {

    private static class Node<T> {
        final T value;

        Node<T> left = null;

        Node<T> right = null;

        Node(T value) {
            this.value = value;
        }
    }

    private Node<T> root = null;

    private int size = 0;

    @Override
    public boolean add(T t) {
        Node<T> closest = find(t);
        int comparison = closest == null ? -1 : t.compareTo(closest.value);
        if (comparison == 0) {
            return false;
        }
        Node<T> newNode = new Node<>(t);
        if (closest == null) {
            root = newNode;
        }
        else if (comparison < 0) {
            assert closest.left == null;
            closest.left = newNode;
        }
        else {
            assert closest.right == null;
            closest.right = newNode;
        }
        size++;
        return true;
    }

    public boolean checkInvariant() {
        return root == null || checkInvariant(root);
    }

    private boolean checkInvariant(Node<T> node) {
        Node<T> left = node.left;
        if (left != null && (left.value.compareTo(node.value) >= 0 || !checkInvariant(left))) return false;
        Node<T> right = node.right;
        return right == null || right.value.compareTo(node.value) > 0 && checkInvariant(right);
    }

    /**
     * Удаление элемента в дереве
     * Средняя
     */
    @Override
    public boolean remove(Object o) {
        throw new NotImplementedError();
    }

    @Override
    public boolean contains(Object o) {
        @SuppressWarnings("unchecked")
        T t = (T) o;
        Node<T> closest = find(t);
        return closest != null && t.compareTo(closest.value) == 0;
    }

    private Node<T> find(T value) {
        if (root == null) return null;
        return find(root, value);
    }

    private Node<T> find(Node<T> start, T value) {
        int comparison = value.compareTo(start.value);
        if (comparison == 0) {
            return start;
        }
        else if (comparison < 0) {
            if (start.left == null) return start;
            return find(start.left, value);
        }
        else {
            if (start.right == null) return start;
            return find(start.right, value);
        }
    }

    public class BinaryTreeIterator implements Iterator<T> {

        private Node<T> current = null;
        private Stack<Node<T>> stack = new Stack<>();

        private BinaryTreeIterator() {
            add(root);
        }

        private void add(Node<T> currentNode) {
            while (currentNode != null) {
                stack.push(currentNode);
                currentNode = currentNode.left;
            }
        }
        /**
         * Поиск следующего элемента
         * Средняя
         */
         // Трудоемкость т = O(1)
         // Ресурсоемкость R = O(N)
        private Node<T> findNext() {
           return stack.pop();
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public T next() {
            current = findNext();
            if (current.right != null) {
                add(current.right);
            }
            return current.value;
        }

        /**
         * Удаление следующего элемента
         * Сложная
         */
        @Override
        public void remove() {
            // TODO
            throw new NotImplementedError();
        }
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new BinaryTreeIterator();
    }

    @Override
    public int size() {
        return size;
    }


    @Nullable
    @Override
    public Comparator<? super T> comparator() {
        return null;
    }

    /**
     * Для этой задачи нет тестов (есть только заготовка subSetTest), но её тоже можно решить и их написать
     * Очень сложная
     */
    @NotNull
    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        // TODO
        throw new NotImplementedError();
    }

    /**
     * Найти множество всех элементов меньше заданного
     * Сложная
     */
     // Трудоемкость T = O(N)
     // Ресурсоемкость R = O(N)
    @NotNull
    @Override
    public SortedSet<T> headSet(T toElement) {
        SortedSet<T> set = new TreeSet<>();
        findHeadSet(set, root, toElement);
        return set;
    }

    public void findHeadSet(SortedSet<T> set, Node<T> currentNode, T toElement) {
        int comparison = currentNode.value.compareTo(toElement);
        if (comparison > 0 && currentNode.left != null) findHeadSet(set, currentNode.left, toElement);
        if (comparison <= 0 && currentNode.left != null) addSet(set, currentNode.left);
        if (comparison < 0) {
            set.add(currentNode.value);
            if (currentNode.right != null) findHeadSet(set, currentNode.right, toElement);
        }
    }

    public void addSet(SortedSet<T> set, Node<T> currentNode) {
        set.add(currentNode.value);
        if (currentNode.left != null) addSet(set, currentNode.left);
        if (currentNode.right != null) addSet(set, currentNode.right);
    }
    /**
     * Найти множество всех элементов больше или равных заданного
     * Сложная
     */
     // Трудоемкость T = O(N)
     // Ресурсоемкость R = O(N)
    @NotNull
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        SortedSet<T> set = new TreeSet<>();
        findTailSet(set, root, fromElement);
        return set;
    }

    public void findTailSet(SortedSet<T> set, Node<T> currentNode, T fromElement) {
        int comparison = currentNode.value.compareTo(fromElement);
        if (comparison < 0 && currentNode.right != null) findTailSet(set, currentNode.right, fromElement);
        if (comparison >= 0) {
            set.add(currentNode.value);
            if (currentNode.left != null) findTailSet(set, currentNode.left, fromElement);
            if (currentNode.right != null) addSet(set, currentNode.right);
        }
    }

    @Override
    public T first() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.value;
    }

    @Override
    public T last() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.value;
    }
}
