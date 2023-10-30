package datastore;

import java.util.ArrayList;
import java.util.Vector;

/**
 * An interface for making sure different operations can be done to a list of data points in a uniform and extendable way
 * <p>
 * Modularization Units:
 * - Module that only contains the method to perform a computation
 * - Classes for specific instances of the operation interface
 */

public interface Operation {

    /**
     * Computes the given operation on a vector of data
     *
     * @param data the data to be computed
     * @return the result of the computation
     */
    Object compute(Vector<Object> data);


    class Median implements Operation {

        // ERROR: here we have a problem. the precondition is stronger than it is in the interface.
        // We should (especially if we think of only dealing with numbers) change the type in the vector to Number
        /**
         * Computes the median (value in the middle of an ordered list) of the given data
         *
         * @param data the data to be computed. The data should be ordered
         * @return the median of the given data or null if the data is empty
         */
        @Override
        public Object compute(Vector<Object> data) {
            ArrayList<Object> list = new ArrayList<>(data);
            return list.get(list.size() / 2);
        }

        /**
         * @return the name of this operation
         */
        @Override
        public String toString() {
            return "Median";
        }
    }

    class Mean implements Operation {
        // ERROR: The postcondition here should be that null or 0 is returned if the data is empty.
        // That's not the case here, we would get an (un-catched) exception
        /**
         * Computes the mean (average) of the given data
         *
         * @param data the data to be computed. All data points need to be numbers
         * @return the mean of the given data.
         */
        @Override
        public Object compute(Vector<Object> data) {
            double sum = 0;
            for (Object o : data) {
                if (o instanceof Number) {
                    sum += ((Number) o).doubleValue();
                } else {
                    throw new IllegalArgumentException("Mean can only be computed on numbers");
                }
            }
            return sum / data.size();
        }

        /**
         * @return the name of this operation
         */
        @Override
        public String toString() {
            return "Mean";
        }
    }
}