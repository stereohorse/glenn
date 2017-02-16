package ru.stereohorse.glenn.entities;

import java.util.List;


public class ReceiptsImages {

    private List<ReceiptImage> receiptsImages;


    public ReceiptsImages(List<ReceiptImage> receipts) {
        this.receiptsImages = receipts;
    }

    public static ReceiptsImages from(List<ReceiptImage> receipts) {
        return new ReceiptsImages(receipts);
    }



    public Receipts makeOCR() {
        return Receipts.from(null);
    }
}
