package com.example.bookrental.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class QRCodeGenerator {

    private final ObjectMapper objectMapper;
    private static final String QR_CODE_IMAGE_PATH = "D:/Spring/BookRental/src/main/resources/static/";
    public String generateQRCodeImage(Map<String, Object> data)
            throws WriterException, IOException {

        String jsonString = objectMapper.writeValueAsString(data);

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        int width = 300;
        int height = 300;

        BitMatrix bitMatrix = qrCodeWriter.encode(jsonString, BarcodeFormat.QR_CODE, width, height);

        String imagePath = QR_CODE_IMAGE_PATH;
        File file = new File(imagePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String fileName = System.currentTimeMillis() + "-" + UUID.randomUUID() + "." +"png";
        String tempFileName = imagePath + fileName;
        Path path = FileSystems.getDefault().getPath(tempFileName);

        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
        return tempFileName;
    }


    public static byte[] getQRCodeImage(String text, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageConfig con = new MatrixToImageConfig( 0xFF000002 , 0xFFFFC041 ) ;

        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream,con);
        byte[] pngData = pngOutputStream.toByteArray();
        return pngData;
    }
}
