package datastore;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

/**
 * A interface for making sure different operations can be done to a list of data points in a uniform and extendable way
 *
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

        /**
         * Computes the median (value in the middle of an ordered list) of the given data
         *
         * @param data the data to be computed
         * @return the median of the given data
         */
        @Override
        public Object compute(Vector<Object> data) {
            ArrayList<Object> list = new ArrayList<>(data);
            return list.get(list.size() / 2);
        }

        @Override
        public String toString() {
            return "Median";
        }
    }

    class Mean implements Operation {

        /**
         * Computes the mean (average) of the given data
         *
         * @param data the data to be computed
         * @return the mean of the given data
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

        @Override
        public String toString() {
            return "Mean";
        }
    }
}