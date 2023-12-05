package br.com.stockMaster.dao.products;

import br.com.stockMaster.Product;
import br.com.stockMaster.database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class Products implements ProductsDAO {
    private Connection connection;
    PreparedStatement preparedStatement = null;
    ResultSet rs = null;

    private void verify() {
        try {
            this.connection = DBConnection.getConnection();
            this.preparedStatement = this.connection.prepareStatement(
                    "DO $$ \n" +
                    "BEGIN\n" +
                    "    IF NOT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'products') THEN\n" +
                    "        CREATE TABLE products (\n" +
                    "            id SERIAL PRIMARY KEY,\n" +
                    "            name VARCHAR(255),\n" +
                    "            price DOUBLE PRECISION,\n" +
                    "            description TEXT,\n" +
                    "            quantity INTEGER\n" +
                    "        );\n" +
                    "        RAISE NOTICE 'Table created';\n" +
                    "     END IF;\n" +
                    "END $$;"
            );
            this.preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void closeConnections() {
        try {
            this.preparedStatement.close();
            this.connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void post(Product product) {
        this.verify();
        try {
            this.connection = DBConnection.getConnection();
            this.preparedStatement = this.connection.prepareStatement("INSERT INTO public.PRODUCTS (name, price, description, quantity) VALUES (?, ?, ?, ?);");
            this.preparedStatement.setString(1, product.getName());
            this.preparedStatement.setDouble(2, product.getPrice());
            this.preparedStatement.setString(3, product.getDescription());
            this.preparedStatement.setInt(4, product.getQuantity());
            this.preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            this.closeConnections();
        }
    }

    @Override
    public Product getById(int id) {
        Product product = null;
        try {
            this.verify();
            this.connection = DBConnection.getConnection();
            this.preparedStatement = this.connection.prepareStatement("SELECT * FROM public.PRODUCTS WHERE ID = ?");
            this.preparedStatement.setInt(1, id);
            this.rs = this.preparedStatement.executeQuery();
            while (rs.next()) {
                product = new Product(rs.getString("name"), rs.getDouble("price"), rs.getString("description"), rs.getInt("quantity"), rs.getInt("id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return product;
    }

    @Override
    public void update(Product product, int id) {
        try {
            this.verify();
            this.connection = DBConnection.getConnection();
            this.preparedStatement = this.connection.prepareStatement("UPDATE public.PRODUCTS SET NAME = ?, DESCRIPTION = ?, PRICE = ?, QUANTITY = ? WHERE ID = ? ");
            this.preparedStatement.setString(1, product.getName());
            this.preparedStatement.setString(2, product.getDescription());
            this.preparedStatement.setDouble(3, product.getPrice());
            this.preparedStatement.setInt(4, product.getQuantity());
            this.preparedStatement.setInt(5, id);
            this.preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean delete(int id) {
        try {
            this.verify();
            this.connection = DBConnection.getConnection();
            this.preparedStatement = this.connection.prepareStatement("DELETE FROM public.PRODUCTS WHERE ID = ?");
            this.preparedStatement.setInt(1, id);
            this.preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Product> getAll() {
        List<Product> products = new ArrayList<Product>();
        this.verify();
        try {
            this.connection = DBConnection.getConnection();
            this.preparedStatement = this.connection.prepareStatement("SELECT * FROM public.PRODUCTS");
            this.rs = this.preparedStatement.executeQuery();
            while (rs.next()) {
                Product product = new Product(rs.getString("name"), rs.getDouble("price"), rs.getString("description"), rs.getInt("quantity"), rs.getInt("id"));
                products.add(product);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeConnections();
        }
        return products;
    }

}
