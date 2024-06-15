package com.example.bookrental.controller;

import com.example.bookrental.controller.basecontroller.BaseController;
import com.example.bookrental.utils.QRCodeGenerator;
import com.google.zxing.WriterException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/generate-qr")
@RequiredArgsConstructor
public class QrCodeController extends BaseController {
    private static final String QR_CODE_IMAGE_PATH = "D:/Spring/BookRental/src/main/resources/static/qr3.png";
    private final QRCodeGenerator qrCodeGenerator;

    @GetMapping()
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
    public String getQRCode(Model model) {
        String base64 = "bibek Bhattarai Base64";
        String imageQr = "bibek bhattarai ImageQR";

        byte[] image = new byte[0];
        try {

            // Generate and Return Qr Code in Byte Array
            image = QRCodeGenerator.getQRCodeImage(base64, 250, 250);


            Map<String, Object> qrData = new HashMap<>();
            qrData.put("ScheduleId", 1);
            qrData.put("studentId", 2);
            qrData.put("studentName", "bibek bhattarai");
            qrData.put("studentEmail", "bibek.bhattarai83@gmail.com");
            qrData.put("profession", "Student");
            qrData.put("studentDob", "2057-9-23");
            qrData.put("studentPhone", "9810569398");
            qrData.put("mockTestName", "IELTS");
            qrData.put("mockTestDate", "2024-6-1");
            qrData.put("startTime", "11:30 AM");
            qrData.put("arrivalTime", "11:00 AM");
            // Generate and Save Qr Code Image in static/image folder
            String s = qrCodeGenerator.generateQRCodeImage(qrData);

            // Convert Byte Array into Base64 Encode String
            String qrcode = Base64.getEncoder().encodeToString(image);

            model.addAttribute("medium", base64);
            model.addAttribute("github", imageQr);
            model.addAttribute("qrcode", qrcode);

            return s;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (WriterException e) {
            throw new RuntimeException(e);
        }
    }
}
