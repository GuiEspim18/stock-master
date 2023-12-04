package br.com.stockMaster.dao.products;

import br.com.stockMaster.Product;

import java.util.List;

public interface ProductsDAO {

    private void verify() {

    }

    void post(Product product);

    Product getById(int id);

    void update(Product product, int id);

    void delete(int id);

    List<Product> getAll();

}
