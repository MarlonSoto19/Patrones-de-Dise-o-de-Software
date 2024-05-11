package EjemploDeLaMochila;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class Objeto {
    String nombre;
    double peso;
    double precio;

    public Objeto(String nombre, double peso, double precio) {
        this.nombre = nombre;
        this.peso = peso;
        this.precio = precio;
    }
}

public class Mochila {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in).useLocale(Locale.US);
        List<Objeto> objetos = new ArrayList<>();

        System.out.println("----" + "Ingrese el número de objetos disponibles:" + "----");
        int numeroObjetos = scanner.nextInt();
        scanner.nextLine(); // Limpia el buffer del escáner después de leer un entero

        for (int i = 0; i < numeroObjetos; i++) {
            System.out.println("Ingrese datos del objeto " + (i + 1) + ":");
            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();

            System.out.print("Peso: ");
            double peso = scanner.nextDouble();

            System.out.print("Precio: ");
            double precio = scanner.nextDouble();
            scanner.nextLine(); // Limpia el buffer del escáner después de leer un double

            objetos.add(new Objeto(nombre, peso, precio));
        }

        System.out.print("Ingrese el peso máximo de la mochila: ");
        double capacidad = scanner.nextDouble();
        scanner.nextLine(); // Limpia el buffer del escáner después de leer un double

        List<Objeto> seleccionados = knapsack(objetos, capacidad);

        System.out.println("----" + "Objetos seleccionados en la mochila:" + "----");
        double totalPrecio = 0.0;
        for (int i = 0; i < seleccionados.size(); i++) {
            Objeto objeto = seleccionados.get(i);
            System.out.println("Objeto " + (i + 1) + ":");
            System.out.println("Nombre: " + objeto.nombre);
            System.out.println("Peso: " + objeto.peso);
            System.out.println("Precio: " + objeto.precio);
            totalPrecio += objeto.precio;
        }
        System.out.println("----" + "Valor total de los objetos en la mochila: " + totalPrecio + "----");

        scanner.close();
    }

    public static List<Objeto> knapsack(List<Objeto> objetos, double capacidad) {
        int n = objetos.size();
        double[][] dp = new double[n+1][(int)(capacidad+1)];

        for (int i = 0; i <= n; i++) {
            for (int w = 0; w <= capacidad; w++) {
                if (i == 0 || w == 0)
                    dp[i][w] = 0;
                else if (objetos.get(i-1).peso <= w)
                    dp[i][w] = Math.max(objetos.get(i-1).precio + dp[i-1][(int)(w-objetos.get(i-1).peso)], dp[i-1][w]);
                else
                    dp[i][w] = dp[i-1][w];
            }
        }

        List<Objeto> seleccionados = new ArrayList<>();
        int w = (int) capacidad;
        for (int i = n; i > 0 && w >= 0; i--) {
            if (dp[i][w] != dp[i-1][w]) {
                Objeto obj = objetos.get(i-1);
                seleccionados.add(obj);
                w -= obj.peso;
            }
        }

        return seleccionados;
    }
}
