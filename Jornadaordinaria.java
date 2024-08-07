package jornadaordinaria;

import java.util.Scanner;

public class Jornadaordinaria {

    public static void main(String[] args) {
        double pagohoraOrdinaria = 16;
        double pagohoraExtraordinaria = 20;
        double jornadaTotal;
        double Total = 0;
        Scanner scanner = new Scanner(System.in);
        System.out.println("¿Cuantas horas trabajaste?");
        jornadaTotal = scanner.nextDouble();
        scanner.close();
        if (jornadaTotal > 40) {
            Total = jornadaTotal * pagohoraExtraordinaria;
        } else {
            Total = jornadaTotal * pagohoraOrdinaria;
        }
        System.out.println("Total: " + Total);
    }
}
