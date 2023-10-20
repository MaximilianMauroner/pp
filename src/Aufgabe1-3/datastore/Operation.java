package datastore;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

public interface Operation {
    Object compute(Vector<Object> data);


    class Median implements Operation {
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