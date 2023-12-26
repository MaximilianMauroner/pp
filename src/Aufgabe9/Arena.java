public class Arena {
    public static void main(String[] args) {
        try {
            String[] inputPattern = {"ANTS", "LEAFS", "SIZE", "WAITSTEPS"};
            Parameters params = Parameters.getInstance(args, inputPattern);

            System.out.println("ANTS: " + params.get("ANTS"));

            Character[][] arena = MapGenerator.generate(params.get("SIZE"), params.get("LEAFS"));
            for (Character[] row : arena) {
                for (char c : row) {
                    System.out.print(c);
                }
                System.out.println();
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
