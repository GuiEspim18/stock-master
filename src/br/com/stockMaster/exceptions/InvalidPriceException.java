package br.com.stockMaster.exceptions;

public class InvalidPriceException extends Exception{
    private String message = "O preço não pode ser abaixo de 0!";

    @Override
    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
