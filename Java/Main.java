import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static boolean shouldRemove(int position, int step) {
        if (step == 0) return position %2 == 0;
        if (step == 1) return position % 2 == 1;
        return position % 3 == 0;
    }

    public static ArrayList<Integer> removeStep(ArrayList<Integer> list, int step) {
        ArrayList<Integer> next = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            if (!shouldRemove(i + 1, step)){
                next.add(list.get(i));
            }
        }
        return next;
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        ArrayList<Integer> list = new ArrayList<>();

        for (int i = 1; i <= n; i++) list.add(i);
        int step = 0;
        while (list.size() > 1){
            list = removeStep(list, step);
            step = (step + 1) % 3;
        }
        System.out.println(list.get(0));
    }
}