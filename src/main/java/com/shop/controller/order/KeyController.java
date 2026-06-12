package com.shop.controller.order;

import com.shop.model.Customer;
import com.shop.services.UserKeyService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.Base64;

@WebServlet(name = "KeyController", urlPatterns = {"/key"})
@MultipartConfig
public class KeyController extends HttpServlet {

    private UserKeyService userKeyService;

    @Override
    public void init() throws ServletException {
        userKeyService = new UserKeyService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Customer customer = getCustomer(request, response);
        if (customer == null) return;

        HttpSession session = request.getSession(false);

        byte[] pendingPrivate = (byte[]) session.getAttribute("pendingPrivateKey");
        byte[] pendingPublic  = (byte[]) session.getAttribute("pendingPublicKey");

        if (pendingPrivate != null && pendingPublic != null) {
            session.removeAttribute("pendingPrivateKey");
            session.removeAttribute("pendingPublicKey");

            request.setAttribute("pendingPrivateKeyB64",
                    Base64.getEncoder().encodeToString(pendingPrivate));
            request.setAttribute("pendingPublicKeyB64",
                    Base64.getEncoder().encodeToString(pendingPublic));
            request.setAttribute("customerId", customer.getId());

            Boolean newKeyActivated = (Boolean) session.getAttribute("newKeyActivated");
            if (newKeyActivated != null && newKeyActivated) {
                session.removeAttribute("newKeyActivated");
                request.setAttribute("newKeyActivated", true);
            } else {
                request.setAttribute("hasPendingKey", true);
            }
        }

        request.setAttribute("activeKey", userKeyService.getActiveKey(customer.getId()));
        request.setAttribute("keys", userKeyService.getAllKeys(customer.getId()));

        request.getRequestDispatcher("/WEB-INF/jsp/order/key-manager.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/key");
    }

    private Customer getCustomer(HttpServletRequest request,
                                 HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("customer") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return null;
        }
        return (Customer) session.getAttribute("customer");
    }
}