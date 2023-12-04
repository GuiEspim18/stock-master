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
                    "            price DECIMAL(10, 2),\n" +
                    "            description TEXT\n" +
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
        try {
            this.verify();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Product getById(int id) {
        try {
            this.verify();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Product product, int id) {
        try {
            this.verify();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try {
            this.verify();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Product> getAll() {
        List<Product> products = new ArrayList<Product>();
        try {
            this.verify();
            this.connection = DBConnection.getConnection();
            this.preparedStatement = this.connection.prepareStatement("SELECT * FROM public.PRODUCTS");
            this.rs = this.preparedStatement.executeQuery();
            while (rs.next()) {
                Product product = new Product(rs.getString("name"), rs.getInt("price"), rs.getString("description"));
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
