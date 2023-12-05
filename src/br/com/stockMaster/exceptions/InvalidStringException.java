package br.com.stockMaster.exceptions;

public class InvalidStringException extends Exception{

    private String message = "Valor inválido!";

    @Override
    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
