package ru.stereohorse.glenn.entities;

import java.util.List;

public class Receipts {

    private List<Receipt> receipts;

    public Receipts(List<Receipt> receipts) {
        this.receipts = receipts;
    }

    public static Receipts from(List<Receipt> receipts) {
        return new Receipts(receipts);
    }

    public void store(Storage storage) {
        System.out.println("Stored!");
    }
}
