package ru.stereohorse.glenn.entities;

public class Telegram {

    public static Telegram useTelegram() {
        return new Telegram();
    }

    public ReceiptsImages pullReceipts() {
        return ReceiptsImages.from(null);
    }
}
