package formicarium;

import java.util.Iterator;

/*
 * The RatedSet interface is an interface with three type parameters X, P, and R, and the supertype java.lang.Iterable<X>. 
 * An object of this type is a container with entries of types X and P, where X must be a subtype of Rated (with appropriate type parameter substitutions), 
 * so that for each entry x of type X and each entry p of type P, the method x.rated(p) can be called and returns a result of type R. 
*/ 
public interface RatedSet<
        X extends Rated<? super P, R>,
        P,
        R extends Calc<R>
        > extends Iterable<X> {

    // Pre: x is a non-null object of type X.
    // Post: Adds the given item x to the set. If the item already exists in the set, the set remains unchanged.
    void add(X x);

    // Pre: p is a non-null object of type P.
    // Post: Sets the criterion for rating to the given item p. The criterion is used in subsequent calls to the iterator() methods.
    void addCriterion(P p);

    // Pre: -
    // Post: Returns an iterator over the elements in the set. The returned iterator is never null.
    Iterator<X> iterator();

    // Pre: p is a non-null object of type P, and r is a non-null object of type R.
    // Post: Returns an iterator over the elements in the set that meet the given criterion p and rating r. The returned iterator is never null.
    Iterator<X> iterator(P p, R r);

    // Pre: r is a non-null object of type R.
    // Post: Returns an iterator over the elements in the set that meet the given rating r. The returned iterator is never null.
    Iterator<X> iterator(R r);

    // Pre: -.
    // Post: Returns an iterator over the criterions in the set. The returned iterator is never null.
    Iterator<P> criterions();
}
