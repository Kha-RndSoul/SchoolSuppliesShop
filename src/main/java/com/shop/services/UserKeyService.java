package com.shop.services;
import com.shop.dao.order.UserKeyDAO;

public class UserKeyService {
    private final UserKeyDAO userKeyDAO;

    public UserKeyService() {
        this.userKeyDAO = new UserKeyDAO();
    }
}