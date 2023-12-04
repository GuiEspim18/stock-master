package br.com.stockMaster;

import br.com.stockMaster.database.DBConnection;

import java.sql.Connection;

public class StockMaster {
    public static void main(String[] args) {
        Connection connection = DBConnection.getConnection();
    }
}
