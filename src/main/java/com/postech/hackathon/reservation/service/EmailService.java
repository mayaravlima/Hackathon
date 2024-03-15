package com.postech.hackathon.reservation.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.postech.hackathon.client.entity.Client;
import com.postech.hackathon.client.model.ClientResponse;
import com.postech.hackathon.locality.entity.*;
import com.postech.hackathon.locality.model.request.AddressRequest;
import com.postech.hackathon.locality.model.response.*;
import com.postech.hackathon.optionalService.entity.OfferedService;
import com.postech.hackathon.optionalService.entity.OptionalItem;
import com.postech.hackathon.optionalService.model.OfferedServiceResponse;
import com.postech.hackathon.optionalService.model.OptionalItemResponse;
import com.postech.hackathon.reservation.entity.Reservation;
import com.postech.hackathon.reservation.model.ReservationResponse;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;

@Service
@AllArgsConstructor
@Slf4j
public class EmailService {
    private final JavaMailSender emailSender;

    public void send(String email, ReservationResponse response) {
        MimeMessage message = emailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom("your-email@example.com");
            helper.setTo(email);
            helper.setSubject("Reservation Confirmation");
            helper.setText(buildEmailBody(response), true);
            emailSender.send(message);
        } catch (Exception e) {
            log.error("Failed to send email", e);
        }
    }

    public String buildEmailBody(ReservationResponse reservation) {
        StringBuilder htmlBuilder = new StringBuilder();

        htmlBuilder.append("<!DOCTYPE html>");
        htmlBuilder.append("<html lang=\"en\">");
        htmlBuilder.append("<head>");
        htmlBuilder.append("<meta charset=\"UTF-8\">");
        htmlBuilder.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
        htmlBuilder.append("<title>Detalhes da Reserva</title>");
        htmlBuilder.append("</head>");
        htmlBuilder.append("<body>");

        htmlBuilder.append("<h1>Detalhes da Reserva</h1>");
        htmlBuilder.append("<h2>Dados do Cliente:</h2>");
        htmlBuilder.append("<p>Nome: ").append(reservation.client().name()).append("</p>");
        htmlBuilder.append("<p>Email: ").append(reservation.client().email()).append("</p>");
        htmlBuilder.append("<p>Número de Telefone: ").append(reservation.client().phoneNumber()).append("</p>");

        htmlBuilder.append("<h2>Serviços:</h2>");
        htmlBuilder.append("<ul>");
        for (OfferedServiceResponse service : reservation.service()) {
            htmlBuilder.append("<li>Nome: ").append(service.name()).append(" | Preço: ").append(service.price()).append("</li>");
        }
        htmlBuilder.append("</ul>");

        htmlBuilder.append("<h2>Optionais:</h2>");
        htmlBuilder.append("<ul>");
        for (OptionalItemResponse optional : reservation.optionals()) {
            htmlBuilder.append("<li>Nome: ").append(optional.name()).append(" | Preço: ").append(optional.price()).append("</li>");
        }
        htmlBuilder.append("</ul>");

        htmlBuilder.append("<h2>Quartos:</h2>");
        htmlBuilder.append("<ul>");
        for (RoomResponse room : reservation.rooms()) {
            htmlBuilder.append("<li>Tipo: ").append(room.type()).append(" | Capacidade: ").append(room.capacity()).append(" | Preço: ").append(room.price()).append(" | Quantidade: ").append(room.quantity()).append("</li>");
        }
        htmlBuilder.append("</ul>");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DecimalFormat decimalFormat = new DecimalFormat("#.00");

        htmlBuilder.append("<h2>Detalhes da Reserva:</h2>");
        htmlBuilder.append("<p>Data de Check-in: ").append(reservation.startDate().format(formatter)).append("</p>");
        htmlBuilder.append("<p>Data de Check-out: ").append(reservation.endDate().format(formatter)).append("</p>");
        htmlBuilder.append("<p>Número de Hóspedes: ").append(reservation.numGuests()).append("</p>");
        htmlBuilder.append("<p>Preço Total: ").append("R$ ").append(decimalFormat.format(reservation.totalPrice())).append("</p>");

        htmlBuilder.append("</body>");
        htmlBuilder.append("</html>");

        return htmlBuilder.toString();
    }
}
