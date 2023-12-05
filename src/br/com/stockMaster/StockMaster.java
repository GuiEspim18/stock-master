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
    private static final Products products = new Products();

    public static void main(String[] args) {
        init();
    }

    private static void init() {
        final Scanner scanner = new Scanner(System.in).useLocale(Locale.US);
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
                case 1: save(); break;
                case 2: delete(); break;
                case 3: update(); break;
                case 4: getById(); break;
                case 5: getAll(); break;
                default: throw new NumberFormatException();
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
        System.out.format("_____________________________________________________________%n");
        System.out.format("| %-2s | %-8s | %5s | %-20s | %-10s |%n", "ID", "NOME", "PREÇO", "DESCRIÇÃ0", "QUANTIDADE");
        System.out.format("-------------------------------------------------------------%n");
        for (Product item : list) {
            System.out.format("| %-2s | %-8s | %5s | %-20s | %-10s |%n", item.getId(), item.getName(), item.getPrice(), item.getDescription(), item.getQuantity());
        }
        System.out.format("-------------------------------------------------------------%n");
    }

    private static void save() throws InvalidStringException, InvalidPriceException {
        final Scanner scanner = new Scanner(System.in).useLocale(Locale.US);
        try {
            // getting product name
            System.out.println("Digite o nome do seu produto: ");
            String name = scanner.nextLine();
            if (name.isEmpty()) {
                throw new InvalidStringException();
            }

            // getting product description
            System.out.println("Digite uma descrição para o seu produto: ");
            String description = scanner.nextLine();
            if (description.isEmpty()) {
                throw new InvalidStringException();
            }

            // getting product value
            System.out.println("Digite um valor para o seu produto: ");
            double price = scanner.nextDouble();
            if (price == 0.0) {
                throw new InvalidPriceException();
            }

            // getting product quantity
            System.out.println("Digite a quantidade do produto: ");
            int quantity = scanner.nextInt();
            if (quantity == 0) {
                throw new InvalidPriceException();
            }

            // saving product on database
            Product product = new Product(name, price, description, quantity);
            products.post(product);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close();
            getAll();
        }
    }

    private static void delete() {
        final Scanner scanner = new Scanner(System.in).useLocale(Locale.US);
        try {
            getAll();
            System.out.println("Digite o id do item que você quer deletar: ");
            final int choice = scanner.nextInt();
            final boolean result = products.delete(choice);
            if (result) {
                final String message = MessageFormat.format("Produto de id {0} deletado", choice);
                System.out.println(message);
            } else {
                final String message = MessageFormat.format("Nenhum produto de id {0} encontrado", choice);
                System.out.println(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close();
            getAll();
        }
    }

    private static void update() {
        final Scanner scanner = new Scanner(System.in).useLocale(Locale.US);
        try {
            getAll();
            System.out.println("Digite o id do item que você quer editar: ");
            final int choice = scanner.nextInt();
            // Consumir a quebra de linha pendente
            scanner.nextLine();
            final Product found = products.getById(choice);
            String name;
            String description;
            double price;
            int quantity;

            if (found != null) {
                // getting product name
                System.out.println(MessageFormat.format("Digite o nome do seu produto ({0}): ", found.getName()));
                name = scanner.nextLine();
                if (name.isEmpty()) {
                    name = found.getName();
                }

                // getting product description
                System.out.println(MessageFormat.format("Digite uma descrição para o seu produto ({0}): ", found.getDescription()));
                description = scanner.nextLine();
                if (description.isEmpty()) {
                    description = found.getDescription();
                }

                // getting product value
                System.out.println(MessageFormat.format("Digite um valor para o seu produto ({0}): ", found.getPrice()));
                price = scanner.nextDouble();
                if (Double.isNaN(price)) {
                    price = found.getPrice();
                }

                // getting product quantity
                System.out.println(MessageFormat.format("Digite a quantidade do produto ({0}): ", found.getQuantity()));
                quantity = scanner.nextInt();
                if (Double.isNaN(Double.parseDouble(String.valueOf(quantity)))) {
                    quantity = found.getQuantity();
                }

                // saving product on database
                Product product = new Product(name, price, description, quantity);
                products.update(product, choice);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close();
            getAll();
        }

    }

    protected static void getById() {
        try {
            final Scanner scanner = new Scanner(System.in).useLocale(Locale.US);
            System.out.print("Digite o id do produto que voçê deseja achar no banco de dados: ");
            final int choice = scanner.nextInt();
            Product result = products.getById(choice);
            if (result != null) {
                System.out.format("_____________________________________________________________%n");
                System.out.format("| %-2s | %-8s | %5s | %-20s | %-10s |%n", "ID", "NOME", "PREÇO", "DESCRIÇÃ0", "QUANTIDADE");
                System.out.format("-------------------------------------------------------------%n");
                System.out.format("| %-2s | %-8s | %5s | %-20s | %-10s |%n", result.getId(), result.getName(), result.getPrice(), result.getDescription(), result.getQuantity());
                System.out.format("-------------------------------------------------------------%n");
            } else {
                System.out.println("Produto não encontrado");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
