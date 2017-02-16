package ru.stereohorse.glenn;

import static ru.stereohorse.glenn.entities.Storage.inFile;
import static ru.stereohorse.glenn.entities.Telegram.useTelegram;

import static java.util.Optional.ofNullable;

public class Glenn {

    private void updateStorageWithNewReceipts() {
        useTelegram()
            .pullReceipts()
            .makeOCR()
            .store(inFile(ofNullable(System.getenv("GLENN_DB_PATH"))
                        .orElse("glenn.db")));
    }

    public static void main(String... args) {
        new Glenn().updateStorageWithNewReceipts();
    }
}
