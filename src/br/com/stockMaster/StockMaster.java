package br.com.stockMaster;

import br.com.stockMaster.dao.products.Products;
import br.com.stockMaster.database.DBConnection;
import br.com.stockMaster.exceptions.InvalidPriceException;
import br.com.stockMaster.exceptions.InvalidStringException;
import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class StockMaster {
    private static final Scanner scanner = new Scanner(System.in).useLocale(Locale.US);
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
                    save();
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
        } catch (InvalidStringException e) {
            System.out.println(e.getMessage());
        } catch (InvalidPriceException e) {
            System.out.println(e.getMessage());
        } finally {
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

    private static void save() throws InvalidStringException, InvalidPriceException {
        int quantity = 0;
        double price = 0.0;
        String description = null;
        String name = null;
        try {
            System.out.println("Digite o nome do seu produto: ");
            name = scanner.next();
            if (name.isEmpty()) {
                throw new InvalidStringException();
            }
            System.out.println("Digite uma descrição para o seu produto: ");
            description = scanner.next();
            if (description.isEmpty()) {
                throw new InvalidStringException();
            }
            System.out.println("Digite um valor para o seu produto: ");
            price = Float.parseFloat(scanner.next());
            if (price == 0.0) {
                throw new InvalidPriceException();
            }
            System.out.println("Digite a quantidade do produto: ");
            quantity = Integer.parseInt(scanner.next());
            if (quantity == 0) {
                throw new InvalidPriceException();
            }
        } catch (Exception e) {

        } finally {
            Product product = new Product(name, price, description, quantity);
            products.post(product);
        }


    }
}
