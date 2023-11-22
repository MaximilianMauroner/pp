package formicarium;

/*
 * The Rated interface represents a rating system in the formicarium system. It is a generic interface that takes two type parameters: P and R.
 * R must be a type that extends the Calc interface.
 * The Rated interface provides methods to get a rating for a given item (rated(P p)), set a criterion (setCriterion(P p)), and get a rating (rated()).
 */
public interface Rated<P, R extends Calc<R>> {

    // Pre: The Rated object is initialized and p is a non-null object of type P.
    // Post: Returns a rating of type R for the given item p. Returns null if no criterion has been set
    R rated(P p);

    // Pre: The Rated object is initialized and p is a non-null object of type P.
    // Post: Sets the criterion for rating to the given item p. The criterion is used in subsequent calls to the rated() method.
    void setCriterion(P p);


    // Pre: The Rated object is initialized and a criterion has been set using the setCriterion(P p) method.
    // Post: Returns a rating of type R for the item that was set as the criterion. The returned rating is never null.
    R rated();
}