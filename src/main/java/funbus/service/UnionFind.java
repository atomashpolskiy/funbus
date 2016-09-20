package funbus.service;

import java.util.Collections;
import java.util.Set;

/**
 * Convenience wrapper around {@link org.jgrapht.alg.util.UnionFind} implementation.
 * Provides {@link UnionFind#contains} method for determining if this set already contains a given element.
 */
public class UnionFind<T> {

    private UnionFindWrapper<T> delegate;

    public UnionFind() {
        this.delegate = new UnionFindWrapper<>(Collections.<T>emptySet());
    }

    public boolean contains(T element) {
        return delegate.contains(element);
    }

    public void addElement(T element) {
        delegate.addElement(element);
    }

    public T find(T element) {
        return delegate.find(element);
    }

    public void union(T element1, T element2) {
        delegate.union(element1, element2);
    }

    private static class UnionFindWrapper<T> extends org.jgrapht.alg.util.UnionFind<T> {

        public UnionFindWrapper(Set<T> elements) {
            super(elements);
        }

        public boolean contains(T element) {
            return getParentMap().containsKey(element);
        }
    }
}
