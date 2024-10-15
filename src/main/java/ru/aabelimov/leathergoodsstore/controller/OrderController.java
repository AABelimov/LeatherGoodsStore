package ru.aabelimov.leathergoodsstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;
import ru.aabelimov.leathergoodsstore.dto.CreateOrderDto;
import ru.aabelimov.leathergoodsstore.dto.UpdatedOrderProductQuantityDto;
import ru.aabelimov.leathergoodsstore.entity.*;
import ru.aabelimov.leathergoodsstore.service.*;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderProductService orderProductService;
    private final CategoryService categoryService;
    private final LeatherService leatherService;
    private final ProductService productService;
    private final LeatherColorService leatherColorService;
    private final ProductLeatherColorService productLeatherColorService;
    private final EmailService emailService;
    private final ImageService imageService;
    private final CartService cartService;
    private final Cart cart;
    private final ObjectMapper objectMapper;

    @Value("${credentials.yookassa.shop-id}")
    private String shopId;

    @Value("${credentials.yookassa.secret-key}")
    private String secretKey;

    @Value("${admin.username}")
    private String username;

    @PostMapping
    public String createOrder(CreateOrderDto dto) throws IOException, MessagingException {
        List<Image> productsImages = cartService.getProductsImages();
        List<String> imagesResourceNames = productsImages.stream()
                .map(Image::getImagePath)
                .toList();
        Order order = orderService.createOrder(dto);
        Context context = new Context();
        String subject = "Оформлен заказ #%d".formatted(order.getId());

        context.setVariable("subject", subject);
        context.setVariable("order", order);
        context.setVariable("orderProducts", orderProductService.getAllByOrderId(order.getId()));
        context.setVariable("imagesResourceNames", imagesResourceNames);

        Thread sendMessageThread = new Thread(() -> {
            try {
                emailService.sendHtmlMessage(username, subject, "email/email", context, productsImages);
            } catch (MessagingException e) {
                throw new RuntimeException(e); // TODO ::
            }
        });

        sendMessageThread.start();

//        PaymentRequest paymentRequest = new PaymentRequest(BigDecimal.valueOf(order.getTotalCost()),
//                "Заказ №%d".formatted(order.getId()));
//        String credentials = "%s:%s".formatted(shopId, secretKey);
//        String encodedCredentials = new String(Base64.getEncoder().encode(credentials.getBytes()));
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.add("Idempotence-Key", "%d".formatted(order.getId()));
//        headers.add("Authorization", "Basic " + encodedCredentials);
//        HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(paymentRequest), headers);
//        PaymentResponse test = restTemplate.postForObject("https://api.yookassa.ru/v3/payments", request, PaymentResponse.class);
//        System.out.println(test);
//
//        emailService.sendSimpleMessage(order.getUser().getUsername(),
//                "Оплата заказа #%d".formatted(order.getId()),
//                "Оплати заказ по ссылке: %s".formatted(Objects.requireNonNull(test).getConfirmation().getConfirmation_url()));

//        System.out.println(payment);
        return "redirect:/";
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String getOrder(@PathVariable Long id, Model model) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        model.addAttribute("formatter", formatter);
        model.addAttribute("order", orderService.getOrder(id));
        model.addAttribute("orderProducts", orderProductService.getAllByOrderId(id));
        model.addAttribute("categories", categoryService.getAllVisibleCategories());
        return "order/order";
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String getOrders(@RequestParam(required = false) OrderStatus status, Model model) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        status = status == null ? OrderStatus.NEW : status;
        model.addAttribute("status", status);
        model.addAttribute("formatter", formatter);
        model.addAttribute("orders", orderService.getOrdersByStatus(status));
        model.addAttribute("categories", categoryService.getAllVisibleCategories());
        return "order/orders";
    }

    @GetMapping("{id}/add-product")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String getProductsPage(@PathVariable Long id, Model model) {
        model.addAttribute("order", orderService.getOrder(id));
        model.addAttribute("categories", categoryService.getAllVisibleCategories());
        model.addAttribute("leathers", leatherColorService.getAllLeathersWithVisibleLeatherColors());
        model.addAttribute("products", productService.getAllVisibleProductsWhereCategoryIsVisible());
        model.addAttribute("category", null);
        return "order-product/catalog";
    }

    @GetMapping("{id}/add-product/{productId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String getCreateOrderProductPage(@PathVariable Long id, @PathVariable Long productId, Model model) {
        List<Leather> leathers = leatherService.getAllLeathers();
        model.addAttribute("order", orderService.getOrder(id));
        model.addAttribute("product", productService.getProduct(productId));
        model.addAttribute("leathers", leathers);
        model.addAttribute("productLeatherColors", productLeatherColorService.getAllByProductId(productId));
        model.addAttribute("categories", categoryService.getAllVisibleCategories());
        return "order-product/create-order-product";
    }

    @PatchMapping("{id}/update-status")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String updateStatus(@PathVariable Long id, @RequestParam OrderStatus status) {
        orderService.updateStatus(id, status);
        return "redirect:/orders/{id}";
    }

    @PatchMapping("order-product/{orderProductId}/minus")
    @ResponseBody
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<UpdatedOrderProductQuantityDto> minusOrderProduct(@PathVariable Long orderProductId) {
        return ResponseEntity.ok(orderService.updateOrderProductQuantity(orderProductId, "minus"));
    }

    @PatchMapping("order-product/{orderProductId}/plus")
    @ResponseBody
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<UpdatedOrderProductQuantityDto> plusOrderProduct(@PathVariable Long orderProductId) {
        return ResponseEntity.ok(orderService.updateOrderProductQuantity(orderProductId, "plus"));
    }

    @PatchMapping("order-product/{orderProductId}/remove")
    @ResponseBody
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<UpdatedOrderProductQuantityDto> removeOrderProduct(@PathVariable Long orderProductId) {
        return ResponseEntity.ok(orderService.deleteOrderProduct(orderProductId));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return "redirect:/orders";
    }
}
