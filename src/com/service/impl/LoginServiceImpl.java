package com.service.impl;

import com.entity.Admin;
import com.entity.Reader;
import com.repository.AdminRepository;
import com.repository.ReaderRepository;
import com.repository.impl.AdminRepositoryImpl;
import com.repository.impl.ReaderRepositoryImpl;
import com.service.LoginService;

public class LoginServiceImpl implements LoginService {

    private ReaderRepository readerRepository = new ReaderRepositoryImpl();
    private AdminRepository adminRepository = new AdminRepositoryImpl();

    @Override
    public Object login(String username, String password, String type) {
        Object object = null;
        switch (type) {
            case "reader":
                object = readerRepository.login(username,password);
                break;
            case "admin":
                object = adminRepository.login(username,password);
                break;
        }
        return object;
    }
}
