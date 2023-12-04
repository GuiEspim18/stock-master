package br.com.stockMaster;

import br.com.stockMaster.dao.products.Products;
import br.com.stockMaster.database.DBConnection;
import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StockMaster {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Products products = new Products();

    public static void main(String[] args) {
        init();
    }

    private static void init() {
        try {
            System.out.println("Bem-vindo (a) ao StockMaster!\n");
            String[] options = { "Adicionar item", "Deletar item", "Atualizar item", "Achar um item por id", "Listar items" };
            for (int i = 0; i < options.length; i++) {
                final String message = MessageFormat.format("({0}) {1}", i + 1, options[i]);
                System.out.println(message);
            }
            System.out.println("\nEscolha uma opção: ");
            final int choice = Integer.parseInt(scanner.next());

            switch (choice) {
                case 1:
                    break;

                case 2:
                    break;

                case 3:
                    break;

                case 4:
                    break;

                case 5:
                    getAll();
                    break;
                default:
                    throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            System.out.println("Digite um número válido!");
            scanner.close();
        }
    }

    private static void getAll() {
        List<Product> list = products.getAll();
        for (Product item : list) {
            final String message = MessageFormat.format("{0} | R$: {1} | {2}", item.getName(), item.getPrice(), item.getDescription());
            System.out.println(message);
        }
    }
}
